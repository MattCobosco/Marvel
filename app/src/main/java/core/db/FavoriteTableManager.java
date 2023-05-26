package core.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

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
        values.put("name", favorite.name);
        db.insert("favorite", null, values);
    }

    public void removeFavorite(final Favorite favorite) {
        final SQLiteDatabase db = this.databaseHelper.getWritableDatabase();
        db.delete("favorite", "type = ? AND id = ? AND name = ?", new String[]{favorite.type.name(), favorite.id, favorite.name});
    }

    public List<Favorite> getAllFavorites(@Nullable String[] orderBys) {
        final SQLiteDatabase db = this.databaseHelper.getReadableDatabase();
        final Cursor cursor = db.query("favorite", new String[]{"type", "id", "name"}, null, null,null, null, orderBys != null ? String.join(",", orderBys) : null);
        final List<Favorite> favorites = new ArrayList<>();
        while (cursor.moveToNext()) {
            final Type type = Type.valueOf(cursor.getString(0));
            final String id = cursor.getString(1);
            final String name = cursor.getString(2);
            favorites.add(new Favorite(type, id, name));
        }
        cursor.close();
        return favorites;
    }

    public void removeAllFavorites() {
        final SQLiteDatabase db = this.databaseHelper.getWritableDatabase();
        db.delete("favorite", null, null);
        db.close();
    }

    public Boolean isFavorite(final Favorite favorite) {
        final SQLiteDatabase db = this.databaseHelper.getReadableDatabase();
        final Cursor cursor = db.query("favorite", new String[]{"id"}, "type = ? AND id = ? AND name = ?", new String[]{favorite.type.name(), favorite.id, favorite.name}, null, null, null);
        final Boolean isFavorite = 0 < cursor.getCount();
        cursor.close();
        return isFavorite;
    }

    public void closeDbConnection() {
        this.databaseHelper.close();
    }
}
