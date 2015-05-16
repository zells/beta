package org.rtens.zells.beta;

import java.util.*;

public class Engine {
    private final Cell root;
    private final Path defaultStem = new Path("°", "cell");

    public Engine() {
        root = new Cell(null, "°", null);
        root.add("cell", null);
    }

    public void create(Path parent, String child) {
        resolve(parent).add(child, defaultStem);
    }

    public void setStem(Path cell, Path stem) {
        resolve(cell).setStem(stem);
    }

    public Path getStem(Path cell) {
        return resolve(cell).getStem();
    }

    public void delete(Path parent, String child) {
        resolve(parent).remove(child);
    }

    public void rename(Path path, String name) {
        resolve(path).setName(name);
    }

    public List<String> listChildren(Path path) {
        return sortStrings(resolve(path).getChildren());
    }

    private List<String> sortStrings(Set<String> children) {
        ArrayList<String> list = new ArrayList<String>(children);
        Collections.sort(list);
        return list;
    }

    private Cell resolve(Path path) {
        return resolve(path, root);
    }

    private Cell resolve(Path path, Cell cell) {
        return cell.resolve(path);
    }
}
