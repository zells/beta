package org.rtens.zells.beta;

abstract public class CellEvent {

    private final Path path;

    public CellEvent(Path path) {
        this.path = path;
    }

    public Path getPath() {
        return path;
    }
}
