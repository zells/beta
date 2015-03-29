package org.rtens.zells.beta;

import org.junit.Ignore;
import org.junit.Test;

/**
 * Only own children can be deleted, not inherited ones.
 */
public class DeleteCell {

    @Test
    @Ignore
    public void _FailIfCellDoesNotExist() {
//        whenIDelete("foo");
//        thenItShouldThrowAnException("[foo] does not exist.");
    }

    @Test
    @Ignore
    public void _DeleteExistingCell() {
//        givenACell("foo/bar");
//        whenIDelete("foo/bar");
//        then_ShouldNotExist("foo/bar");
//        then_ShouldHave_Children("foo", 0);
    }

    @Test
    @Ignore
    public void _FailIfCellIsInherited() {
//        givenACell("foo/one");
//        givenACell_WithTheStem("bar", "/foo");
//        whenIDelete("bar/one");
//        thenItShouldThrowAnException("Cannot delete an inherited cell.");
    }
}
