package org.rtens.zells.beta;

import org.junit.Ignore;
import org.junit.Test;

/**
 * Inherited and own children are listed together but can still be differentiated.
 */
public class ListChildren {

    @Test
    @Ignore
    public void _NoChildren() {
//        givenACell("foo");
//        whenIListTheChildrenOf("foo");
//        thenIShouldGet_Cells(0);
    }

    @Test
    @Ignore
    public void _WithChildren() {
//        givenACell("foo/bar");
//        givenACell("foo/baz");
//        whenIListTheChildrenOf("foo");
//        thenIShouldGet_Cells(2);
//        then_ShouldBeAChild("bar");
//        then_ShouldBeAChild("baz");
    }

    @Test
    @Ignore
    public void _SortByName() {
//        givenACell("foo/baz");
//        givenACell("foo/meh");
//        givenACell("foo/bar");
//        whenIListTheChildrenOf("foo");
//        thenChild_ShouldBe(1, "bar");
//        thenChild_ShouldBe(2, "baz");
//        thenChild_ShouldBe(3, "meh");
    }

    @Test
    @Ignore
    public void _ListInheritedChildren() {
//        givenACell("foo/one");
//        givenACell("foo/two");
//        givenACell_WithTheStem("bar", "/foo");
//        whenIListTheChildrenOf("bar");
//        thenIShouldGet_Cells(2);
//        then_ShouldBeAChild("one");
//        then_ShouldBeAChild("two");
    }

    @Test
    @Ignore
    public void _MixOwnAndInheritedChildren() {
//        givenACell("foo/one");
//        givenACell_WithTheStem("foo/two", "ofFoo");
//        givenACell_WithTheStem("bar", "/foo");
//        givenACell_WithTheStem("bar/two", "ofBar");
//        givenACell("bar/three");
//        whenIListTheChildrenOf("bar");
//        thenIShouldGet_Cells(3);
//        then_ShouldBeAChild("one");
//        then_ShouldBeAChild("two");
//        then_ShouldBeAChild("three");
//        thenTheStemCellOf_ShouldBe("bar/two", "ofBar");
//        then_ShouldBeInherited("bar/one");
//        then_ShouldNotBeInherited("bar/two");
//        then_ShouldBeAnOwnChild("bar/three");
    }

    @Test
    @Ignore
    public void _InheritInheritedChildren() {
//        givenACell("foo/one");
//        givenACell_WithTheStem("bar", "/foo");
//        givenACell_WithTheStem("baz", "/bar");
//        whenIListTheChildrenOf("baz");
//        thenIShouldGet_Cells(1);
//        then_ShouldBeInherited("bar/one");
//        then_ShouldBeInherited("baz/one");
    }
}
