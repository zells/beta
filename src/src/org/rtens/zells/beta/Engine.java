package org.rtens.zells.beta;

import org.rtens.zells.beta.events.*;

import java.util.*;

public class Engine {
    private final Cell root;
    private final Path defaultStem = new Path("°", "cell");
    private Set<Observer> observers = new HashSet<Observer>();

    public Engine() {
        root = new Cell(null, "°", defaultStem);
        root.add("cell", defaultStem);
    }

    public void send(Path cell, Path message) {
        resolve(cell).receive(message);
    }

    public void observe(Observer observer) {
        observers.add(observer);
    }

    private void fire(CellEvent event) {
        for (Observer o : observers) {
            o.on(event);
        }
    }

    public void create(Path parent, String name) {
        guardName(name);
        guardNotOwnChild(parent, name);

        resolve(parent).add(name, defaultStem);
        fire(new CellCreatedEvent(parent.with(name)));
    }

    public void changeStem(Path cell, Path stem) {
        resolve(cell).setStem(stem);
        fire(new ChangedStemEvent(cell));
    }

    public Path getStem(Path cell) {
        return resolve(cell).getStem();
    }

    public void delete(Path parent, String child) {
        guardExisting(parent.with(child));
        guardOwnChild(parent, child);

        resolve(parent).remove(child);
        fire(new CellDeletedEvent(parent.with(child)));
    }

    public void rename(Path path, String name) {
        guardName(name);
        guardNotExisting(path.parent().with(name));

        resolve(path).setName(name);
        fire(new CellRenamedEvent(path));
        fire(new CellDeletedEvent(path));
        fire(new CellCreatedEvent(path.parent().with(name)));
    }

    public Reaction getReaction(Path cell) {
        return resolve(cell).getReaction();
    }

    public void changeReaction(Path cell, Reaction reaction) {
        resolve(cell).setReaction(reaction);
        fire(new ChangedReactionEvent(cell));
    }

    public Object getOwnReaction(Path cell) {
        return resolve(cell).getOwnReaction();
    }

    public List<String> listChildren(Path parent) {
        return sortStrings(resolve(parent).getChildren());
    }

    public List<String> listOwnChildren(Path parent) {
        return sortStrings(resolve(parent).getOwnChildren());
    }

    private List<String> sortStrings(Set<String> children) {
        ArrayList<String> list = new ArrayList<String>(children);
        Collections.sort(list);
        return list;
    }

    private Cell resolve(Path path) {
        return resolve(path, root);
    }

    private Cell resolve(Path path, Cell cell) {
        return cell.resolve(path);
    }

    private void guardName(String name) {
        if (name.isEmpty()) {
            throw new RuntimeException("Cannot give a cell an empty name");
        }
    }

    private void guardExisting(Path path) {
        resolve(path);
    }

    private void guardNotExisting(final Path path) {
        guardNot(new Runnable() {
            @Override
            public void run() {
                guardExisting(path);
            }
        }, "[" + path + "] already exists");
    }

    private void guardOwnChild(Path parent, String name) {
        if (!resolve(parent).getOwnChildren().contains(name)) {
            throw new RuntimeException("[" + parent.with(name) + "] is inherited");
        }
    }

    private void guardNotOwnChild(final Path parent, final String name) {
        guardNot(new Runnable() {
            @Override
            public void run() {
                guardOwnChild(parent, name);
            }
        }, "[" + parent.with(name) + "] already exists");
    }

    private void guardNot(Runnable runnable, String message) {
        try {
            runnable.run();
        } catch (Exception e) {
            return;
        }
        throw new RuntimeException(message);
    }
}
