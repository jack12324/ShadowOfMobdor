package com.jack12324.som.gen;

import net.minecraft.util.DamageSource;

public enum Weaknesses {
    FIRE("fire", DamageSource.LAVA),
    WATER("water", DamageSource.DROWN),
    SWORD("sword", new DamageSource("sword")),
    AXE("axe", new DamageSource("axe")),
    ARROW("arrow", new DamageSource("proj").setProjectile()),
    MAGIC("magic", DamageSource.MAGIC),
    WITHER("wither", DamageSource.WITHER),
    CRITICAL("critical", new DamageSource("crit")),
    HOE("hoe", new DamageSource("hoe"));
    private String key;
    private DamageSource source;

    Weaknesses(String key, DamageSource source) {
        this.key = key;
        this.source = source;
    }

    public String key() {
        return key;
    }

    public DamageSource source() {
        return source;
    }
}
