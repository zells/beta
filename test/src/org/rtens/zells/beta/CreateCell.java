package org.rtens.zells.beta;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Cells are always created as a child of an existing cell. It gets a default stem cell which can later be changed.
 */
public class CreateCell extends CellsTest {

    @Test
    public void _FailIfParentDoesNotExist() {
        whenITryToCreate_In("bar", "foo");
        thenItShouldThrowAnException("Could not find [foo]");
    }

    @Test
    public void _FailIfNameIsEmpty() {
        givenTheCell("foo");
        whenITryToCreate_In("", "foo");
        thenItShouldThrowAnException("Cannot give a cell an empty name");
    }

    @Test
    public void _CreateNewChild() {
        givenTheCell("foo");
        whenICreate_In("bar", "foo");
        thenThereShouldBeACell("foo.bar");
    }

    @Test
    public void _FailIfChildAlreadyExists() {
        givenTheCell("foo.bar");
        whenITryToCreate_In("bar", "foo");
        thenItShouldThrowAnException("[foo.bar] already exists");
    }

    @Test
    public void _DefaultStemCell() {
        givenTheCell("foo");
        whenICreate_In("bar", "foo");
        thenTheStemCellOf_ShouldBe("foo.bar", "°.cell");
    }

    @Test
    @Ignore
    public void _AdoptInheritedCell() {
        givenTheCell("foo.one");
        givenACell_WithTheStem("bar", "°.foo");

        whenICreate_In("two", "bar.one");

        thenThereShouldBeACell("bar.one.two");
        thenTheStemCellOf_ShouldBe("bar.one", "°.foo.one");
        then_ShouldNotExist("foo.one.two");
    }

    @Test
    @Ignore
    public void _AdoptInheritedGrandChild() {
        givenTheCell("foo.one.two");
        givenACell_WithTheStem("bar", "°.foo");

        whenICreate_In("three", "bar.one.two");

        thenTheStemCellOf_ShouldBe("bar.one.two", "°.foo.one.two");
        thenTheStemCellOf_ShouldBe("bar.one", "°.foo.one");
    }

    private void whenITryToCreate_In(String child, String parent) {
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

    private void then_ShouldNotExist(String path) {
        try {
            engine.listChildren(Path.parse(path));
        } catch (Exception e) {
            caught = e;
        }
        Assert.assertNotNull(caught);
    }
}
