package com.jack12324.som;

import java.util.UUID;

public class SoMConst {
    public static final int[] levels = new int[99];
    public static final int[] rings = new int[]{500, 1000, 1500};

    public static final UUID HP_ADD = UUID.fromString("b6922f5a-1705-40cc-ba77-d6264ad5cace");
    public static final UUID HP_MULT = UUID.fromString("79e21e03-7750-4f9e-acc1-2a2221a22634");

    public static final UUID FLLW_RNG_ADD = UUID.fromString("af2fe707-dd13-49b8-9669-e259ab4a275c");
    public static final UUID FLLW_RNG_MULT = UUID.fromString("75a4282b-328f-438b-9651-ca1c4641eb04");

    public static final UUID KNCKBCK_RES_ADD = UUID.fromString("cd66a719-b20a-41b5-b99e-7b975f0bd78d");
    public static final UUID KNCKBCK_RES_MULT = UUID.fromString("f8940fd2-226a-4bfb-b18a-1ee198f51cc6");

    public static final UUID MVMT_SPD_ADD = UUID.fromString("31ccdee9-2691-4043-9a1b-08621517d374");
    public static final UUID MVMT_SPD_MULT = UUID.fromString("0836adb3-4e1e-4b20-a784-873bd21c2d8d");

    public static final UUID ATK_ADD = UUID.fromString("842bc9a2-21ab-4579-81c2-9677a3db923b");
    public static final UUID ATK_MULT = UUID.fromString("6f05600d-f1e7-4854-9dd7-ef3e552fd44e");

    public static final UUID ARMR_ADD = UUID.fromString("90b7ec49-354e-4eae-b0ff-df12e2eb5cff");
    public static final UUID ARMR_MULT = UUID.fromString("db2ad047-1246-404d-9c27-9fe359574cec");

    public static final UUID ARMR_TGH_ADD = UUID.fromString("acfe9a33-cbc4-4cbc-ae0f-d506d0feecc6");
    public static final UUID ARMR_TGH_MULT = UUID.fromString("fc221fdb-229a-4026-9000-796a89931a13");


    static {
        levels[0] = 0;
        for (int i = 1; i < levels.length; i++)
            levels[i] = (int) (100 * Math.pow(1.075, i - 1));
    }
}
