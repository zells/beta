package org.rtens.zells.beta;

import org.junit.Ignore;
import org.junit.Test;

/**
 * Reactions are always completely overwritten with a new Reaction instance. Which instances are supported
 * depends on the actual model.
 */
public class ChangeReactions {

    @Test
    @Ignore
    public void FailIfCellDoesNotExist() {
//        whenISetTheReactionOf_To("foo", "A new Reaction");
//        thenItShouldThrowAnException("[foo] does not exist.");
    }

    @Test
    @Ignore
    public void SuccessfulChange() {
//        givenTheCell("foo");
//        whenISetTheReactionOf_To("foo", "A new Reaction");
//        thenTheReactionOf_ShouldBe("foo", "A new Reaction");
    }

    @Test
    @Ignore
    public void AdoptInheritedCell() {
//        givenTheCell("foo/one");
//        givenTheReactionOf_Is("foo/one", "Old Reaction");
//        givenTheCell_WithTheStem("bar", "/foo");
//        whenISetTheReactionOf_To("bar/one", "New Reaction");
//        thenTheReactionOf_ShouldBe("bar/one", "New Reaction");
//        thenTheStemCellOf_ShouldBe("bar/one", "/foo/one");
//        thenTheReactionOf_ShouldBe("foo/one", "Old Reaction");
    }
}
