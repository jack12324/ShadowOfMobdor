package com.jack12324.som.entity;

import com.jack12324.som.ShadowOfMobdor;
import com.jack12324.som.SoMConst;
import com.jack12324.som.capabilities.CapabilityHandler;
import com.jack12324.som.gen.*;
import com.jack12324.som.network.SoMPacketHandler;
import com.jack12324.som.network.nemesis.NemListPacket;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class EntitySoMZombie extends EntityZombie {
    private boolean killed;
    private EntityPlayer player = null;
    private int level;
    private Tier tier;
    private SoMClass mobClass;
    private ArrayList<Weaknesses> mobWk = new ArrayList<>();
    private ArrayList<Invulnerabilities> mobInv = new ArrayList<>();
    private String name;
    private BlockPos spawnLoc;

    public static final ResourceLocation LOOT = new ResourceLocation(ShadowOfMobdor.MODID, "entities/zombie");
    public static final DataParameter<Integer> TEXTURE_NUM = EntityDataManager.createKey(EntitySoMZombie.class, DataSerializers.VARINT);

    public EntitySoMZombie(EntityPlayer player) {
        super(player.getEntityWorld());
        this.player = player;
        this.level = StatGeneration.rollLevel(player.getCapability(CapabilityHandler.XP, null).getLevel());
        this.tier = StatGeneration.rollTier(this.level);
        this.mobClass = StatGeneration.rollClass();
        mobWk = StatGeneration.rollWeaknesses(this.tier.weaknessRolls(), this.mobWk);
        mobInv = StatGeneration.rollInvulnerabilities(this.tier.invulnerableRolls(), this.mobWk, this.mobInv);
        this.name = StatGeneration.generateName(mobInv);
        this.killed = false;
        this.spawnLoc = null;//todo
        setCustomNameTag(name);
        this.setSize(this.width * 1.25f, this.height * 1.25f);
        setTexture(this.tier);

        applyModifiers();
        this.setHealth(this.getMaxHealth());
    }

    public EntitySoMZombie(EntitySoMZombie mobToCopy) {
        super(mobToCopy.getEntityWorld());
        this.player = mobToCopy.player;
        this.level = mobToCopy.getLevel();
        this.tier = mobToCopy.getTier();
        this.mobClass = mobToCopy.getMobClass();
        mobWk = mobToCopy.getMobWk();
        mobInv = mobToCopy.getMobInv();
        this.name = mobToCopy.getName();
        this.killed = false;
        this.spawnLoc = mobToCopy.getSpawnLoc();
        setCustomNameTag(name);
        this.setSize(this.width * 1.25f, this.height * 1.25f);
        setTexture(this.tier);

        applyModifiers();
        this.setHealth(this.getMaxHealth());
    }

    public EntitySoMZombie(World worldIn) {
        super(worldIn);
        this.setSize(this.width * 1.25f, this.height * 1.25f);
    }

    /**
     * sets the texture varient based on the tier
     *
     * @param tier tier of mob
     */
    private void setTexture(Tier tier) {
        switch (tier) {
            case BASIC:
                setTextureNum(0);
                break;

            case COMMON:
                setTextureNum(1);
                break;

            case RARE:
                setTextureNum(2);
                break;

            case EPIC:
                setTextureNum(3);
                break;

            case LEGENDARY:
                setTextureNum(4);
                break;

            case DEITY:
                setTextureNum(5);
                break;

            default:
                setTextureNum(0);
        }
    }

    public void setPlayer(EntityPlayer player) {
        this.player = player;
    }

    public int getTextureNumber() {
        return this.dataManager.get(TEXTURE_NUM);
    }

    public ArrayList<Weaknesses> getMobWk() {
        return mobWk;
    }

    public ArrayList<Invulnerabilities> getMobInv() {
        return mobInv;
    }

    public int getLevel() {
        return level;
    }

    public Tier getTier() {
        return tier;
    }

    public SoMClass getMobClass() {
        return mobClass;
    }

    public boolean isKilled() {
        return killed;
    }

    public void setKilled(boolean killed) {
        this.killed = killed;
    }

    public void setTextureNum(int num) {
        this.dataManager.set(TEXTURE_NUM, num);
    }

    public BlockPos getSpawnLoc() {
        return this.spawnLoc;
    }

    @Override
    protected void entityInit() {
        int num = 0; //todo use 2 change texture
        super.entityInit();
        this.dataManager.register(TEXTURE_NUM, num);
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        for (Invulnerabilities inv : mobInv) {
            if (source.equals(inv.source()))
                return false;
            else if (source.isFireDamage() && inv.source().isFireDamage())
                return false;
            else if (source.isProjectile() && inv.source().isProjectile())
                return false;
            else if (source.getTrueSource() instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) source.getTrueSource();
                if (inv.key().equals("sword") && player.getHeldItemMainhand().getItem() instanceof ItemSword)
                    return false;
                else if (inv.key().equals("axe") && player.getHeldItemMainhand().getItem() instanceof ItemAxe)
                    return false;
                else if (inv.key().equals("hoe") && player.getHeldItemMainhand().getItem() instanceof ItemHoe)
                    return false;
            }
        }
        for (Weaknesses wk : mobWk) {
            if ((source == wk.source()) || (source.isFireDamage() && wk.source().isFireDamage()) || (source.isProjectile() && wk.source().isProjectile()))
                return super.attackEntityFrom(source, amount * 2);//todo alter amount bonus based on tier?

            else if (source.getTrueSource() instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) source.getTrueSource();
                if ((wk.key().equals("sword") && player.getHeldItemMainhand().getItem() instanceof ItemSword) || (wk.key().equals("axe") && player.getHeldItemMainhand().getItem() instanceof ItemAxe)
                        || (wk.key().equals("hoe") && player.getHeldItemMainhand().getItem() instanceof ItemHoe))
                    return super.attackEntityFrom(source, amount * 2);
            }
        }
        return super.attackEntityFrom(source, amount);//todo make the default false to force using certain weapons?
    }

    @Override
    public boolean shouldBurnInDay() {
        return false;
    }

    @Override protected void applyEntityAttributes() {
        //this.getEntityAttribute(SharedMonsterAttributes.FLYING_SPEED).setBaseValue(0);
        //this.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).setBaseValue(0);
        super.applyEntityAttributes();
    }

    @Override
    protected void initEntityAI() {
        super.initEntityAI();
    }

    @Nullable
    @Override
    protected Item getDropItem() {
        return super.getDropItem();
    }


    @Nullable
    @Override
    protected ResourceLocation getLootTable() {
        return super.getLootTable();
    }

    @Override
    protected void dropLoot(boolean wasRecentlyHit, int lootingModifier, DamageSource source) {
        super.dropLoot(wasRecentlyHit, lootingModifier, source);
    }

    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
        livingdata = super.onInitialSpawn(difficulty, livingdata);

        this.setEquipmentBasedOnDifficulty(difficulty);//todo edit for this mod
        this.setEnchantmentBasedOnDifficulty(difficulty);//todo make methods for level also

        return livingdata;
    }


    @Override
    public void onDeath(DamageSource cause) {
        setKilled(true);
        super.onDeath(cause);
        if (!this.getEntityWorld().isRemote)
            SoMPacketHandler.NETWORK.sendTo(new NemListPacket(this.player.getCapability(CapabilityHandler.NEM, null).getMobs()), (EntityPlayerMP) this.player);
    }

    private void applyModifiers() {

        removeModifiers();

        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).applyModifier(new AttributeModifier(SoMConst.HP_ADD, "Health level add", 10 * level, 0));
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).applyModifier(new AttributeModifier(SoMConst.FLLW_RNG_ADD, "Follow range level add", level, 0));
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).applyModifier(new AttributeModifier(SoMConst.KNCKBCK_RES_ADD, "Knockback resistance level add", .5 * level, 0));
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).applyModifier(new AttributeModifier(SoMConst.MVMT_SPD_ADD, "Movement Speed level add", .01 * level, 0));
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).applyModifier(new AttributeModifier(SoMConst.ATK_ADD, "Attack damage level add", .5 * level, 0));
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).applyModifier(new AttributeModifier(SoMConst.ARMR_ADD, "Armor level add", .1 * level, 0));
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).applyModifier(new AttributeModifier(SoMConst.ARMR_TGH_ADD, "Armor toughness level add", .1 * level, 0));


        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).applyModifier(new AttributeModifier(SoMConst.HP_MULT, "Health mult", this.mobClass.healthMult() * this.tier.tierMultiplier(), 1));
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).applyModifier(new AttributeModifier(SoMConst.FLLW_RNG_MULT, "Follow range mult", this.mobClass.followRangeMult() * this.tier.tierMultiplier(), 1));
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).applyModifier(new AttributeModifier(SoMConst.KNCKBCK_RES_MULT, "Knockback resistance mult", this.mobClass.knockbackResistanceMult() * this.tier.tierMultiplier(), 1));
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).applyModifier(new AttributeModifier(SoMConst.MVMT_SPD_MULT, "Movement Speed mult", this.mobClass.mvtSpeedMult() * this.tier.tierMultiplier(), 1));
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).applyModifier(new AttributeModifier(SoMConst.ATK_MULT, "Attack damage mult", this.mobClass.attackMult() * this.tier.tierMultiplier(), 1));
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).applyModifier(new AttributeModifier(SoMConst.ARMR_MULT, "Armor mult", this.mobClass.armorMult() * this.tier.tierMultiplier(), 1));
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).applyModifier(new AttributeModifier(SoMConst.ARMR_TGH_MULT, "Armor toughness mult", this.mobClass.armorToughnessMult() * this.tier.tierMultiplier(), 1));

        //todo new attributes
        //this.getEntityAttribute(SharedMonsterAttributes.HEAL).applyModifier
        //this.getEntityAttribute(SharedMonsterAttributes.SECOND_LIFE).applyModifier(new AttributeModifier("Random spawn bonus", this.rand.nextDouble() * 0.05000000074505806D, 0));
    }

    private void removeModifiers() {
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).removeModifier(SoMConst.HP_ADD);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).removeModifier(SoMConst.FLLW_RNG_ADD);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).removeModifier(SoMConst.KNCKBCK_RES_ADD);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).removeModifier(SoMConst.MVMT_SPD_ADD);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).removeModifier(SoMConst.ATK_ADD);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).removeModifier(SoMConst.ARMR_ADD);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).removeModifier(SoMConst.ARMR_TGH_ADD);

        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).removeModifier(SoMConst.HP_MULT);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).removeModifier(SoMConst.FLLW_RNG_MULT);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).removeModifier(SoMConst.KNCKBCK_RES_MULT);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).removeModifier(SoMConst.MVMT_SPD_MULT);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).removeModifier(SoMConst.ATK_MULT);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).removeModifier(SoMConst.ARMR_MULT);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).removeModifier(SoMConst.ARMR_TGH_MULT);

        //todo new attributes
        //this.getEntityAttribute(SharedMonsterAttributes.HEAL).applyModifier
        //this.getEntityAttribute(SharedMonsterAttributes.SECOND_LIFE).applyModifier(new AttributeModifier("Random spawn bonus", this.rand.nextDouble() * 0.05000000074505806D, 0));
    }

    /**
     * attempts a reroll of mob stats with no improvement guaranteed
     *
     * @param level
     */
    public void reRoll(int level) {
        reRoll(level, 0);
    }

    /**
     * rerolls mob stats with a minimum level increase ensured
     *
     * @param level
     * @param minimumLevelIncrease
     */
    public void reRoll(int level, int minimumLevelIncrease) {
        int oldLevel = getLevel();
        int loopBug = 0;
        do {
            tryLevelUp(level);
            ++loopBug;
            if (loopBug > 200)
                ShadowOfMobdor.logger.debug("Reroll loop stuck, mob level: " + getLevel() + ", level in: " + level + ", minimum increase: " + minimumLevelIncrease);
        } while (getLevel() - oldLevel < minimumLevelIncrease && loopBug < 200);

        Tier temp = StatGeneration.rollTier(this.level);
        if (temp.isGreaterTier(this.tier)) {
            mobWk.clear();
            mobWk = StatGeneration.rollWeaknesses(temp.weaknessRolls(), this.mobWk);
            mobInv = StatGeneration.rollInvulnerabilities(temp.invulnerableRolls() - this.tier.invulnerableRolls(), this.mobWk, this.mobInv);
            this.tier = temp;
            applyModifiers();
            setTexture(this.tier);
        }

    }

    public void tryLevelUp(int level) {
        int templvl = StatGeneration.rollLevel(level);
        if (templvl > this.level) ;
        this.level = templvl;
    }


    public void print() {
        System.out.printf("%-25s %5s\n", "Name:", this.name);
        System.out.printf("%-25s %-5d\n", "Level:", this.level);
        System.out.printf("%-25s %5s\n", "Class:", this.mobClass);
        System.out.printf("%-25s %5s\n\n", "Tier:", this.tier);
        System.out.printf("%-25s %5.2f\n", "Max Health:",
                        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH)
                                        .getAttributeValue());
        System.out.printf("%-25s %5.2f\n", "Follow Range:",
                        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE)
                                        .getAttributeValue());
        System.out.printf("%-25s %5.2f\n", "KNOCKBACK_RESISTANCE:",
                        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE)
                                        .getAttributeValue());
        System.out.printf("%-25s %5.2f\n", "MOVEMENT_SPEED:",
                        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED)
                                        .getAttributeValue());
        //System.out.printf("%-25s %5.2f\n", "FLYING_SPEED:", this.getEntityAttribute(SharedMonsterAttributes.FLYING_SPEED).getAttributeValue());
        System.out.printf("%-25s %5.2f\n", "ATTACK_DAMAGE:",
                        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE)
                                        .getAttributeValue());
        //System.out.printf("%-25s %5.2f\n", "ATTACK_SPEED:", this.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).getAttributeValue());
        System.out.printf("%-25s %5.2f\n", "ARMOR:",
                        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).getAttributeValue());
        System.out.printf("%-25s %5.2f\n", "ARMOR_TOUGHNESS:",
                        this.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS)
                                        .getAttributeValue());
        System.out.printf("\n%-25s %s\n", "Weaknesses:", mobWk);
        System.out.printf("%-25s, %s\n", "Invulnerabilities:", mobInv);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setInteger("textureNumber", getTextureNumber());
        compound.setInteger("som_level", level);
        compound.setString("som_name", name);
        compound.setString("som_tier", tier.name());
        compound.setString("som_class", mobClass.name());
        compound.setTag("weaknesses", writeEnumListToNBT(mobWk));
        compound.setTag("inv", writeEnumListToNBT(mobInv));
        compound.setBoolean("killed", this.killed);
        compound.setInteger("x", spawnLoc.getX());
        compound.setInteger("y", spawnLoc.getY());
        compound.setInteger("z", spawnLoc.getZ());
    }

    private <T extends Enum> NBTTagList writeEnumListToNBT(ArrayList<T> listIn) {
        NBTTagList list = new NBTTagList();
        NBTTagCompound level;
        for (int i = 0; i < listIn.size(); i++) {
            level = new NBTTagCompound();
            level.setString("" + i, listIn.get(i).name());
            list.appendTag(level);
        }
        return list;
    }

    private <T extends Enum> ArrayList<T> readEnumListFromNBT(Class<T> c, NBTTagList listIn) {
        NBTTagCompound level;
        ArrayList<T> listOut = new ArrayList<T>();
        for (int i = 0; i < listIn.tagCount(); i++) {
            level = listIn.getCompoundTagAt(i);
            listOut.add((T) T.valueOf(c, level.getString("" + i)));//todo not sure if this works
        }
        return listOut;
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        setTextureNum(compound.getInteger("textureNumber"));
        this.level = compound.getInteger("som_level");
        this.name = compound.getString("som_name");
        setCustomNameTag(this.name);
        this.tier = Tier.valueOf(compound.getString("som_tier"));
        setTexture(this.tier);
        this.mobClass = SoMClass.valueOf(compound.getString("som_class"));
        this.mobWk = readEnumListFromNBT(Weaknesses.class, compound.getTagList("weaknesses", 10));
        this.mobInv = readEnumListFromNBT(Invulnerabilities.class, compound.getTagList("inv", 10));
        this.killed = compound.getBoolean("killed");
        this.spawnLoc = new BlockPos(compound.getInteger("x"), compound.getInteger("y"), compound.getInteger("z"));

        applyModifiers();
        this.setHealth(this.getMaxHealth());
    }
}
