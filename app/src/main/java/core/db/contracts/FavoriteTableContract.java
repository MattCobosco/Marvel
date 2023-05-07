package core.db.contracts;

import android.provider.BaseColumns;

public final class FavoriteTableContract {
    private FavoriteTableContract() {}

    public static class FavoriteTableEntry implements BaseColumns {
        public static final String TABLE_NAME = "favorite";
        public static final String COLUMN_NAME_TYPE = "type";
        public static final String COLUMN_NAME_ID = "id";
    }
}
