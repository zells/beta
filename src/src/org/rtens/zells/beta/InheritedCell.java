package org.rtens.zells.beta;

public class InheritedCell extends Cell {

    private Cell parent;
    private Cell child;

    public InheritedCell(Cell parent, Cell child) {
        super(parent, child.getName(), child.getStem());
        this.parent = parent;
        this.child = child;
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
    public void add(String name, Path stem) {
        parent.add(getName(), child.getPath());
        parent.resolve(new Path(getName())).add(name, stem);
    }
}
