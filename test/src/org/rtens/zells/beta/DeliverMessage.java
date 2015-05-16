package org.rtens.zells.beta;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * When a message is delivered, the receiver's Reaction is executed with the path of the message,
 * relative to the receiver.
 */
public class DeliverMessage extends CellsTest {

    @Test
    public void FailIfCellDoesNotExist() {
        whenITryToSend_To("bar", "foo");
        thenItShouldThrowAnException("Could not find [foo]");
    }

    @Test
    public void WithoutReaction() {
        givenTheCell("foo");
        given_HasNoReaction("foo");
        whenISend_To("bar", "foo");
    }

    @Test
    public void DeliverMessageToCell() {
        givenTheCell("foo");
        whenISend_To("bar", "foo");
        thenTheReactionOf_ShouldBeExecutedWith("foo", "bar");
    }

    @Test
    public void DeliverMessageToInheritedCell() {
        givenTheCell("foo.one");
        givenTheCell_WithTheStem("bar", "°.foo");

        whenISend_To("message", "bar.one");

        thenTheReactionOf_ShouldBeExecutedWith("foo.one", "message");
        thenTheReactionOf_ShouldBeExecutedInTheContextOf("foo.one", "°.bar.one");
    }

    private Map<String, SpyReaction> reactions = new HashMap<>();

    private void whenITryToSend_To(String message, String cell) {
        try {
            whenISend_To(message, cell);
        } catch (Exception e) {
            caught = e;
        }
    }

    @Override
    protected void givenTheCell(String path) {
        super.givenTheCell(path);
        reactions.put(path, new SpyReaction());
        engine.changeReaction(Path.parse(path), reactions.get(path));
    }

    private void given_HasNoReaction(String path) {
        engine.changeReaction(Path.parse(path), null);
    }

    private void whenISend_To(String message, String cell) {
        engine.send(Path.parse(cell), Path.parse(message));
    }

    private void thenTheReactionOf_ShouldBeExecutedWith(String cell, String message) {
        Assert.assertEquals(Path.parse(message), reactions.get(cell).received);
    }

    private void thenTheReactionOf_ShouldBeExecutedInTheContextOf(String cell, String context) {
        Assert.assertEquals(Path.parse(context), reactions.get(cell).context.getPath());
    }

    private class SpyReaction implements Reaction {
        public Path received;
        public Cell context;

        @Override
        public void execute(Cell receiver, Path message) {
            context = receiver;
            received = message;
        }
    }
}
