package org.rtens.zells.beta;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Inherited and own children are listed together but can still be differentiated.
 */
public class ListChildren extends CellsTest {

    @Test
    public void _NoChildren() {
        givenACell("foo");
        whenIListTheChildrenOf("foo");
        thenIShouldGet_Cells(0);
    }

    @Test
    public void _WithChildren() {
        givenACell("foo.bar");
        givenACell("foo.baz");
        whenIListTheChildrenOf("foo");
        thenIShouldGet_Cells(2);
        then_ShouldBeAChild("bar");
        then_ShouldBeAChild("baz");
    }

    @Test
    public void _SortByName() {
        givenACell("foo.baz");
        givenACell("foo.meh");
        givenACell("foo.bar");
        whenIListTheChildrenOf("foo");
        thenChild_ShouldBe(1, "bar");
        thenChild_ShouldBe(2, "baz");
        thenChild_ShouldBe(3, "meh");
    }

    @Test
    public void _ListInheritedChildren() {
        givenACell("foo.one");
        givenACell("foo.two");
        givenACell_WithTheStem("bar", "°.foo");
        whenIListTheChildrenOf("bar");
        thenIShouldGet_Cells(2);
        then_ShouldBeAChild("one");
        then_ShouldBeAChild("two");
    }

    @Test
    public void _MixOwnAndInheritedChildren() {
        givenACell("of.foo");
        givenACell("of.bar");

        givenACell("foo.one");
        givenACell_WithTheStem("foo.two", "°.of.foo");

        givenACell_WithTheStem("bar", "°.foo");
        givenACell_WithTheStem("bar.two", "°.of.bar");

        givenACell("bar.three");

        whenIListTheChildrenOf("bar");

        thenIShouldGet_Cells(3);

        then_ShouldBeAChild("one");
        then_ShouldBeAChild("two");
        then_ShouldBeAChild("three");

        thenTheStemCellOf_ShouldBe("bar.two", "°.of.bar");
    }

    @Test
    @Ignore
    public void _ListOwnChildren() {

    }

    @Test
    public void _InheritInheritedChildren() {
        givenACell("foo.one");
        givenACell_WithTheStem("bar", "°.foo");
        givenACell_WithTheStem("baz", "°.bar");

        whenIListTheChildrenOf("baz");

        thenIShouldGet_Cells(1);
        then_ShouldBeAChild("one");
    }

    @Test
    public void _OverwriteStem() {
        givenACell("foo.one");
        givenACell_WithTheStem("bar", "°.foo");
        givenACell_WithTheStem("baz", "°.bar.one");

        whenIListTheChildrenOf("baz");
        thenIShouldGet_Cells(0);

        givenACell("bar.one.two");

        whenIListTheChildrenOf("baz");
        thenIShouldGet_Cells(1);
        then_ShouldBeAChild("two");
    }

    @Test
    public void _InheritChildrenOfInheritedChildren() {
        givenACell_WithTheStem("meh", "°.baz");
        givenACell_WithTheStem("baz.one", "°.bar");
        givenACell_WithTheStem("bar.two", "°.foo");
        givenACell("foo.three");

        whenIListTheChildrenOf("meh.one.two");
        thenIShouldGet_Cells(1);
        then_ShouldBeAChild("three");
    }

    @Test
    public void _StemPathIsRelative() {
        givenACell_WithTheStem("foo.bar", "baz");
        givenACell("foo.bar.baz.one");
        whenIListTheChildrenOf("foo.bar");

        thenIShouldGet_Cells(2);
        then_ShouldBeAChild("baz");
        then_ShouldBeAChild("one");
    }

    @Test
    public void _CatchInheritanceLoop() {
        givenACell_WithTheStem("foo", "°.baz");
        givenACell_WithTheStem("bar", "°.foo");
        givenACell_WithTheStem("baz", "°.bar");
        whenITryToListTheChildrenOf("foo");
        thenItShouldThrowAnException("Inheritance loop detected.");
    }

    private void whenITryToListTheChildrenOf(String path) {
        try {
            whenIListTheChildrenOf(path);
        } catch (Exception e) {
            caught = e;
        }
    }

    private List<String> children = new ArrayList<>();

    private void whenIListTheChildrenOf(String path) {
        children = engine.listChildren(Path.parse(path));
    }

    private void thenIShouldGet_Cells(int count) {
        Assert.assertEquals(count, children.size());
    }

    private void then_ShouldBeAChild(String name) {
        Assert.assertTrue(children.contains(name));
    }

    private void thenChild_ShouldBe(int index, String name) {
        Assert.assertEquals(name, children.get(index - 1));
    }

    private void thenTheStemCellOf_ShouldBe(String path, String stem) {
        Assert.assertEquals(Path.parse(stem), engine.getStem(Path.parse(path)));
    }
}
