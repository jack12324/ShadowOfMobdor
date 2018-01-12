package com.jack12324.som.gen;

public enum SoMClass {
    // 0.0 = no change
    // 1.0 = 100% increase
    //-1.0 = 100% decrease
    TANK(1.0, 0.0, 1.0, -0.5, -0.5, 3.0, -0.5, 0.0, 0.0, 0.0, 0.0),
    BERSERKER(-0.5, 0.0, 0.0, 1.0, 1.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0),
    KNIGHT(0.0, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 1.0, 1.0, 0.0, 0.0),
    CLERIC(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0),
    NECROMANCER(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0),
    DEADEYE(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);

    private double healthMult;
    private double followRangeMult;
    private double knockbackResistanceMult;
    private double mvtSpeedMult;
    private double flySpeedMult;
    private double attackMult;
    private double atkSpeedMult;
    private double armorMult;
    private double armorToughnessMult;
    private double healMult;
    private double secondLifeMult;

    SoMClass(double healthMult, double followRangeMult, double knockbackResistanceMult,
             double mvtSpeedMult, double flySpeedMult, double attackMult, double atkSpeedMult,
             double armorMult, double armorToughnessMult, double healMult, double secondLifeMult) {
        this.healthMult = healthMult;
        this.followRangeMult = followRangeMult;
        this.knockbackResistanceMult = knockbackResistanceMult;
        this.mvtSpeedMult = mvtSpeedMult;
        this.flySpeedMult = flySpeedMult;
        this.attackMult = attackMult;
        this.atkSpeedMult = atkSpeedMult;
        this.armorMult = armorMult;
        this.armorToughnessMult = armorToughnessMult;
        this.healMult = healMult;
        this.secondLifeMult = secondLifeMult;
    }

    public double healthMult() {
        return healthMult;
    }

    public double followRangeMult() {
        return followRangeMult;
    }

    public double knockbackResistanceMult() {
        return knockbackResistanceMult;
    }

    public double mvtSpeedMult() {
        return mvtSpeedMult;
    }

    public double flySpeedMult() {
        return flySpeedMult;
    }

    public double attackMult() {
        return attackMult;
    }

    public double atkSpeedMult() {
        return atkSpeedMult;
    }

    public double armorMult() {
        return armorMult;
    }

    public double armorToughnessMult() {
        return armorToughnessMult;
    }

    public double healMult() {
        return healMult;
    }

    public double secondLifeMult() {
        return secondLifeMult;
    }
}
