package org.rtens.zells.beta;

import org.rtens.zells.beta.model.Cell;

public interface Reaction {

    void execute(Cell receiver, Path message);
}
