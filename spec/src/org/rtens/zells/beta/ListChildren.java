package org.rtens.zells.beta;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Inherited and own children are listed together but can still be differentiated.
 */
public class ListChildren extends CellsTest {

    @Test
    public void NoChildren() {
        givenTheCell("foo");
        whenIListTheChildrenOf("foo");
        thenIShouldGet_Cells(0);
    }

    @Test
    public void WithChildren() {
        givenTheCell("foo.bar");
        givenTheCell("foo.baz");
        whenIListTheChildrenOf("foo");
        thenIShouldGet_Cells(2);
        then_ShouldBeAChild("bar");
        then_ShouldBeAChild("baz");
    }

    @Test
    public void SortByName() {
        givenTheCell("foo.baz");
        givenTheCell("foo.meh");
        givenTheCell("foo.bar");
        whenIListTheChildrenOf("foo");
        thenChild_ShouldBe(1, "bar");
        thenChild_ShouldBe(2, "baz");
        thenChild_ShouldBe(3, "meh");
    }

    @Test
    public void ListInheritedChildren() {
        givenTheCell("foo.one");
        givenTheCell("foo.two");
        givenTheCell_WithTheStem("bar", "°.foo");
        whenIListTheChildrenOf("bar");
        thenIShouldGet_Cells(2);
        then_ShouldBeAChild("one");
        then_ShouldBeAChild("two");
    }

    @Test
    public void MixOwnAndInheritedChildren() {
        givenTheCell("of.foo");
        givenTheCell("of.bar");

        givenTheCell("foo.one");
        givenTheCell_WithTheStem("foo.two", "°.of.foo");

        givenTheCell_WithTheStem("bar", "°.foo");
        givenTheCell_WithTheStem("bar.two", "°.of.bar");

        givenTheCell("bar.three");

        whenIListTheChildrenOf("bar");

        thenIShouldGet_Cells(3);

        then_ShouldBeAChild("one");
        then_ShouldBeAChild("two");
        then_ShouldBeAChild("three");

        thenTheStemCellOf_ShouldBe("bar.two", "°.of.bar");
    }

    @Test
    public void ListOwnChildren() {
        givenTheCell("foo.one");
        givenTheCell("foo.two");

        givenTheCell_WithTheStem("bar", "°.foo");
        givenTheCell("bar.one");
        givenTheCell("bar.three");

        whenIListTheOwnChildrenOf("bar");
        thenIShouldGet_Cells(2);
        then_ShouldBeAChild("one");
        then_ShouldBeAChild("three");
    }

    @Test
    public void InheritInheritedChildren() {
        givenTheCell("foo.one");
        givenTheCell_WithTheStem("bar", "°.foo");
        givenTheCell_WithTheStem("baz", "°.bar");

        whenIListTheChildrenOf("baz");

        thenIShouldGet_Cells(1);
        then_ShouldBeAChild("one");
    }

    @Test
    public void OverwriteStem() {
        givenTheCell("foo.one");
        givenTheCell_WithTheStem("bar", "°.foo");
        givenTheCell_WithTheStem("baz", "°.bar.one");

        whenIListTheChildrenOf("baz");
        thenIShouldGet_Cells(0);

        givenTheCell("bar.one.two");

        whenIListTheChildrenOf("baz");
        thenIShouldGet_Cells(1);
        then_ShouldBeAChild("two");
    }

    @Test
    public void InheritChildrenOfInheritedChildren() {
        givenTheCell("foo.three");
        givenTheCell_WithTheStem("bar.two", "°.foo");
        givenTheCell_WithTheStem("baz.one", "°.bar");
        givenTheCell_WithTheStem("meh", "°.baz");

        whenIListTheChildrenOf("meh.one.two");
        thenIShouldGet_Cells(1);
        then_ShouldBeAChild("three");
    }

    @Test
    public void InheritGrandChildren() {
        givenTheCell("bar");
        givenTheCell("stem.one.a");
        givenTheCell_WithTheStem("foo", "°.stem");

        whenIListTheChildrenOf("foo.one");
        thenIShouldGet_Cells(1);
        then_ShouldBeAChild("a");
    }

    @Test
    public void StemPathIsRelative() {
        givenTheCell("foo.bar.baz.one");
        givenTheCell_WithTheStem("foo.bar", "baz");
        whenIListTheChildrenOf("foo.bar");

        thenIShouldGet_Cells(2);
        then_ShouldBeAChild("baz");
        then_ShouldBeAChild("one");
    }

    @Test
    public void CatchInheritanceLoop() {
        givenTheCell("foo");
        givenTheCell_WithTheStem("bar", "°.foo");
        givenTheCell_WithTheStem("baz", "°.bar");
        givenTheCell_WithTheStem("foo", "°.foo");
        whenIListTheChildrenOf("foo");
        thenIShouldGet_Cells(0);
    }

    private List<String> children = new ArrayList<>();

    private void whenIListTheChildrenOf(String path) {
        children = engine.listChildren(Path.parse(path));
    }

    private void whenIListTheOwnChildrenOf(String path) {
        children = engine.listOwnChildren(Path.parse(path));
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
