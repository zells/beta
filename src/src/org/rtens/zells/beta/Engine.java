package org.rtens.zells.beta;

import java.util.List;

public interface Engine {

    void send(Path cell, Path message);

    void observe(Path path, Observer observer);

    void create(Path parent, String name);

    void changeStem(Path cell, Path stem);

    Path getStem(Path cell);

    void delete(Path parent, String child);

    void rename(Path path, String name);

    Reaction getReaction(Path cell);

    Reaction getOwnReaction(Path cell);

    void changeReaction(Path cell, Reaction reaction);

    List<String> listChildren(Path parent);

    List<String> listOwnChildren(Path parent);
}
