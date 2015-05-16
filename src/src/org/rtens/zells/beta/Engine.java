package org.rtens.zells.beta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class Engine {
    private final Cell root;
    private final Path defaultStem = new Path("°", "cell");

    public Engine() {
        root = new Cell(null, "°", defaultStem);
        root.add("cell", defaultStem);
    }

    public void create(Path parent, String name) {
        guardName(name);
        guardNotOwnChild(parent, name);

        resolve(parent).add(name, defaultStem);
    }

    public void setStem(Path cell, Path stem) {
        resolve(cell).setStem(stem);
    }

    public Path getStem(Path cell) {
        return resolve(cell).getStem();
    }

    public void delete(Path parent, String child) {
        guardExisting(parent.with(child));
        guardOwnChild(parent, child);

        resolve(parent).remove(child);
    }

    public void rename(Path path, String name) {
        guardName(name);
        guardNotExisting(path.parent().with(name));

        resolve(path).setName(name);
    }

    public List<String> listChildren(Path parent) {
        return sortStrings(resolve(parent).getChildren());
    }

    public List<String> listOwnChildren(Path parent) {
        return sortStrings(resolve(parent).getOwnChildren());
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

    private void guardName(String name) {
        if (name.isEmpty()) {
            throw new RuntimeException("Cannot give a cell an empty name");
        }
    }

    private void guardExisting(Path path) {
        resolve(path);
    }

    private void guardNotExisting(final Path path) {
        guardNot(new Runnable() {
            @Override
            public void run() {
                guardExisting(path);
            }
        }, "[" + path + "] already exists");
    }

    private void guardOwnChild(Path parent, String name) {
        if (!resolve(parent).getOwnChildren().contains(name)) {
            throw new RuntimeException("[" + parent.with(name) + "] is inherited");
        }
    }

    private void guardNotOwnChild(final Path parent, final String name) {
        guardNot(new Runnable() {
            @Override
            public void run() {
                guardOwnChild(parent, name);
            }
        }, "[" + parent.with(name) + "] already exists");
    }

    private void guardNot(Runnable runnable, String message) {
        try {
            runnable.run();
        } catch (Exception e) {
            return;
        }
        throw new RuntimeException(message);
    }
}
