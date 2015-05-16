package org.rtens.zells.beta;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Reactions are always completely overwritten with a new Reaction instance. Which instances are supported
 * depends on the actual model.
 */
public class ChangeReactions extends CellsTest {

    @Test
    public void FailIfCellDoesNotExist() {
        whenITryToSetTheReactionOf_To("foo", "A new Reaction");
        thenItShouldThrowAnException("Could not find [foo]");
    }

    @Test
    public void SuccessfulChange() {
        givenTheCell("foo");
        whenISetTheReactionOf_To("foo", "A new Reaction");
        thenTheReactionOf_ShouldBe("foo", "A new Reaction");
    }

    @Test
    public void InheritReaction() {
        givenTheCell("foo");
        givenTheCell_WithTheStem("bar", "°.foo");

        whenISetTheReactionOf_To("foo", "Foo!");

        then_ShouldHaveNoOwnReaction("bar");
        thenTheReactionOf_ShouldBe("bar", "Foo!");
    }

    @Test
    public void AdoptInheritedCell() {
        givenTheCell("foo.one");
        givenTheReactionOf_Is("foo.one", "Old Reaction");
        givenTheCell_WithTheStem("bar", "°.foo");

        whenISetTheReactionOf_To("bar.one", "New Reaction");

        thenTheReactionOf_ShouldBe("bar.one", "New Reaction");
        thenTheStemCellOf_ShouldBe("bar.one", "°.foo.one");
        thenTheReactionOf_ShouldBe("foo.one", "Old Reaction");
    }

    private Map<String, Reaction> reactions = new HashMap<>();

    private void givenTheReactionOf_Is(String path, String reaction) {
        whenISetTheReactionOf_To(path, reaction);
    }

    private void whenITryToSetTheReactionOf_To(String path, String reaction) {
        try {
            whenISetTheReactionOf_To(path, reaction);
        } catch (Exception e) {
            caught = e;
        }
    }

    private void whenISetTheReactionOf_To(String path, String reaction) {
        reactions.put(reaction, new DummyReaction());
        engine.setReaction(Path.parse(path), reactions.get(reaction));
    }

    private void then_ShouldHaveNoOwnReaction(String path) {
        Assert.assertNull(engine.getOwnReaction(Path.parse(path)));
    }

    private void thenTheReactionOf_ShouldBe(String path, String reaction) {
        Assert.assertEquals(reactions.get(reaction), engine.getReaction(Path.parse(path)));
    }

    private void thenTheStemCellOf_ShouldBe(String path, String stem) {
        Assert.assertEquals(Path.parse(stem), engine.getStem(Path.parse(path)));
    }

    private class DummyReaction extends Reaction {
        @Override
        public void execute(Cell receiver, Path message) {

        }
    }
}
