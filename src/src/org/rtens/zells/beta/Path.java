package org.rtens.zells.beta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Path {
    private List<String> parts;

    public Path(List<String> parts) {
        this.parts = parts;
    }

    public Path(String... parts) {
        this(Arrays.asList(parts));
    }

    public Path() {
        this(new ArrayList<String>());
    }

    public List<String> getParts() {
        return parts;
    }

    public Path with(String child) {
        List<String> childPath = new ArrayList<String>(parts);
        childPath.add(child);

        return new Path(childPath);
    }

    public Path parent() {
        return new Path(parts.subList(0, parts.size() - 1));
    }

    public String last() {
        return parts.get(parts.size() - 1);
    }

    public static Path parse(String path) {
        return new Path(Arrays.asList(path.split("\\.")));
    }

    @Override
    public String toString() {
        return String.join(".", parts);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Path && ((Path) obj).parts.equals(parts);
    }
}
