package core.db.models;

import core.enums.Type;

public class HistoryEntry extends BaseTypeId {
    public final long timestamp;
    public HistoryEntry(Type type, String id, long timestamp) {
        super(type, id);
        this.timestamp = timestamp;
    }

    public Type getType() {
        return this.type;
    }

    public String getId() {
        return this.id;
    }

    public long getTimestamp() {
        return this.timestamp;
    }
}
