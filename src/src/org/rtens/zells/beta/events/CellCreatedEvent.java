package org.rtens.zells.beta.events;

import org.rtens.zells.beta.CellEvent;
import org.rtens.zells.beta.Path;

public class CellCreatedEvent extends CellEvent {

    public CellCreatedEvent(Path path) {
        super(path);
    }
}
