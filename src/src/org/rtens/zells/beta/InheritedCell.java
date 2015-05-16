package org.rtens.zells.beta;

import java.util.Set;

public class InheritedCell extends Cell {

    private Cell parent;
    private Cell child;

    public InheritedCell(Cell parent, Cell child) {
        super(parent, child.getName(), child.getStem());
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
    public void setName(String name) {
        throw new RuntimeException("Cannot rename an inherited cell");
    }

    @Override
    public void setStem(Path stem) {
        parent.add(getName(), stem);
    }

    @Override
    public void setReaction(Reaction reaction) {
        parent.add(getName(), child.getPath()).setReaction(reaction);
    }

    @Override
    public Cell add(String name, Path stem) {
        return parent.add(getName(), child.getPath()).add(name, stem);
    }

    @Override
    protected Cell findChild(String name, Set<Cell> visited) {
        return new InheritedCell(this, child.findChild(name, visited));
    }
}
