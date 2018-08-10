package com.jack12324.som.capabilities.nemesis;

import com.jack12324.som.entity.EntitySoMZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class NemesisList implements INemesisList {

    private World world;
    private EntityPlayer player;

    public NemesisList(EntityPlayer player) {
        this.world = player.getEntityWorld();
        this.player = player;
    }

    private EntitySoMZombie mobs[] = new EntitySoMZombie[10];

    @Override
    public void addMob(EntitySoMZombie mob, int index) {
        mobs[index] = mob;
    }

    @Override
    public void removeMob(EntitySoMZombie mob) {
        for (int i = 0; i < mobs.length; i++)
            if (mobs[i].equals(mob))
                mobs[i] = null;
    }

    @Override
    public void removeMob(int index) {
        mobs[index] = null;
    }

    @Override
    public void clearMobs() {
        for (int i = 0; i < mobs.length; i++) {
            mobs[i] = null;
        }
    }

    @Override
    public EntitySoMZombie getMob(int index) {
        return mobs[index];
    }

    @Override
    public EntityPlayer getPlayer() {
        return this.player;
    }

    @Override
    public void copyList(EntitySoMZombie[] listToCopy) {
        clearMobs();
        for (int i = 0; i < listToCopy.length; i++)
            this.mobs[i] = listToCopy[i];
    }

    @Override
    public EntitySoMZombie[] getMobs() {
        return mobs;
    }

    @Override
    public World getWorld() {
        return this.world;
    }

    @Override
    public void setWorld(World world) {
        this.world = world;
    }

    @Override
    public boolean isEmpty() {
        for (int i = 0; i < mobs.length; i++) {
            if (!(mobs[i] == null))
                return false;
        }
        return true;
    }

}
