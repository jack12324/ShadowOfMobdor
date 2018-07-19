package com.jack12324.som.capabilities.nemesis;

import com.jack12324.som.entity.EntitySoMZombie;
import net.minecraft.world.World;

public interface INemesisList {//todo make entitySoMZombie generic

    void addMob(EntitySoMZombie mob, int index);

    void removeMob(EntitySoMZombie mob);

    void removeMob(int index);

    void clearMobs();

    EntitySoMZombie getMob(int index);

    EntitySoMZombie[] getMobs();

    World getWorld();

    void setWorld(World world);

    boolean isEmpty();
}

