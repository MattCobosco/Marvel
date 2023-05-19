package core.db.models;

import core.enums.Type;

public class BaseTypeId {
    public Type type;
    public String id;

    public BaseTypeId(final Type type, final String id) {
        this.type = type;
        this.id = id;
    }
}