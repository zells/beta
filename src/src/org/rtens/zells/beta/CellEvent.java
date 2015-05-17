package org.rtens.zells.beta;

public class CellEvent {

    public enum Type {
        Created,
        Deleted,
        ChangedReaction,
        ChangedStem
    }

    private Type type;
    private Path path;

    public CellEvent(Type type, Path path) {
        this.type = type;
        this.path = path;
    }

    public Path getPath() {
        return path;
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        return type + " " + path;
    }
}
