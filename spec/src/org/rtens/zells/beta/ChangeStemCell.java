package org.rtens.zells.beta;

import org.junit.Assert;
import org.junit.Test;

/**
 * Stem cells are saved as paths relative to the sub cell. Avoid creating inheritance loops.
 */
public class ChangeStemCell extends CellsTest {

    @Test
    public void SetNewStemCell() {
        givenTheCell("some.stem");
        givenTheCell("foo.bar");
        whenISetTheStemOf_To("foo.bar", "°.some.stem");
        thenTheStemCellOf_ShouldBe("foo.bar", "°.some.stem");
    }

    @Test
    public void AdoptInheritedChild() {
        givenTheCell("inherited");
        givenTheCell("adopted");
        givenTheCell_WithTheStem("foo.one", "°.inherited");
        givenTheCell_WithTheStem("bar", "°.foo");

        whenISetTheStemOf_To("bar.one", "°.adopted");

        thenTheStemCellOf_ShouldBe("bar.one", "°.adopted");
        thenTheStemCellOf_ShouldBe("foo.one", "°.inherited");
    }

    @Test
    public void NotExistingStem() {
        givenTheCell("foo");
        whenITryToSetTheStemOf_To("foo", "non");
        thenItShouldThrowAnException("Could not find [non]");
    }

    private void whenITryToSetTheStemOf_To(String path, String stem) {
        try {
            whenISetTheStemOf_To(path, stem);
        } catch (Exception e) {
            caught = e;
        }
    }

    private void whenISetTheStemOf_To(String path, String stem) {
        engine.changeStem(Path.parse(path), Path.parse(stem));
    }

    private void thenTheStemCellOf_ShouldBe(String path, String stem) {
        Assert.assertEquals(Path.parse(stem), engine.getStem(Path.parse(path)));
    }
}
