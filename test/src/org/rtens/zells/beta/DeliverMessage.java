package org.rtens.zells.beta;

import org.junit.Ignore;
import org.junit.Test;

/**
 * When a message is delivered, the receiver's Reaction is executed with the path of the message,
 * relative to the receiver.
 */
public class DeliverMessage {

    @Test
    @Ignore
    public void FailIfCellDoesNotExist() {
//        whenISend_To("bar", "foo");
//        thenItShouldThrowAnException("[foo] does not exist.");
    }

    @Test
    @Ignore
    public void DeliverMessageToCell() {
//        givenTheCell("foo");
//        whenISend_To("bar", "foo");
//        thenTheReactionOf_ShouldBeExecutedWith("foo", "bar");
    }

    @Test
    @Ignore
    public void DeliverMessageToInheritedCell() {
//        givenTheCell("foo/one");
//        givenTheCell_WithTheStem("bar", "/foo");
//        whenISend_To("message", "bar/one");
//        thenTheReactionOf_ShouldBeExecutedInTheContextOf("foo/one", "bar/one");
    }

}
