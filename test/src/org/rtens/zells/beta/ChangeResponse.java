package org.rtens.zells.beta;

import org.junit.Ignore;
import org.junit.Test;

/**
 * Responses are always completely overwritten with a new Response instance. Which instances are supported
 * depends on the actual model.
 */
public class ChangeResponse {

    @Test
    @Ignore
    public void _FailIfCellDoesNotExist() {
//        whenISetTheResponseOf_To("foo", "A new response");
//        thenItShouldThrowAnException("[foo] does not exist.");
    }

    @Test
    @Ignore
    public void _ChangeResponse() {
//        givenTheCell("foo");
//        whenISetTheResponseOf_To("foo", "A new response");
//        thenTheResponseOf_ShouldBe("foo", "A new response");
    }

    @Test
    @Ignore
    public void _AdoptInheritedCell() {
//        givenTheCell("foo/one");
//        givenTheResponseOf_Is("foo/one", "Old response");
//        givenACell_WithTheStem("bar", "/foo");
//        whenISetTheResponseOf_To("bar/one", "New response");
//        thenTheResponseOf_ShouldBe("bar/one", "New response");
//        thenTheStemCellOf_ShouldBe("bar/one", "/foo/one");
//        thenTheResponseOf_ShouldBe("foo/one", "Old response");
    }
}
