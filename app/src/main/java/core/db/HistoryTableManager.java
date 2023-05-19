package core.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import core.db.models.HistoryEntry;
import core.enums.Type;

public class HistoryTableManager {
    private final DatabaseHelper databaseHelper;

    public HistoryTableManager(final Context context) {
        this.databaseHelper = new DatabaseHelper(context);
    }

    public void addHistoryEntry(final HistoryEntry entry) {
        final SQLiteDatabase db = this.databaseHelper.getWritableDatabase();
        final ContentValues values = new ContentValues();
        values.put("type", entry.type.name());
        values.put("id", entry.id);
        values.put("timestamp", entry.timestamp);
        db.insert("history", null, values);

        limitHistoryEntries();
    }

    public void limitHistoryEntries() {
        final SQLiteDatabase db = this.databaseHelper.getWritableDatabase();
        db.execSQL("DELETE FROM history WHERE id NOT IN (SELECT id FROM history ORDER BY timestamp DESC LIMIT 10)");
    }

    public void removeAllHistoryEntries() {
        final SQLiteDatabase db = this.databaseHelper.getWritableDatabase();
        db.delete("history", null, null);
        db.close();
    }

    public List<HistoryEntry> getAllHistoryEntries() {
        final SQLiteDatabase db = this.databaseHelper.getReadableDatabase();
        final Cursor cursor = db.query("history", new String[]{"type", "id", "timestamp"}, null, null, null, null, null);
        final List<HistoryEntry> historyEntries = new ArrayList<>();
        while (cursor.moveToNext()) {
            historyEntries.add(new HistoryEntry(Type.valueOf(cursor.getString(0)), cursor.getString(1), cursor.getLong(2)));
        }
        cursor.close();
        return historyEntries;
    }

    public void closeDbConnection() {
        this.databaseHelper.close();
    }
}
