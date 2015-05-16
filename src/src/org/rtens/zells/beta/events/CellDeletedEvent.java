package org.rtens.zells.beta.events;

import org.rtens.zells.beta.CellEvent;
import org.rtens.zells.beta.Path;

public class CellDeletedEvent extends CellEvent {

    public CellDeletedEvent(Path path) {
        super(path);
    }
}
