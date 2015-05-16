package org.rtens.zells.beta;

import java.util.ArrayList;
import java.util.List;

public class Engine {
    private final Cell base;
    private final Cell root;

    public Engine() {
        root = new Cell(null, "°", null);
        base = new Cell(root, "cell", null);
    }

    public void create(Path parent, String child) {
        if (child.isEmpty()) {
            throw new RuntimeException("Cannot create cell with empty name.");
        }
        resolve(parent).addChild(child, base);
    }

    public void setStem(Path cell, Path stem) {
        resolve(cell).setStem(resolve(stem));
    }

    public List<String> listChildren(Path path) {
        ArrayList<String> names = new ArrayList<String>();
        for (Cell child : resolve(path).getChildren()) {
            names.add(child.getName());
        }
        return names;
    }

    public Path getStem(Path cell) {
        return resolve(cell).getStem().getPath();
    }

    private Cell resolve(Path path) {
        Cell cell = root;
        for (String name : path.getParts()) {
            cell = cell.getChild(name);
        }
        return cell;
    }
}
