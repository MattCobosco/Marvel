package core.db.models;

import core.enums.Type;

public class Favorite extends BaseTypeId {
    public String name;
    public Favorite(Type type, String id, String name) {
        super(type, id);
        this.name = name;
    }

    public Type getType() {
        return this.type;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }
}

