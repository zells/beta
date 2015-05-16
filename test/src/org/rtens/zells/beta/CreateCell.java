package org.rtens.zells.beta;

import org.junit.Assert;
import org.junit.Test;

/**
 * Cells are always created as a child of an existing cell. It gets a default stem cell which can later be changed.
 */
public class CreateCell extends CellsTest {

    @Test
    public void _FailIfParentDoesNotExist() {
        whenITryToCreate_Under("bar", "foo");
        thenItShouldThrowAnException("Child [foo] does not exist.");
    }

    @Test
    public void _FailIfNameIsEmpty() {
        givenACell("foo");
        whenITryToCreate_Under("", "foo");
        thenItShouldThrowAnException("Cannot create cell with empty name.");
    }

    @Test
    public void _CreateNewChild() {
        givenACell("foo");
        whenICreate_In("bar", "foo");
        thenThereShouldBeACell("foo.bar");
    }

    @Test
    public void _FailIfChildAlreadyExists() {
        givenACell("foo.bar");
        whenITryToCreate_Under("bar", "foo");
        thenItShouldThrowAnException("Child [bar] already exists.");
    }

    @Test
    public void _DefaultStemCell() {
        givenACell("foo");
        whenICreate_In("bar", "foo");
        thenTheStemCellOf_ShouldBe("foo.bar", "°.cell");
    }

    private void whenITryToCreate_Under(String child, String parent) {
        try {
            whenICreate_In(child, parent);
        } catch (Exception e) {
            caught = e;
        }
    }

    private void whenICreate_In(String child, String parent) {
        engine.create(Path.parse(parent), child);
    }

    private void thenThereShouldBeACell(String path) {
        engine.listChildren(Path.parse(path));
    }

    private void thenTheStemCellOf_ShouldBe(String path, String stem) {
        Assert.assertEquals(Path.parse(stem), engine.getStem(Path.parse(path)));
    }
}
