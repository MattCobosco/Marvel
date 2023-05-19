package core.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import core.db.contracts.FavoriteTableContract;
import core.db.contracts.HistoryTableContract;

public class DatabaseHelper extends SQLiteOpenHelper{
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Marvel.db";

    // Favorite Table
    private static final String SQL_CREATE_FAVORITE_TABLE =
            "CREATE TABLE " + FavoriteTableContract.FavoriteTableEntry.TABLE_NAME + " (" +
                    BaseColumns._ID + " INTEGER PRIMARY KEY," +
                    FavoriteTableContract.FavoriteTableEntry.COLUMN_NAME_TYPE + " TEXT," +
                    FavoriteTableContract.FavoriteTableEntry.COLUMN_NAME_ID + " TIMESTAMP)";

    private static final String SQL_DELETE_FAVORITE_TABLE =
            "DROP TABLE IF EXISTS " + FavoriteTableContract.FavoriteTableEntry.TABLE_NAME;

    // History Table
    private static final String SQL_CREATE_HISTORY_TABLE =
            "CREATE TABLE " + HistoryTableContract.HistoryTableEntry.TABLE_NAME + " (" +
                    BaseColumns._ID + " INTEGER PRIMARY KEY," +
                    HistoryTableContract.HistoryTableEntry.COLUMN_NAME_TYPE + " TEXT," +
                    HistoryTableContract.HistoryTableEntry.COLUMN_NAME_ID + " TEXT," +
                    HistoryTableContract.HistoryTableEntry.COLUMN_NAME_NAME + " TEXT," +
                    HistoryTableContract.HistoryTableEntry.COLUMN_NAME_TIMESTAMP + " LONG)";

    private static final String SQL_DELETE_HISTORY_TABLE =
            "DROP TABLE IF EXISTS " + HistoryTableContract.HistoryTableEntry.TABLE_NAME;

    public DatabaseHelper(final Context context){
        super(context, DatabaseHelper.DATABASE_NAME, null, DatabaseHelper.DATABASE_VERSION);
    }

    @Override
    public void onCreate(final SQLiteDatabase sqLiteDatabase) {
          sqLiteDatabase.execSQL(DatabaseHelper.SQL_CREATE_FAVORITE_TABLE);
            sqLiteDatabase.execSQL(DatabaseHelper.SQL_CREATE_HISTORY_TABLE);
    }

    @Override
    public void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion){
        db.execSQL(DatabaseHelper.SQL_DELETE_FAVORITE_TABLE);
        db.execSQL(DatabaseHelper.SQL_DELETE_HISTORY_TABLE);
        this.onCreate(db);
    }

    public void onDowngrade(final SQLiteDatabase db, final int oldVersion, final int newVersion){
        this.onUpgrade(db, oldVersion, newVersion);
    }
}
