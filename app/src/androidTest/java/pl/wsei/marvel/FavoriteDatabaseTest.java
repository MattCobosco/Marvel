package pl.wsei.marvel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import android.content.Context;
import androidx.test.core.app.ApplicationProvider;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import core.db.FavoriteTableManager;
import core.db.models.Favorite;
import core.enums.Type;

public class FavoriteDatabaseTest {
    private FavoriteTableManager favoriteTableManager;
    List<Favorite> favorites;

    @Before
    public void setup() {
        Context context = ApplicationProvider.getApplicationContext();
        favoriteTableManager = new FavoriteTableManager(context);
        favorites = favoriteTableManager.getAllFavorites(null);
        favoriteTableManager.removeAllFavorites();
    }

    @Test
    public void testAddFavorite() {
        Favorite favorite = new Favorite(Type.CHARACTER, "123", "Character 123");
        favoriteTableManager.addFavorite(favorite);

        List<Favorite> favorites = favoriteTableManager.getAllFavorites(null);
        assertEquals(1, favorites.size());

        Favorite savedFavorite = favorites.get(0);
        assertEquals(Type.CHARACTER, savedFavorite.getType());
        assertEquals("123", savedFavorite.getId());
        assertEquals("Character 123", savedFavorite.getName());
    }

    @Test
    public void testRemoveFavorite() {
        Favorite favorite = new Favorite(Type.CHARACTER, "123", "Character 123");
        favoriteTableManager.addFavorite(favorite);

        boolean isFavoriteBeforeRemoval = favoriteTableManager.isFavorite(favorite);
        assertTrue(isFavoriteBeforeRemoval);

        favoriteTableManager.removeFavorite(favorite);

        boolean isFavoriteAfterRemoval = favoriteTableManager.isFavorite(favorite);
        assertFalse(isFavoriteAfterRemoval);
    }

    @Test
    public void testGetAllFavorites() {
        Favorite favorite1 = new Favorite(Type.CHARACTER, "123", "Character 123");
        Favorite favorite2 = new Favorite(Type.CREATOR, "456", "Creator 456");

        favoriteTableManager.addFavorite(favorite1);
        favoriteTableManager.addFavorite(favorite2);

        List<Favorite> favorites = favoriteTableManager.getAllFavorites(null);
        assertEquals(2, favorites.size());

        Favorite savedFavorite1 = favorites.get(0);
        assertEquals(Type.CHARACTER, savedFavorite1.getType());
        assertEquals("123", savedFavorite1.getId());
        assertEquals("Character 123", savedFavorite1.getName());

        Favorite savedFavorite2 = favorites.get(1);
        assertEquals(Type.CREATOR, savedFavorite2.getType());
        assertEquals("456", savedFavorite2.getId());
        assertEquals("Creator 456", savedFavorite2.getName());
    }

    @Test
    public void testIsFavorite() {
        Favorite favorite = new Favorite(Type.CHARACTER, "123", "Character 123");
        favoriteTableManager.addFavorite(favorite);

        boolean isFavorite = favoriteTableManager.isFavorite(favorite);
        assertTrue(isFavorite);

        Favorite nonExistingFavorite = new Favorite(Type.CREATOR, "456", "Creator 456");
        boolean isNonExistingFavorite = favoriteTableManager.isFavorite(nonExistingFavorite);
        assertFalse(isNonExistingFavorite);
    }

    @After
    public void cleanup() {
        favoriteTableManager.removeAllFavorites();
        for (Favorite favorite : favorites) {
            favoriteTableManager.addFavorite(favorite);
        }
        favoriteTableManager.closeDbConnection();
    }
}
