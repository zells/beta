package org.rtens.zells.beta;

import org.junit.Ignore;
import org.junit.Test;

/**
 * Stem cells are saved as paths relative to the sub cell. Avoid creating inheritance loops.
 */
public class ChangeStemCell {

    @Test
    @Ignore
    public void _SetNewStemCell() {
//        givenACell("foo/bar");
//        whenISetTheStemOf_To("foo/bar", "some/cell");
//        thenTheStemCellOf_ShouldBe("foo/bar", "some/cell");
    }

    @Test
    @Ignore
    public void _AdoptInheritedChild() {
//        givenACell_WithTheStem("foo/one", "inherited");
//        givenACell_WithTheStem("bar", "/foo");
//        whenISetTheStemOf_To("bar/one", "adopted");
//        thenTheStemCellOf_ShouldBe("bar/one", "adopted");
//        thenTheStemCellOf_ShouldBe("foo/one", "inherited");
    }

    @Test
    @Ignore
    public void _CatchInheritanceLoop() {
//        givenACell("foo");
//        givenACell_WithTheStem("bar", "/foo");
//        givenACell_WithTheStem("baz", "/bar");
//        whenISetTheStemOf_To("foo", "/baz");
//        thenItShouldThrowAnException("Inheritance loop detected.");
    }
}
