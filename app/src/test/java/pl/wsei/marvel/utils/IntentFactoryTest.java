package pl.wsei.marvel.utils;

import android.content.Context;
import android.content.Intent;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import androidx.test.core.app.ApplicationProvider;

import core.enums.Type;
import core.utils.IntentFactory;
import pl.wsei.marvel.ui.characters.CharacterCardActivity;
import pl.wsei.marvel.ui.comics.ComicCardActivity;
import pl.wsei.marvel.ui.creators.CreatorCardActivity;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class IntentFactoryTest {

    private Context context;

    @Before
    public void setup() {
        context = ApplicationProvider.getApplicationContext();
    }

    @Test
    public void testCreateIntentForComic() {
        IntentFactory intentFactory = new IntentFactory(context);
        Intent intent = intentFactory.createIntent(Type.COMIC, "1", "TestComic");
        assertNotNull(intent);
        assertEquals("1", intent.getStringExtra("id"));
        assertEquals("TestComic", intent.getStringExtra("name"));
        assertEquals(ComicCardActivity.class.getCanonicalName(), intent.getComponent().getClassName());
    }

    @Test
    public void testCreateIntentForCharacter() {
        IntentFactory intentFactory = new IntentFactory(context);
        Intent intent = intentFactory.createIntent(Type.CHARACTER, "2", "TestCharacter");
        assertNotNull(intent);
        assertEquals("2", intent.getStringExtra("id"));
        assertEquals("TestCharacter", intent.getStringExtra("name"));
        assertEquals(CharacterCardActivity.class.getCanonicalName(), intent.getComponent().getClassName());
    }

    @Test
    public void testCreateIntentForCreator() {
        IntentFactory intentFactory = new IntentFactory(context);
        Intent intent = intentFactory.createIntent(Type.CREATOR, "3", "TestCreator");
        assertNotNull(intent);
        assertEquals("3", intent.getStringExtra("id"));
        assertEquals("TestCreator", intent.getStringExtra("name"));
        assertEquals(CreatorCardActivity.class.getCanonicalName(), intent.getComponent().getClassName());
    }
}

