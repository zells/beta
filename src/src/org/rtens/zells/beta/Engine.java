package org.rtens.zells.beta;

import java.util.*;

public class Engine {
    private final Cell root;
    private final Path defaultStem = new AbsolutePath("cell");

    public Engine() {
        root = new Cell(new AbsolutePath());
        root.add("cell", null);
    }

    public void create(Path parent, String child) {
        if (child.isEmpty()) {
            throw new RuntimeException("Cannot create cell with empty name.");
        }
        resolve(parent).add(child, defaultStem);
    }

    public void setStem(Path cell, Path stem) {
        resolve(cell).setStem(stem);
    }

    public List<String> listChildren(Path path) {
        Set<String> children = getChildren(resolve(path), new HashSet<Path>());

        return sortStrings(children);
    }

    private Set<String> getChildren(Cell cell, Set<Path> stems) {
        Set<String> children = new HashSet<String>(cell.getChildren());

        Path stem = cell.getStem();
        if (stem != null) {
            guardAgainstInheritanceLoop(stem, stems);
            children.addAll(getChildren(resolve(stem, cell), stems));
        }

        return children;
    }

    private void guardAgainstInheritanceLoop(Path stem, Set<Path> stems) {
        if (stems.contains(stem)) {
            throw new RuntimeException("Inheritance loop detected.");
        }
        stems.add(stem);
    }

    private List<String> sortStrings(Set<String> children) {
        ArrayList<String> list = new ArrayList<String>(children);
        Collections.sort(list);
        return list;
    }

    public Path getStem(Path cell) {
        return resolve(cell).getStem();
    }

    public void delete(Path parent, String child) {
        resolve(parent).remove(child);
    }

    public void rename(Path parent, String oldName, String newName) {
        resolve(parent).rename(oldName, newName);
    }

    private Cell resolve(Path path) {
        return resolve(path, root);
    }

    private Cell resolve(Path path, Cell cell) {
        if (path instanceof AbsolutePath) {
            cell = root;
        }

        for (String name : path.getParts()) {
            try {
                cell = cell.getChild(name);
            } catch (Exception e) {
                cell = resolve(cell.getStem()).getChild(name);
            }
        }
        return cell;
    }
}
