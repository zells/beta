package org.rtens.zells.beta;

import org.junit.Assert;
import org.junit.Test;

/**
 * Stem cells are saved as paths relative to the sub cell. Avoid creating inheritance loops.
 */
public class ChangeStemCell extends CellsTest {

    @Test
    public void SetNewStemCell() {
        givenTheCell("foo.bar");
        whenISetTheStemOf_To("foo.bar", "some.cell");
        thenTheStemCellOf_ShouldBe("foo.bar", "some.cell");
    }

    @Test
    public void AdoptInheritedChild() {
        givenTheCell_WithTheStem("foo.one", "inherited");
        givenTheCell_WithTheStem("bar", "°.foo");

        whenISetTheStemOf_To("bar.one", "adopted");

        thenTheStemCellOf_ShouldBe("bar.one", "adopted");
        thenTheStemCellOf_ShouldBe("foo.one", "inherited");
    }

    private void whenISetTheStemOf_To(String path, String stem) {
        engine.changeStem(Path.parse(path), Path.parse(stem));
    }

    private void thenTheStemCellOf_ShouldBe(String path, String stem) {
        Assert.assertEquals(Path.parse(stem), engine.getStem(Path.parse(path)));
    }
}