package org.rtens.zells.beta;

public interface Reaction {

    void execute(Cell receiver, Path message);
}
