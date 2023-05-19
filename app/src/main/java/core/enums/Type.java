package core.enums;

public enum Type {
    CHARACTER("character"),
    COMIC("comic"),
    CREATOR("creator");

    public final String type;

    Type(final String type) {
        this.type = type;
    }
}
