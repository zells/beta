package org.rtens.zells.beta;

import org.junit.Test;

/**
 * Only own children can be deleted, not inherited ones.
 */
public class DeleteCell extends CellsTest {

    @Test
    public void FailIfCellDoesNotExist() {
        whenITryToDelete_Of("foo", "°");
        thenItShouldThrowAnException("Could not find [foo]");
    }

    @Test
    public void DeleteExistingCell() {
        givenTheCell("foo.bar");
        whenIDelete_Of("bar", "foo");
        then_ShouldHave_Children("foo", 0);
    }

    @Test
    public void FailIfCellIsInherited() {
        givenTheCell("foo.one");
        givenTheCell_WithTheStem("bar", "°.foo");
        whenITryToDelete_Of("one", "bar");
        thenItShouldThrowAnException("[bar.one] is inherited");
    }

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
}
