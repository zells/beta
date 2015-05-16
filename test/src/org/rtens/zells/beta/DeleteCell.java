package org.rtens.zells.beta;

import org.junit.Assert;
import org.junit.Test;

/**
 * Only own children can be deleted, not inherited ones.
 */
public class DeleteCell extends CellsTest {

    @Test
    public void _FailIfCellDoesNotExist() {
        whenITryToDelete_Of("foo", "°");
        thenItShouldThrowAnException("[°] has no child named [foo].");
    }

    @Test
    public void _DeleteExistingCell() {
        givenACell("foo.bar");
        whenIDelete_Of("bar", "foo");
        then_ShouldHave_Children("foo", 0);
    }

    @Test
    public void _FailIfCellIsInherited() {
        givenACell("foo.one");
        givenACell_WithTheStem("bar", "foo");
        whenITryToDelete_Of("one", "bar");
        thenItShouldThrowAnException("Cannot delete an inherited cell.");
    }

    private Exception caught;

    private void whenITryToDelete_Of(String child, String parent) {
        try {
            whenIDelete_Of(child, parent);
        } catch (Exception e) {
            caught = e;
        }
    }

    private void whenIDelete_Of(String child, String parent) {
        engine.delete(Path.parse(parent), child);
    }

    private void thenItShouldThrowAnException(String message) {
        Assert.assertNotNull(caught);
        Assert.assertEquals(message, caught.getMessage());
    }

    private void then_ShouldHave_Children(String path, int count) {
        Assert.assertEquals(count, engine.listChildren(Path.parse(path)).size());
    }
}
