package core.db.models;

import core.enums.Type;

public class Favorite extends BaseTypeId {
    public Favorite(Type type, String id) {
        super(type, id);
    }
}

