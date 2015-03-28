package org.rtens.zells.beta;

import org.junit.Ignore;
import org.junit.Test;

public class CreateCell {

    @Test
    @Ignore
    public void _FailIfParentDoesNotExist() {
//        whenICreate_Under("bar", "foo");
//        thenItShouldThrowAnException("[foo] does not exist.");
    }

    @Test
    @Ignore
    public void _FailIfChildAlreadyExists() {
//        givenACell("foo/bar");
//        whenICreate_Under("bar", "foo");
//        thenItShouldThrowAnException("[foo] already has a child named [bar].");
    }

    @Test
    @Ignore
    public void _FailIfNameIsEmpty() {
//        givenACell("foo");
//        whenICreate_Under("", "foo");
//        thenItShouldThrowAnException("Cannot create cell with empty name.");
    }

    @Test
    @Ignore
    public void _CreateNewChild() {
//        givenACell("foo");
//        whenICreate_Under("bar", "foo");
//        thenThereShouldBeACell("foo/bar");
    }

    @Test
    @Ignore
    public void _DefaultStempCell() {
//        givenACell("foo");
//        whenICreate_Under("bar", "foo");
//        thenTheStemCellOf_ShouldBe("foo/bar", "/zells/Cell");
    }

    @Test
    @Ignore
    public void _AdoptInheritedCell() {
//        givenACell_WithTheStem("foo/one", "inherited");
//        givenACell_WithTheStem("bar", "/foo");
//        whenICreate_Under("two", "bar/one");
//        thenThereShouldBeACell("bar/one/two");
//        thenTheStemCellOf_ShouldBe("bar/one", "/foo/one");
//        then_ShouldNotExist("foo/one/two");
    }

    @Test
    @Ignore
    public void _AdoptInheritedGrandChild() {
//        givenACell("foo/one/two");
//        givenACell_WithTheStem("bar", "/foo");
//        whenICreate_Under("three", "bar/one/two");
//        thenTheStemCellOf_ShouldBe("bar/one/two", "/foo/one/two");
//        thenTheStemCellOf_ShouldBe("bar/one", "/foo/one");
    }
}
