package org.rtens.zells.beta;

import org.junit.Ignore;
import org.junit.Test;

public class RenameCell {

    @Test
    @Ignore
    public void _FailIfCellDoesNotExist() {
//        whenIRename_To("foo", "bar");
//        thenItShouldThrowAnException("[foo] does not exist.");
    }

    @Test
    @Ignore
    public void _FailIfSiblingWithSameNameExists() {
//        givenACell("foo/bar");
//        givenACell("foo/baz");
//        whenIRename_To("foo/bar", "baz");
//        thenItShouldThrowAnException("[foo] already has a child named [baz].");
    }

    @Test
    @Ignore
    public void _FailIfNewNameIsEmpty() {
//        givenACell("foo");
//        whenIRename_To("foo", "");
//        thenItShouldThrowAnException("Cannot give a cell an empty name.");
    }

    @Test
    @Ignore
    public void _RenameExistingCell() {
//        givenACell("foo/bar");
//        whenIRename_To("foo/bar", "baz");
//        then_ShouldNotExist("foo/bar");
//        thenThereShouldBeACell("foo/baz");
    }

    @Test
    @Ignore
    public void _FailIfCellIsInherited() {
//        givenACell("foo/one");
//        givenACell_WithTheStem("bar", "/foo");
//        whenIRename_To("bar/one", "two");
//        thenItShouldThrowAnException("Cannot rename an inherited cell.");
    }
}
