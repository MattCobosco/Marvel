package core.db.contracts;

import android.provider.BaseColumns;

public class HistoryTableContract {
    private HistoryTableContract() {}

    public static class HistoryTableEntry implements BaseColumns {
        public static final String TABLE_NAME = "history";
        public static final String COLUMN_NAME_TYPE = "type";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_TIMESTAMP = "timestamp";
    }
}
