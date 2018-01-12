package com.jack12324.som.gen;

import com.jack12324.som.ShadowOfMobdor;

import java.util.ArrayList;
import java.util.Random;

public class StatGeneration {
    static final double[] BASICWEIGHTS = {0.90, 0.10, 0.00, 0.00, 0.00, 0.00};
    static final double[] COMMONWEIGHTS = {0.50, 0.50, 0.00, 0.00, 0.00, 0.00};
    static final double[] RAREWEIGHTS = {0.20, 0.70, 0.10, 0.00, 0.00, 0.00};
    static final double[] EPICWEIGHTS = {0.10, 0.60, 0.20, 0.10, 0.00, 0.00};
    static final double[] LEGENDARYWEIGHTS = {0.00, 0.30, 0.40, 0.20, 0.10, 0.00};
    static final double[] DEITYWEIGHTS = {0.00, 0.00, 0.45, 0.35, 0.15, 0.05};
    public static final Tier[] TIERS = {Tier.BASIC, Tier.COMMON, Tier.RARE, Tier.EPIC, Tier.LEGENDARY, Tier.DEITY};
    public static final SoMClass[] CLASSES = {SoMClass.TANK, SoMClass.BERSERKER, SoMClass.KNIGHT, SoMClass.CLERIC, SoMClass.NECROMANCER, SoMClass.DEADEYE};
    public static final Weaknesses[] WEAKNESSES = {Weaknesses.ARROW, Weaknesses.AXE, Weaknesses.CRITICAL, Weaknesses.FIRE, Weaknesses.HOE, Weaknesses.POISON, Weaknesses.SWORD, Weaknesses.WATER, Weaknesses.WITHER};
    public static final Invulnerabilities[] INVULNERABILITIES = {Invulnerabilities.ARROW, Invulnerabilities.AXE, Invulnerabilities.CRITICAL, Invulnerabilities.FIRE, Invulnerabilities.HOE, Invulnerabilities.MAGIC, Invulnerabilities.SWORD, Invulnerabilities.WATER, Invulnerabilities.WITHER};

    /**
     * returns level of mob given a players level
     *
     * @param playerLvl level of player at time of roll
     * @return mob level +- 5 of player level
     */
    public static int rollLevel(int playerLvl) {

        Random rand = new Random();
        int lvl = rand.nextInt(11);
        lvl = lvl < 6 ? playerLvl + lvl : playerLvl - (lvl - 5);
        return lvl > 0 ? lvl : 1; //checks if lvl is negative number
    }

    /**
     * Determines tier of mob
     *
     * @param lvl Level of the mob, used to determine what roll weights to use
     * @return returns a tier for the mob
     */
    public static Tier rollTier(int lvl) {
        if (lvl >= 0) {
            if (lvl > 9) {
                if (lvl > 19) {
                    if (lvl > 29) {
                        if (lvl > 39) {
                            if (lvl > 49) {
                                return rollWithWeights(DEITYWEIGHTS);
                            }
                            return rollWithWeights(LEGENDARYWEIGHTS);
                        }
                        return rollWithWeights(EPICWEIGHTS);
                    }
                    return rollWithWeights(RAREWEIGHTS);
                }
                return rollWithWeights(COMMONWEIGHTS);
            }
            return rollWithWeights(BASICWEIGHTS);
        }
        ShadowOfMobdor.logger.error("rollTier hit catch case: lvl in = " + lvl);
        return rollWithWeights(BASICWEIGHTS);
    }

    /**
     * Used to randomly generate a tier for a mob given a set of weights
     *
     * @param weights percentages of certain tiers which are allowed
     * @return Tier for a mob
     */
    private static Tier rollWithWeights(double[] weights) {

        Random rand = new Random();
        double[] cuttoffs = new double[weights.length];
        double last = 0.0;
        for (int i = 0; i < weights.length; i++) {
            cuttoffs[i] = (weights[i] * 1.0) + last;
            last = cuttoffs[i];
        }
        double rNumber = rand.nextDouble();
        for (int i = 0; i < cuttoffs.length; i++) {
            if (rNumber < cuttoffs[i])
                return TIERS[i];
        }
        ShadowOfMobdor.logger.error("rollWeightedTier hit catch case: random number = " + rNumber);
        return TIERS[0];
    }

    /**
     * randomly generates the class of a mob
     *
     * @return one of the 6 classes with equal weight
     */
    public static SoMClass rollClass() {

        Random rand = new Random();
        return CLASSES[rand.nextInt(6)];
    }

    /**
     * generates weaknesses
     *
     * @param rolls         controlls the number of rolls
     * @param oldWeaknesses arraylis with already generated weaknesses
     * @return copy of the old Weaknesses arraylist containing new weaknesses, if any
     */
    public static ArrayList<Weaknesses> rollWeaknesses(int rolls, ArrayList<Weaknesses> oldWeaknesses) {
        ArrayList<Weaknesses> newWk = (ArrayList<Weaknesses>) oldWeaknesses.clone();
        Random rand = new Random();
        boolean newAdded;

        for (int i = 0; i < rolls; i++) {
            if (newWk.size() == WEAKNESSES.length)
                break;
            if (rand.nextBoolean()) {
                newAdded = false;
                while (!newAdded) {
                    Weaknesses weakness = (WEAKNESSES[rand.nextInt(9)]);
                    if (!(newWk.contains(weakness)) && (!newWk.contains(weakness))) {
                        newWk.add(weakness);
                        newAdded = true;
                    }
                }
            }
        }
        return newWk;
    }

    /**
     * generates invulnerabilities
     *
     * @param rolls      this controlls the number of rolls
     * @param weaknesses weaknesses of the mob, a strength can't also be a weakness
     * @param oldInv     contains invulnerabilities already applied
     * @return copy of arraylist of rolled invulnerabilities
     */
    public static ArrayList<Invulnerabilities> rollInvulnerabilities(int rolls, ArrayList<Weaknesses> weaknesses, ArrayList<Invulnerabilities> oldInv) {
        ArrayList<Invulnerabilities> newInv = (ArrayList<Invulnerabilities>) oldInv.clone();
        ArrayList<String> wkKeys = new ArrayList<>();
        for (Weaknesses wk : weaknesses) {
            wkKeys.add(wk.key());
        }

        Random rand = new Random();
        boolean newAdded;

        for (int i = 0; i < rolls; i++) {
            if (newInv.size() == INVULNERABILITIES.length)
                break;
            if (rand.nextBoolean()) {
                newAdded = false;
                while (!newAdded) {
                    Invulnerabilities invulnerability = (INVULNERABILITIES[rand.nextInt(9)]);
                    if ((!newInv.contains(invulnerability)) && (!wkKeys.contains(invulnerability.key()))) {
                        newInv.add(invulnerability);
                        newAdded = true;
                    }
                }
            }
        }
        return newInv;
    }

}
