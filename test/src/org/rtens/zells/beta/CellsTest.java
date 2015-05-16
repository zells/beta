package org.rtens.zells.beta;

import org.junit.Before;

public class CellsTest {

    protected Engine engine;

    @Before
    public void before() {
        engine = new Engine();
    }

    protected void givenACell(String path) {
        Path current = new Path();
        for (String name : Path.parse(path).getParts()) {
            try {
                engine.create(current, name);
            } catch (Exception ignored) {
            }

            current = current.with(name);
        }
    }

    protected void givenACell_WithTheStem(String path, String stem) {
        givenACell(path);
        engine.setStem(Path.parse(path), Path.parse(stem));
    }
}
