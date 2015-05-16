package org.rtens.zells.beta.events;

import org.rtens.zells.beta.CellEvent;
import org.rtens.zells.beta.Path;

public class ChangedStemEvent extends CellEvent {

    public ChangedStemEvent(Path path) {
        super(path);
    }
}
