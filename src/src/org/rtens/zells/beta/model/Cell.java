package org.rtens.zells.beta.model;

import org.rtens.zells.beta.Observer;
import org.rtens.zells.beta.Path;
import org.rtens.zells.beta.Reaction;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Cell implements Observer {

    private Cell parent;
    private String name;
    private Path stem;
    private Set<Cell> children = new HashSet<Cell>();
    private Reaction reaction;
    private Set<Observer> observers = new HashSet<Observer>();
    private Map<String, InheritedCell> inheritedChildren = new HashMap<String, InheritedCell>();

    public Cell(Cell parent, String name) {
        this.parent = parent;
        this.name = name;
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
        doSetStem(stem);
        fire();
    }

    protected void doSetStem(Path stem) {
        if (this.stem != null) {
            try {
                resolve(this.stem).observers.remove(this);
            } catch (Exception ignored) {
            }
        }

        this.stem = stem;

        if (stem != null) {
            resolve(stem).observe(this);
        }
    }

    public String getName() {
        return name;
    }

    public void setReaction(Reaction reaction) {
        this.reaction = reaction;
        fire();
    }

    public Reaction getReaction() {
        if (reaction == null && stem != null && !stem.equals(getPath())) {
            return resolve(stem).getReaction();
        }
        return getOwnReaction();
    }

    public Reaction getOwnReaction() {
        return reaction;
    }

    public Cell add(String name, Path stem) {
        Cell child = doAdd(name, stem);
        fire();
        return child;
    }

    protected Cell doAdd(String name, Path stem) {
        final Cell child = new Cell(this, name);
        child.doSetStem(stem);
        children.add(child);
        return child;
    }

    public void remove(String name) {
        for (Cell child : children) {
            if (child.name.equals(name)) {
                children.remove(child);
                fire();
                return;
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
        if (!visited.contains(this) && stem != null) {
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

        if (!visited.contains(this) && stem != null) {
            visited.add(this);
            return createInheritedCell(name, visited);
        }

        throw new RuntimeException("Could not find [" + name + "]");
    }

    private InheritedCell createInheritedCell(String name, Set<Cell> visited) {
        if (!inheritedChildren.containsKey(name)) {
            inheritedChildren.put(name, new InheritedCell(this, doResolve(stem, visited).findChild(name, visited)));
        }
        return inheritedChildren.get(name);
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

    public void observe(Observer observer) {
        observers.add(observer);
    }

    private void fire() {
        fire(new HashSet<Cell>());
    }

    private void fire(Set<Cell> cells) {
        for (Observer o : observers) {
            if (o instanceof Cell) {
                ((Cell) o).stemChanged(cells);
            } else {
                o.cellChanged(getPath());
            }
        }
    }

    private void stemChanged(Set<Cell> cells) {
        if (cells.contains(this)) {
            return;
        }
        cells.add(this);
        fire(cells);
    }

    @Override
    public void cellChanged(Path cell) {
        fire();
    }
}
