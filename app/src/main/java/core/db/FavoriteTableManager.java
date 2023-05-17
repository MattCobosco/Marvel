package core.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import core.db.models.Favorite;

public class FavoriteTableManager {
    private final DatabaseHelper databaseHelper;

    public FavoriteTableManager(final Context context) {
        this.databaseHelper = new DatabaseHelper(context);
    }

    public void addFavorite(final Favorite favorite) {
        final SQLiteDatabase db = this.databaseHelper.getWritableDatabase();
        final ContentValues values = new ContentValues();
        values.put("type", favorite.type);
        values.put("id", favorite.id);
        db.insert("favorite", null, values);
    }

    public void removeFavorite(final Favorite favorite) {
        final SQLiteDatabase db = this.databaseHelper.getWritableDatabase();
        db.delete("favorite", "type = ? AND id = ?", new String[]{favorite.type, favorite.id});
        db.close();
    }

    public void removeAllFavorites() {
        final SQLiteDatabase db = this.databaseHelper.getWritableDatabase();
        db.delete("favorite", null, null);
        db.close();
    }

    public List<String> getFavoriteIdsByType(final String type) {
        final SQLiteDatabase db = this.databaseHelper.getReadableDatabase();
        final Cursor cursor = db.query("favorite", new String[]{"id"}, "type = ?", new String[]{type}, null, null, null);
        final List<String> favorites = new ArrayList<>();
        while (cursor.moveToNext()) {
            favorites.add(cursor.getString(0));
        }
        cursor.close();
        return favorites;
    }

    public List<Favorite> getAllFavorites() {
        final SQLiteDatabase db = this.databaseHelper.getReadableDatabase();
        final Cursor cursor = db.query("favorite", new String[]{"type", "id"}, null, null, null, null, null);
        final List<Favorite> favorites = new ArrayList<>();
        while (cursor.moveToNext()) {
            favorites.add(new Favorite(cursor.getString(0), cursor.getString(1)));
        }
        cursor.close();
        return favorites;
    }

    public Boolean isFavorite(final Favorite favorite) {
        final SQLiteDatabase db = this.databaseHelper.getReadableDatabase();
        final Cursor cursor = db.query("favorite", new String[]{"id"}, "type = ? AND id = ?", new String[]{favorite.type, favorite.id}, null, null, null);
        final Boolean isFavorite = 0 < cursor.getCount();
        cursor.close();
        return isFavorite;
    }

    public void closeDbConnection() {
        this.databaseHelper.close();
    }
}
