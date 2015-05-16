package org.rtens.zells.beta.events;

import org.rtens.zells.beta.CellEvent;
import org.rtens.zells.beta.Path;

public class CellRenamedEvent extends CellEvent {

    public CellRenamedEvent(Path path) {
        super(path);
    }
}
