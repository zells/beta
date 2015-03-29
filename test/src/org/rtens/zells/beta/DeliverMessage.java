package org.rtens.zells.beta;

import org.junit.Ignore;
import org.junit.Test;

/**
 * When a message is delivered, the receiver's Response is executed with the full path of the message.
 */
public class DeliverMessage {

    @Test
    @Ignore
    public void _FailIfCellDoesNotExist() {
//        whenISend_To("bar", "foo");
//        thenItShouldThrowAnException("[foo] does not exist.");
    }

    @Test
    @Ignore
    public void _DeliverMessageToCell() {
//        givenACell("foo");
//        whenISend_To("bar", "foo");
//        thenTheResponseOf_ShouldBeExecutedWith("foo", "bar");
    }

    @Test
    @Ignore
    public void _DeliverMessageToInheritedCell() {
//        givenACell("foo/one");
//        givenACell_WithTheStem("bar", "/foo");
//        whenISend_To("message", "bar/one");
//        thenTheResponseOf_ShouldBeExecutedInTheContextOf("foo/one", "bar/one");
    }

    @Test
    @Ignore
    public void _ResolveMessagePath() {
    }
}
