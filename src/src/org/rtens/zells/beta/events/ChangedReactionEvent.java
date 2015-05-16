package org.rtens.zells.beta.events;

import org.rtens.zells.beta.CellEvent;
import org.rtens.zells.beta.Path;

public class ChangedReactionEvent extends CellEvent {

    public ChangedReactionEvent(Path path) {
        super(path);
    }
}
