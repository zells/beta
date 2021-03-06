package org.rtens.zells.beta;

import org.junit.Assert;
import org.junit.Test;

public class CopyCell extends CellsTest {

    @Test
    public void FailForNonExistingSource() {
        whenITryToCopy_To("foo", "", "bar");
        thenItShouldThrowAnException("Could not find [foo]");
    }

    @Test
    public void FailForEmptyName() {
        givenTheCell("foo.bar");
        whenITryToCopy_To("foo.bar", "foo", "");
        thenItShouldThrowAnException("Cannot give a cell an empty name");
    }

    @Test
    public void FailForNonExistingTargetParent() {
        givenTheCell("foo");
        whenITryToCopy_To("foo", "not", "bar");
        thenItShouldThrowAnException("Could not find [not]");
    }

    @Test
    public void FailForExistingTarget() {
        givenTheCell("foo.bar");
        givenTheCell("foo.baz");
        whenITryToCopy_To("foo.bar", "foo", "baz");
        thenItShouldThrowAnException("[foo.baz] already exists");
    }

    @Test
    public void CopyWithStem() {
        givenTheCell("stem");
        givenTheCell_WithTheStem("foo.bar", "°.stem");
        whenICopy_To("foo.bar", "foo", "baz");

        then_ShouldHave_Children("foo", 2);
        then_ShouldBeAChildOf("bar", "foo");
        then_ShouldBeAChildOf("baz", "foo");
        thenTheStemOf_ShouldBe("foo.baz", "°.stem");
    }

    @Test
    public void CopyInheritedCell() {
        givenTheCell("bar");
        givenTheCell("stem.one.a");
        givenTheCell_WithTheStem("foo", "°.stem");

        whenICopy_To("foo.one", "bar", "one");

        then_ShouldHave_Children("bar.one", 1);
    }

    @Test
    public void CopyWithReaction() {
        givenTheCell("foo.bar");
        given_HasAReaction("foo.bar");

        whenICopy_To("foo.bar", "foo", "baz");
        then_ShouldHaveTheSameReaction("foo.baz");
    }

    @Test
    public void CopyWithAllChildren() {
        givenTheCell("stem");
        givenTheCell("foo.bar.one");
        givenTheCell("foo.bar.two");
        givenTheCell("foo.bar.two.a");
        givenTheCell_WithTheStem("foo.bar.two.b", "°.stem");
        given_HasAReaction("foo.bar.two.b");

        whenICopy_To("foo.bar", "foo", "baz");

        then_ShouldHave_Children("foo.baz", 2);
        then_ShouldBeAChildOf("one", "foo.baz");
        then_ShouldBeAChildOf("two", "foo.baz");

        then_ShouldHave_Children("foo.baz.two", 2);
        then_ShouldBeAChildOf("a", "foo.baz.two");
        then_ShouldBeAChildOf("b", "foo.baz.two");
        thenTheStemOf_ShouldBe("foo.baz.two.b", "°.stem");
        then_ShouldHaveTheSameReaction("foo.baz.two.b");
    }

    @Test
    public void CopyWithInheritedChildren() {
        givenTheCell("stem.foo");
        givenTheCell_WithTheStem("bar", "°.stem");
        whenICopy_To("bar", "°", "baz");
        then_ShouldBeAChildOf("foo", "baz");
    }

    private Reaction reaction;

    private void given_HasAReaction(String path) {
        reaction = (receiver, message) -> {
        };
        engine.changeReaction(Path.parse(path), reaction);
    }

    private void whenITryToCopy_To(String path, String newParent, String newName) {
        try {
            whenICopy_To(path, newParent, newName);
        } catch (Exception e) {
            caught = e;
        }
    }

    private void whenICopy_To(String path, String newParent, String newName) {
        engine.copy(Path.parse(path), Path.parse(newParent), newName);
    }

    private void thenTheStemOf_ShouldBe(String path, String stem) {
        Assert.assertEquals(Path.parse(stem), engine.getStem(Path.parse(path)));
    }

    private void then_ShouldHaveTheSameReaction(String path) {
        Assert.assertEquals(reaction, engine.getOwnReaction(Path.parse(path)));
    }
}
