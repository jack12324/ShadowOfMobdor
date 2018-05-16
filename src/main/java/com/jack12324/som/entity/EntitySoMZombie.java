package com.jack12324.som.entity;

import com.jack12324.som.ShadowOfMobdor;
import com.jack12324.som.gen.*;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class EntitySoMZombie extends EntityZombie {
    private int level;
    private Tier tier;
    private SoMClass mobClass;
    private ArrayList<Weaknesses> mobWk = new ArrayList<>();
    private ArrayList<Invulnerabilities> mobInv = new ArrayList<>();
    private String name;

    public static final ResourceLocation LOOT = new ResourceLocation(ShadowOfMobdor.MODID, "entities/zombie");
    public static final DataParameter<Integer> TEXTURE_NUM = EntityDataManager.createKey(EntitySoMZombie.class, DataSerializers.VARINT);

    public ArrayList<Weaknesses> getMobWk() {
        return mobWk;
    }

    public ArrayList<Invulnerabilities> getMobInv() {
        return mobInv;
    }

    public EntitySoMZombie(World worldIn, int playerLvl) {
        super(worldIn);
        this.level = StatGeneration.rollLevel(playerLvl);
        this.tier = StatGeneration.rollTier(this.level);
        this.mobClass = StatGeneration.rollClass();
        mobWk = StatGeneration.rollWeaknesses(this.tier.weaknessRolls(), this.mobWk);
        mobInv = StatGeneration.rollInvulnerabilities(this.tier.invulnerableRolls(), this.mobWk, this.mobInv);
        this.name = StatGeneration.generateName(mobInv);
        this.setSize(this.width * 1.25f, this.height * 1.25f);
        setTexture(this.tier);
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

    public EntitySoMZombie(World worldIn) {
        this(worldIn, 0);
    }

    public int getTextureNumber() {
        return this.dataManager.get(TEXTURE_NUM);
    }

    public void setTextureNum(int num) {
        this.dataManager.set(TEXTURE_NUM, num);
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

        applyModifiers();
        this.setHealth(this.getMaxHealth());


        return livingdata;
    }


    @Override
    public void onDeath(DamageSource cause) {
        MobTracker.removeMob(this);
        super.onDeath(cause);
    }

    private void applyModifiers() {
        removeModifiers();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).applyModifier(new AttributeModifier("Health level add", 10 * level, 0));
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).applyModifier(new AttributeModifier("Follow range level add", 1 * level, 0));
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).applyModifier(new AttributeModifier("Knockback resistance level add", .5 * level, 0));
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).applyModifier(new AttributeModifier("Movement Speed level add", .01 * level,
                        0));
        //this.getEntityAttribute(SharedMonsterAttributes.FLYING_SPEED).applyModifier(new AttributeModifier("flying speed level add", .01 * level, 0));
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).applyModifier(new AttributeModifier("Attack damage level add", .5 * level,
                        0));
        //this.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).applyModifier(new AttributeModifier("Attack Speed level add", .2 * level, 0));
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).applyModifier(new AttributeModifier("Armor level add", .1 * level, 0));
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).applyModifier(new AttributeModifier("Armor toughness level add", .1 * level, 0));


        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).applyModifier(new AttributeModifier("Health mult", this.mobClass.healthMult() * this.tier.tierMultiplier(), 1));
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).applyModifier(new AttributeModifier("Follow range mult", this.mobClass.followRangeMult() * this.tier.tierMultiplier(), 1));
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).applyModifier(new AttributeModifier("Knockback resistance mult", this.mobClass.knockbackResistanceMult() * this.tier.tierMultiplier(), 1));
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).applyModifier(new AttributeModifier("Movement Speed mult", this.mobClass.mvtSpeedMult() * this.tier.tierMultiplier(),
                        1));
        //this.getEntityAttribute(SharedMonsterAttributes.FLYING_SPEED).applyModifier(new AttributeModifier("flying speed mult", this.mobClass.flySpeedMult() * this.tier.tierMultiplier(), 1));
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).applyModifier(new AttributeModifier("Attack damage mult", this.mobClass.attackMult() * this.tier.tierMultiplier(),
                        1));
        // this.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).applyModifier(new AttributeModifier("Attack Speed mult", this.mobClass.atkSpeedMult() * this.tier.tierMultiplier(), 1));
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).applyModifier(new AttributeModifier("Armor mult", this.mobClass.armorMult() * this.tier.tierMultiplier(), 1));
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).applyModifier(new AttributeModifier("Armor toughness mult", this.mobClass.armorToughnessMult() * this.tier.tierMultiplier(), 1));

        //todo new attributes
        //this.getEntityAttribute(SharedMonsterAttributes.HEAL).applyModifier
        //this.getEntityAttribute(SharedMonsterAttributes.SECOND_LIFE).applyModifier(new AttributeModifier("Random spawn bonus", this.rand.nextDouble() * 0.05000000074505806D, 0));
    }

    private void removeModifiers() {
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).removeAllModifiers();
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).removeAllModifiers();
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).removeAllModifiers();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).removeAllModifiers();
        //this.getEntityAttribute(SharedMonsterAttributes.FLYING_SPEED).removeAllModifiers();
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).removeAllModifiers();
        // this.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).removeAllModifiers();
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).removeAllModifiers();
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).removeAllModifiers();

        //todo new attributes
        //this.getEntityAttribute(SharedMonsterAttributes.HEAL).applyModifier
        //this.getEntityAttribute(SharedMonsterAttributes.SECOND_LIFE).applyModifier(new AttributeModifier("Random spawn bonus", this.rand.nextDouble() * 0.05000000074505806D, 0));
    }

    public void reRoll(int level) {

        tryLevelUp(level);

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
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        setTextureNum(compound.getInteger("textureNumber"));

    }
}
