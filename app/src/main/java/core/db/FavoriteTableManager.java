package core.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import core.db.models.Favorite;
import core.enums.Type;

public class FavoriteTableManager {
    private final DatabaseHelper databaseHelper;

    public FavoriteTableManager(final Context context) {
        this.databaseHelper = new DatabaseHelper(context);
    }

    public void addFavorite(final Favorite favorite) {
        final SQLiteDatabase db = this.databaseHelper.getWritableDatabase();
        final ContentValues values = new ContentValues();
        values.put("type", favorite.type.name());
        values.put("id", favorite.id);
        db.insert("favorite", null, values);
    }

    public void removeFavorite(final Favorite favorite) {
        final SQLiteDatabase db = this.databaseHelper.getWritableDatabase();
        db.delete("favorite", "type = ? AND id = ?", new String[]{favorite.type.name(), favorite.id});
    }

    public void removeAllFavorites() {
        final SQLiteDatabase db = this.databaseHelper.getWritableDatabase();
        db.delete("favorite", null, null);
        db.close();
    }

    public Boolean isFavorite(final Favorite favorite) {
        final SQLiteDatabase db = this.databaseHelper.getReadableDatabase();
        final Cursor cursor = db.query("favorite", new String[]{"id"}, "type = ? AND id = ?", new String[]{favorite.type.name(), favorite.id}, null, null, null);
        final Boolean isFavorite = 0 < cursor.getCount();
        cursor.close();
        return isFavorite;
    }

    public void closeDbConnection() {
        this.databaseHelper.close();
    }
}
