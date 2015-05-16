package org.rtens.zells.beta;

public abstract class Reaction {

    abstract public void execute(Cell receiver, Path message);
}
