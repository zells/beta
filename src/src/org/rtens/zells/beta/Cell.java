package org.rtens.zells.beta;

import java.util.*;

public class Cell {
    private Path stem;
    private Map<String, Cell> children = new HashMap<String, Cell>();

    public Cell(Path stem) {
        this.stem = stem;
    }

    public Path getStem() {
        return stem;
    }

    public Cell getChild(String name) {
        guardChildExists(name);
        return children.get(name);
    }

    public void add(String name, Path stem) {
        guardChildExistsNot(name);
        children.put(name, new Cell(stem));
    }

    private boolean hasOwnChild(String name) {
        return children.containsKey(name);
    }

    public Set<String> getChildren() {
        return children.keySet();
    }

    public void setStem(Path stem) {
        this.stem = stem;
    }

    public void remove(String name) {
        guardChildExists(name);
        children.remove(name);
    }

    private void guardChildExists(String name) {
        if (!hasOwnChild(name)) {
            throw new RuntimeException("Child [" + name + "] does not exist.");
        }
    }

    private void guardChildExistsNot(String name) {
        if (hasOwnChild(name)) {
            throw new RuntimeException("Child [" + name + "] already exists.");
        }
    }

    public void rename(String child, String to) {
        if (to.isEmpty()) {
            throw new RuntimeException("Cannot give a cell an empty name.");
        }
        guardChildExists(child);
        guardChildExistsNot(to);
        children.put(to, children.get(child));
        children.remove(child);
    }
}
