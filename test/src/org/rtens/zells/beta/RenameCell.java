package org.rtens.zells.beta;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Renaming a cell is equivalent with deleting and re-creating it.
 */
public class RenameCell extends CellsTest {

    @Test
    public void _FailIfCellDoesNotExist() {
        givenACell("foo");
        whenITryToRename_Of_To("bar", "foo", "baz");
        thenItShouldThrowAnException("Child [bar] does not exist.");
    }

    @Test
    public void _FailIfSiblingWithSameNameExists() {
        givenACell("foo.bar");
        givenACell("foo.baz");
        whenITryToRename_Of_To("bar", "foo", "baz");
        thenItShouldThrowAnException("Child [baz] already exists.");
    }

    @Test
    public void _FailIfNewNameIsEmpty() {
        givenACell("foo.bar");
        whenITryToRename_Of_To("bar", "foo", "");
        thenItShouldThrowAnException("Cannot give a cell an empty name.");
    }

    @Test
    public void _RenameExistingCell() {
        givenACell("foo.bar");
        whenIRename_Of_To("bar", "foo", "baz");
        then_ShouldHave_Children("foo", 1);
        then_ShouldBeAChildOf("baz", "foo");
    }

    @Test
    public void _FailIfCellIsInherited() {
        givenACell("foo.one");
        givenACell_WithTheStem("bar", "foo");
        whenITryToRename_Of_To("one", "bar", "two");
        thenItShouldThrowAnException("Child [one] does not exist.");
    }

    private void whenITryToRename_Of_To(String name, String parent, String newName) {
        try {
            whenIRename_Of_To(name, parent, newName);
        } catch (Exception e) {
            caught = e;
        }
    }

    private void whenIRename_Of_To(String name, String parent, String newName) {
        engine.rename(Path.parse(parent), name, newName);
    }
}
