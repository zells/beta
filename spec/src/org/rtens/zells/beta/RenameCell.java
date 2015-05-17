package org.rtens.zells.beta;

import org.junit.Test;

/**
 * Renaming a cell is equivalent with deleting and re-creating it.
 */
public class RenameCell extends CellsTest {

    @Test
    public void FailIfCellDoesNotExist() {
        givenTheCell("foo");
        whenITryToRename_Of_To("bar", "foo", "baz");
        thenItShouldThrowAnException("Could not find [bar]");
    }

    @Test
    public void FailIfSiblingWithSameNameExists() {
        givenTheCell("foo.bar");
        givenTheCell("foo.baz");
        whenITryToRename_Of_To("bar", "foo", "baz");
        thenItShouldThrowAnException("[foo.baz] already exists");
    }

    @Test
    public void FailIfNewNameIsEmpty() {
        givenTheCell("foo.bar");
        whenITryToRename_Of_To("bar", "foo", "");
        thenItShouldThrowAnException("Cannot give a cell an empty name");
    }

    @Test
    public void RenameExistingCell() {
        givenTheCell("foo.bar");
        whenIRename_Of_To("bar", "foo", "baz");
        then_ShouldHave_Children("foo", 1);
        then_ShouldBeAChildOf("baz", "foo");
    }

    @Test
    public void FailIfCellIsInherited() {
        givenTheCell("foo.one");
        givenTheCell_WithTheStem("bar", "°.foo");
        whenITryToRename_Of_To("one", "bar", "two");
        thenItShouldThrowAnException("Cannot rename an inherited cell");
    }

    @Test
    public void RenameAdoptedCell() {
        givenTheCell("foo.one");
        givenTheCell_WithTheStem("bar", "°.foo");
        givenTheCell("bar.one");

        whenIRename_Of_To("one", "bar", "two");

        then_ShouldHave_Children("bar", 2);
        then_ShouldBeAChildOf("one", "bar");
        then_ShouldBeAChildOf("two", "bar");

        then_ShouldHave_Children("foo", 1);
        then_ShouldBeAChildOf("one", "foo");
    }

    private void whenITryToRename_Of_To(String name, String parent, String newName) {
        try {
            whenIRename_Of_To(name, parent, newName);
        } catch (Exception e) {
            caught = e;
        }
    }

    private void whenIRename_Of_To(String name, String parent, String newName) {
        engine.rename(Path.parse(parent).with(name), newName);
    }
}
