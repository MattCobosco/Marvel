package pl.wsei.marvel;

import core.enums.Type;
import pl.wsei.marvel.R;

public class TypeToIconDictionary {
    public static int getIcon(Type type) {
        switch (type) {
            case CHARACTER:
                return R.drawable.star_half_black_24;
            case COMIC:
                return R.drawable.collections_bookmark_24;
            case CREATOR:
                return R.drawable.recent_actors_24;
            default:
                return R.drawable.placeholder_24;
        }
    }
}
