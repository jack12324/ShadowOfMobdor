package tests;

import com.jack12324.som.ShadowOfMobdor;
import com.jack12324.som.entity.EntitySoMZombie;
import com.jack12324.som.gen.SoMClass;
import com.jack12324.som.gen.StatGeneration;
import com.jack12324.som.gen.Tier;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;

@Mod.EventBusSubscriber
public class testStatGen {
    public static void main(String[] args) {

        //testRolls();
        // testClassRoll();
        // testTierRolls();

    }

    private static void testTierRolls() {
        System.out.println();
        testTierRoll(5);
        testTierRoll(15);
        testTierRoll(25);
        testTierRoll(35);
        testTierRoll(45);
        testTierRoll(55);
        System.out.println();
    }

    private static void testRolls() {
        System.out.println();
        testRollLevel(0);
        testRollLevel(15);
        testRollLevel(76);
        testRollLevel(-2);
        System.out.println();
    }

    private static void testTierRoll(int lvl) {
        int[] amounts = new int[6];
        Tier testTier;
        for (int i = 0; i < 10000000; i++) {
            testTier = StatGeneration.rollTier(lvl);

            switch (testTier) {
                case BASIC:
                    amounts[0]++;
                    break;
                case COMMON:
                    amounts[1]++;
                    break;
                case RARE:
                    amounts[2]++;
                    break;
                case EPIC:
                    amounts[3]++;
                    break;
                case LEGENDARY:
                    amounts[4]++;
                    break;
                case DEITY:
                    amounts[5]++;
                    break;
            }
        }
        System.out.print("\n Testing Tier Roll x10000000: player lvl = " + lvl + "\n");
        int counter = 0;
        for (int num : amounts) {
            System.out.println("Mob Tier " + StatGeneration.TIERS[counter] + " amount: " + num);
            counter++;
        }
    }

    private static void testClassRoll() {
        int[] amounts = new int[6];
        SoMClass testClass;
        for (int i = 0; i < 10000000; i++) {
            testClass = StatGeneration.rollClass();

            switch (testClass) {
                case TANK:
                    amounts[0]++;
                    break;
                case BERSERKER:
                    amounts[1]++;
                    break;
                case KNIGHT:
                    amounts[2]++;
                    break;
                case CLERIC:
                    amounts[3]++;
                    break;
                case NECROMANCER:
                    amounts[4]++;
                    break;
                case DEADEYE:
                    amounts[5]++;
                    break;
            }
        }
        System.out.print("\n Testing Class Roll x10000000:" + "\n");
        int counter = 0;
        for (int num : amounts) {
            System.out.println("Mob Class " + StatGeneration.CLASSES[counter] + " amount: " + num);
            counter++;
        }
    }

    private static void testRollLevel(int lvl) {
        ArrayList<Integer> lvls = new ArrayList<>();
        for (int i = 0; i < 10000000; i++)
            lvls.add(StatGeneration.rollLevel(lvl));

        int[] amounts = new int[11];

        for (int number : lvls) {
            switch (number - lvl) {
                case 5:
                    amounts[0]++;
                    break;
                case 4:
                    amounts[1]++;
                    break;
                case 3:
                    amounts[2]++;
                    break;
                case 2:
                    amounts[3]++;
                    break;
                case 1:
                    amounts[4]++;
                    break;
                case 0:
                    amounts[5]++;
                    break;
                case -1:
                    amounts[6]++;
                    break;
                case -2:
                    amounts[7]++;
                    break;
                case -3:
                    amounts[8]++;
                    break;
                case -4:
                    amounts[9]++;
                    break;
                case -5:
                    amounts[10]++;
                    break;
            }

        }
        System.out.print("\n Testing Lvl Gen x10000000: lvl = " + lvl + "\n");
        int counter = 5;
        for (int num : amounts) {
            System.out.println("Mob level + " + counter + " amount: " + num);
            counter--;
        }

    }

    public static void testMobArray(ArrayList<EntitySoMZombie[]> mobs) {
        for (int i = 0; i < mobs.get(mobs.size() - 1).length; i++) {
            ShadowOfMobdor.logger
                            .info((mobs.get(mobs.size() - 1)[mobs.get(mobs.size() - 1).length - 1])
                                            .equals(mobs.get(mobs.size() - 1)[i]));
        }
    }
}
