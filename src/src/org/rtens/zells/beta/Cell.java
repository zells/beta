package org.rtens.zells.beta;

import java.util.*;

public class Cell {
    private Cell parent;
    private String name;
    private Cell stem;
    private List<Cell> children = new ArrayList<Cell>();

    public Cell(Cell parent, String name, Cell base) {
        this.parent = parent;
        this.name = name;
        this.stem = base;
    }

    public Cell getStem() {
        return stem;
    }

    public Cell getChild(String name) {
        if (name.equals("°")) {
            return getRoot();
        }
        for (Cell child : getChildren()) {
            if (child.name.equals(name)) {
                return child;
            }
        }
        throw new RuntimeException("[" + getPath() + "] has no child named [" + name + "].");
    }

    public Cell addChild(String name, Cell stem) {
        if (hasOwnChild(name)) {
            throw new RuntimeException("[" + getPath() + "] already has a child named [" + name + "].");
        }

        Cell child = new Cell(this, name, stem);
        children.add(child);
        return child;
    }

    private boolean hasOwnChild(String name) {
        for (Cell child : children) {
            if (child.name.equals(name)) {
                return true;
            }
        }
        return false;
    }

    public List<Cell> getChildren() {
        Map<String, Cell> all = new HashMap<String, Cell>();
        if (stem != null) {
            for (Cell inherited : stem.getChildren()) {
                all.put(inherited.name, inherited);
            }
        }

        for (Cell child : children) {
            all.put(child.name, child);
        }

        List<Cell> sorted = new ArrayList<Cell>(all.values());
        sorted.sort(new Comparator<Cell>() {
            @Override
            public int compare(Cell o1, Cell o2) {
                return o1.name.compareTo(o2.name);
            }
        });
        return sorted;
    }

    public Path getPath() {
        return (parent != null ? parent.getPath() : new Path()).with(name);
    }

    @Override
    public String toString() {
        return getPath().toString();
    }

    public void setStem(Cell stem) {
        this.stem = stem;
    }

    public Cell getRoot() {
        return (parent == null ? this : parent.getRoot());
    }

    public String getName() {
        return name;
    }

    public void remove(String name) {
        if (!hasOwnChild(name)) {
            throw new RuntimeException("Cannot delete an inherited cell.");
        }
        children.remove(getChild(name));
    }
}
