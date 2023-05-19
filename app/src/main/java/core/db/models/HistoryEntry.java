package core.db.models;

import core.enums.Type;

public class HistoryEntry extends BaseTypeId {
    public final String name;
    public final long timestamp;

    public HistoryEntry(Type type, String id, String name, long timestamp) {
        super(type, id);
        this.name = name;
        this.timestamp = timestamp;
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

    public long getTimestamp() {
        return this.timestamp;
    }
}
