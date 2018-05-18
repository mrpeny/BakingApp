package eu.captaincode.bakingapp.provider;

import android.net.Uri;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

@ContentProvider(
        authority = BakingProvider.AUTHORITY,
        database = BakingDatabase.class)
public class BakingProvider {

    public static final String AUTHORITY = "eu.captaincode.bakingapp.provider";

    @TableEndpoint(table = BakingDatabase.INGREDIENT)
    public static class Ingredients {
        @ContentUri(
                path = "ingredients",
                type = "vnd.android.cursor.dir/ingredients",
                defaultSort = BakingContract.COLUMN_ID)
        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/ingredients");
    }
}
