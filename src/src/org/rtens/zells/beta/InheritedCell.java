package org.rtens.zells.beta;

public class InheritedCell extends Cell {

    public InheritedCell(Cell parent, Cell child) {
        super(parent, child.getName(), child.getStem());
    }

    @Override
    public void setName(String name) {
        throw new RuntimeException("Cannot rename an inherited cell");
    }

    @Override
    public void setStem(Path stem) {
        getParent().add(getName(), stem);
    }
}
