package org.rtens.zells.beta;

import jdk.nashorn.internal.objects.NativeDate;

import java.util.*;

public class Cell {
    private Cell parent;
    private String name;
    private Path stem;
    private Set<Cell> children = new HashSet<Cell>();

    public Cell(Cell parent, String name, Path stem) {
        this.parent = parent;
        setName(name);
        setStem(stem);
    }

    public Path getStem() {
        return stem;
    }

    public void add(String name, Path stem) {
        guardChildExistsNot(name);
        children.add(new Cell(this, name, stem));
    }

    public Set<String> getChildren() {
        return collectChildren(new HashSet<Cell>());
    }

    private Set<String> collectChildren(Set<Cell> visited) {
        Set<String> names = getOwnChildren();
        if (stem != null) {
            guardAgainstInheritanceLoop(visited);
            names.addAll(resolve(stem).collectChildren(visited));
        }
        return names;
    }

    private void guardAgainstInheritanceLoop(Set<Cell> visited) {
        if (visited.contains(this)) {
            throw new RuntimeException("Inheritance loop detected.");
        }
        visited.add(this);
    }

    private Set<String> getOwnChildren() {
        Set<String> names = new HashSet<String>();
        for (Cell child : children) {
            names.add(child.name);
        }
        return names;
    }

    public void setStem(Path stem) {
        this.stem = stem;
    }

    public void remove(String name) {
        children.remove(findChild(name));
    }

    private Cell findChild(String name) {
        for (Cell child : children) {
            if (child.name.equals(name)) {
                return child;
            }
        }
        throw new RuntimeException("[" + this + "] does not have the child [" + name + "].");
    }

    private Cell findInheritedChild(String name) {
        try {
            return findChild(name);
        } catch (Exception e) {
            try {
                if (stem == null) {
                    throw e;
                }
                return resolve(stem).findInheritedChild(name);
            } catch (Exception e1) {
                throw new RuntimeException("Could not find [" + name + "] in [" + this + "].", e);
            }
        }
    }

    private void guardChildExistsNot(String name) {
        try {
            findChild(name);
        } catch (Exception ignored) {
            return;
        }

        throw new RuntimeException("[" + this + "] already has a child [" + name + "].");
    }

    public void setName(String name) {
        if (name.isEmpty()) {
            throw new RuntimeException("Cannot give a cell an empty name.");
        }
        if (parent != null) {
            parent.guardChildExistsNot(name);
        }

        this.name = name;
    }

    public Cell resolve(Path path) {
        Cell cell = this;
        for (String name : path.getParts()) {
            if (name.equals("°")) {
                cell = getRoot();
            } else {
                cell = cell.findInheritedChild(name);
            }
        }
        return cell;
    }

    private Cell getRoot() {
        if (parent != null) {
            return parent.getRoot();
        }
        return this;
    }

    public Path getPath() {
        if (parent != null) {
            return parent.getPath().with(name);
        }
        return new Path(name);
    }

    @Override
    public String toString() {
        return getPath().toString();
    }

    public String getName() {
        return name;
    }
}
