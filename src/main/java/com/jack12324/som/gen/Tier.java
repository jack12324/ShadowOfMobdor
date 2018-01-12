package com.jack12324.som.gen;

public enum Tier {
    BASIC(8, 1, 1),
    COMMON(4, 2, 1),
    RARE(2, 3, 2),
    EPIC(1, 5, 4),
    LEGENDARY(0, 8, 8),
    DEITY(0, 12, 16);

    private int weaknessRolls;
    private int invulnerableRolls;
    private int tierMultiplier;

    Tier(int weaknessRolls, int invulnerableRolls, int tierMultiplier) {
        this.weaknessRolls = weaknessRolls;
        this.invulnerableRolls = invulnerableRolls;
        this.tierMultiplier = tierMultiplier;
    }

    public int weaknessRolls() {
        return weaknessRolls;
    }

    public int invulnerableRolls() {
        return invulnerableRolls;
    }

    public int tierMultiplier() {
        return tierMultiplier;
    }

    public boolean isGreaterTier(Tier tier) {
        return this.invulnerableRolls() > tier.invulnerableRolls();
    }
}
