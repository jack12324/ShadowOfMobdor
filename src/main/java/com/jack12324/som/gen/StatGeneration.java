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
    public static final Weaknesses[] WEAKNESSES = {Weaknesses.ARROW, Weaknesses.AXE, Weaknesses.CRITICAL, Weaknesses.FIRE, Weaknesses.HOE, Weaknesses.MAGIC, Weaknesses.SWORD, Weaknesses.WATER, Weaknesses.WITHER};
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

    public static String generateName(ArrayList<Invulnerabilities> mobInv) {
        Random rand = new Random();
        String lastName = "";
        String firstName = "";
        if (mobInv.size() > 0) {
            if (rand.nextBoolean())
                lastName = LASTNAMEPRE[rand.nextInt(LASTNAMEPRE.length)] + mobInv.get(rand.nextInt(mobInv.size())).name();
            else
                lastName = mobInv.get(rand.nextInt(mobInv.size())).name() + LASTNAMESUF[rand.nextInt(LASTNAMESUF.length)];
        } else
            lastName = LASTNAMEPRE[rand.nextInt(LASTNAMEPRE.length)] + LASTNAMESUF[rand.nextInt(LASTNAMESUF.length)];

        lastName = lastName.substring(0, 1).toUpperCase() + lastName.substring(1);
        firstName = FIRSTNAMES[rand.nextInt(FIRSTNAMES.length)];

        return firstName + " " + lastName;
    }

    private static final String[] LASTNAMESUF = {
            "reaver",
            "flayer",
            "mantle",
            "flaw",
            "wood",
            "vale",
            "weaver",
            "rider",
            "cloud",
            "brow",
            "scribe",
            "strike",
            "flower",
            "flow",
            "pike",
            "follower",
            "fang",
            "thorne",
            "bloom",
            "song",
            "peak",
            "forest",
            "wind",
            "wind",
            "mantle",
            "wind",
            "arrow",
            "draft",
            "binder",
            "stone",
            "cut",
            "bash",
            "scream",
            "bone",
            "brooke",
            "ward ",
            "guard",
            "striker",
            "gaze",
            "trapper",
            "cut",
            "bash",
            "scream",
            "bone",
            "brooke",
            "ward",
            "guard",
            "striker",
            "gaze",
            "trapper"};

    private static final String[] LASTNAMEPRE = {
            "River",
            "Lone",
            "Rapid",
            "Winter",
            "Swift",
            "Red",
            "Stout",
            "Forest",
            "Slate",
            "Even",
            "Crystal",
            "Leaf",
            "Heart",
            "Plain",
            "Flint",
            "Silent",
            "Clear",
            "Frozen",
            "Skull",
            "Low",
            "Shade",
            "Mourning",
            "Elf",
            "Ice",
            "Hawk",
            "Autumn",
            "Snow",
            "Whisper",
            "Tall",
            "Hell",
            "Moon",
            "Free",
            "Whisper",
            "Tarren",
            "Moon",
            "Grizzly",
            "Hill"};
    private static final String[] FIRSTNAMES = {
            "Kriras",
            "Roug",
            "Hafi",
            "Malsis",
            "Midgu",
            "Meslay",
            "Gazdruid",
            "Ymso",
            "Dimzi",
            "Lifë",
            "Ma",
            "Tlidack",
            "Reonsan",
            "Mun",
            "Sthasthlitch",
            "Castissh",
            "Dorme",
            "Kaden",
            "Rasna",
            "Aegmax",
            "Tombco",
            "Cina",
            "Ylilsh",
            "Cata",
            "Gar",
            "Milhathjung",
            "Sasa",
            "Fledba",
            "Sisthas",
            "Monmi",
            "Lanymne",
            "Tsthihsdonnaky",
            "Lsthytosfou",
            "Nazgbard",
            "Kapzah",
            "Gardtred",
            "Thahu",
            "Bowgor",
            "Boltya",
            "Magaw",
            "Irri",
            "Parksmelt",
            "Salgas",
            "Biwarg",
            "Overchies",
            "Shysa",
            "Cicur",
            "Fenetheldi",
            "Hilxoaspho",
            "Neusfor",
            "Bia",
            "Dáinmo",
            "Baras",
            "Egartre",
            "Xomil",
            "Sitscual",
            "Pyver",
            "Palmi",
            "Krulre",
            "Fales",
            "Hisgu",
            "Morwal",
            "Ushanggnau",
            "Laycoh",
            "Bríthir",
            "Monsa",
            "Buya",
            "Ythan",
            "Fawong",
            "Rah",
            "Niailxōc",
            "Syshsyts'par",
            "Daetress",
            "Vrybad",
            "Maypa",
            "Yaacar",
            "Vas",
            "Atlpos",
            "Unor",
            "Midshu",
            "Egarsharah",
            "Neushtisth",
            "Namon",
            "Azor",
            "Hael",
            "Laiik",
            "Rendiganor",
            "Olorie",
            "Fiyron",
            "Montli",
            "Uma",
            "Duly",
            "Ulfba",
            "Samo",
            "Cimo",
            "Hauah",
            "Silson",
            "Cormi",
            "Nathekthssais",
            "Bete",
            "Ji'go",
            "Nalac",
            "Cen'bra",
            "Wixy",
            "Riqcitbi",
            "Miaa",
            "Gandae",
            "Mumla",
            "Ulfanxō",
            "Celant",
            "Arin",
            "Cuīxsha",
            "Ilmaa",
            "Bel",
            "Rtuthy",
            "Sonka",
            "Khristo",
            "Itsci",
            "Baze",
            "Ayrothon",
            "Darthslish",
            "Jebus",
            "Lafled",
            "Sthy",
            "Jacoē",
            "Huitzi",
            "Umur",
            "Esfur",
            "Gashtla",
            "Vafu",
            "Gimrys",
            "Mor",
            "Neles",
            "Sa",
            "Wenshad",
            "Skesha",
            "Monsham",
            "Simaferum",
            "Rugabr",
            "Sais",
            "Alcu",
            "Frons",
            "Kami",
            "Doom",
            "Acoa",
            "Chocu",
            "Tlalme",
            "Kahudsane",
            "Bagna",
            "Haneus",
            "Shada",
            "Na",
            "Wadog",
            "Soodsa",
            "Winekhi",
            "Santhmus",
            "Minro",
            "Tossrintō",
            "Kaphis",
            "Clava"};

}
