package org.rtens.zells.beta;

import org.junit.Assert;
import org.junit.Before;

public class CellsTest {

    protected Engine engine;

    protected Exception caught;

    @Before
    public void before() {
        engine = new Engine();
    }

    protected void givenTheCell(String path) {
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
        givenTheCell(path);
        engine.setStem(Path.parse(path), Path.parse(stem));
    }

    protected void thenItShouldThrowAnException(String message) {
        Assert.assertNotNull(caught);
        Assert.assertEquals(message, caught.getMessage());
    }

    protected void then_ShouldHave_Children(String path, int count) {
        Assert.assertEquals(count, engine.listChildren(Path.parse(path)).size());
    }

    protected void then_ShouldBeAChildOf(String name, String parent) {
        Assert.assertTrue(engine.listChildren(Path.parse(parent)).contains(name));
    }
}
