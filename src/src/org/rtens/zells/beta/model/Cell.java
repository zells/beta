package org.rtens.zells.beta.model;

import org.rtens.zells.beta.Path;
import org.rtens.zells.beta.Reaction;

import java.util.HashSet;
import java.util.Set;

public class Cell {
    private Cell parent;
    private String name;
    private Path stem;
    private Set<Cell> children = new HashSet<Cell>();
    private Reaction reaction;

    public Cell(Cell parent, String name, Path stem) {
        this.parent = parent;
        this.name = name;
        this.stem = stem;
    }

    public Path getPath() {
        if (parent != null) {
            return parent.getPath().with(name);
        }
        return new Path(name);
    }

    public void receive(Path message) {
        Reaction reaction = getReaction();
        if (reaction != null) {
            reaction.execute(this, message);
        }
    }

    public Path getStem() {
        return stem;
    }

    public void setStem(Path stem) {
        this.stem = stem;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setReaction(Reaction reaction) {
        this.reaction = reaction;
    }

    public Reaction getReaction() {
        if (reaction == null && !stem.equals(getPath())) {
            return resolve(stem).getReaction();
        }
        return getOwnReaction();
    }

    public Reaction getOwnReaction() {
        return reaction;
    }

    public Cell add(String name, Path stem) {
        Cell child = new Cell(this, name, stem);
        children.add(child);
        return child;
    }

    public void remove(String name) {
        for (Cell child : children) {
            if (child.name.equals(name)) {
                children.remove(child);
            }
        }
    }

    public Set<String> getChildren() {
        return collectChildren(new HashSet<Cell>());
    }

    public Set<String> getOwnChildren() {
        Set<String> names = new HashSet<String>();
        for (Cell child : children) {
            names.add(child.name);
        }
        return names;
    }

    private Set<String> collectChildren(Set<Cell> visited) {
        Set<String> names = getOwnChildren();
        if (!visited.contains(this)) {
            visited.add(this);
            names.addAll(resolve(stem).collectChildren(visited));
        }
        return names;
    }

    protected Cell findChild(String name, Set<Cell> visited) {
        for (Cell child : children) {
            if (child.name.equals(name)) {
                return child;
            }
        }

        if (!visited.contains(this)) {
            visited.add(this);
            return new InheritedCell(this, doResolve(stem, visited).findChild(name, visited));
        }

        throw new RuntimeException("Could not find [" + name + "]");
    }

    public Cell resolve(Path path) {
        return doResolve(path, new HashSet<Cell>());
    }

    private Cell doResolve(Path path, Set<Cell> visited) {
        Cell cell = this;
        for (String name : path.getParts()) {
            if (name.equals("°")) {
                cell = getRoot();
            } else {
                cell = cell.findChild(name, visited);
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

    @Override
    public String toString() {
        return getPath().toString();
    }
}
