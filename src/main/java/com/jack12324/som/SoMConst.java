package com.jack12324.som;

public class SoMConst {
    public static final int[] levels = new int[99];

    static {
        levels[0] = 0;
        for (int i = 1; i < levels.length; i++)
            levels[i] = (int) (100 * Math.pow(1.075, i - 1));
    }
}
