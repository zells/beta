package org.rtens.zells.beta.model;

import org.rtens.zells.beta.Observer;
import org.rtens.zells.beta.Path;
import org.rtens.zells.beta.Reaction;

import java.util.HashSet;
import java.util.Set;

public class InheritedCell extends Cell {

    private Cell parent;
    private Cell child;
    private Set<Observer> observers = new HashSet<Observer>();

    public InheritedCell(Cell parent, Cell child) {
        super(parent, child.getName());
        this.parent = parent;
        this.child = child;
    }

    @Override
    public void receive(Path message) {
        Reaction reaction = child.getReaction();
        if (reaction != null) {
            reaction.execute(this, message);
        }
    }

    @Override
    public Set<String> getChildren() {
        return child.getChildren();
    }

    @Override
    public void observe(Observer observer) {
        super.observe(observer);
        observers.add(observer);
    }

    private Cell adopt() {
        Cell adopted = parent.add(getName(), child.getPath());
        for (Observer o : observers) {
            adopted.observe(o);
        }
        return adopted;
    }

    @Override
    public void setStem(Path stem) {
        adopt().setStem(stem);
    }

    @Override
    public void setReaction(Reaction reaction) {
        adopt().setReaction(reaction);
    }

    @Override
    public Cell add(String name, Path stem) {
        return adopt().add(name, stem);
    }

    @Override
    protected Cell findChild(String name, Set<Cell> visited) {
        return new InheritedCell(this, child.findChild(name, visited));
    }
}
