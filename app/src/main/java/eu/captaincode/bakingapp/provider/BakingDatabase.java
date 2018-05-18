package eu.captaincode.bakingapp.provider;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.Table;

@Database(version = BakingDatabase.VERSION)
public class BakingDatabase {
    public static final int VERSION = 1;

    @Table(BakingContract.class)
    public static final String INGREDIENT = "ingredient";
}
