package core.utils;

import android.content.Context;
import android.content.Intent;

import core.enums.Type;
import pl.wsei.marvel.ui.characters.CharacterCardActivity;
import pl.wsei.marvel.ui.comics.ComicCardActivity;
import pl.wsei.marvel.ui.creators.CreatorCardActivity;

public class IntentFactory {
    private final Context context;

    public IntentFactory(Context context) {
        this.context = context;
    }

    public Intent createIntent(Type type, String id, String name) {
        Intent intent = new Intent(context, getIntentClass(type));
        intent.putExtra("id", id);
        intent.putExtra("name", name);
        return intent;
    }

    public Class getIntentClass(Type type) {
        switch (type) {
            case COMIC:
                return ComicCardActivity.class;
            case CHARACTER:
                return CharacterCardActivity.class;
            case CREATOR:
                return CreatorCardActivity.class;
            default:
                return null;
        }
    }


}
