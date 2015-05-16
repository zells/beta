package org.rtens.zells.beta;

import java.util.List;

public class AbsolutePath extends Path {

    public AbsolutePath(String... parts) {
        super(parts);
    }

    public AbsolutePath(List<String> parts) {
        super(parts);
    }

    @Override
    public String toString() {
        return "°." + super.toString();
    }
}
