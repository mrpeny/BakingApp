package eu.captaincode.bakingapp.provider;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.ConflictResolutionType;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.PrimaryKey;

public class BakingContract {

    @DataType(DataType.Type.INTEGER)
    @PrimaryKey(onConflict = ConflictResolutionType.REPLACE)
    @AutoIncrement
    public static final String COLUMN_ID = "_id";

    @DataType(DataType.Type.TEXT)
    public static final String COLUMN_INGREDIENT = "ingredient";

    @DataType(DataType.Type.TEXT)
    public static final String COLUMN_MEASURE = "measure";

    @DataType(DataType.Type.REAL)
    public static final String COLUMN_QUANTITY = "quantity";
}
