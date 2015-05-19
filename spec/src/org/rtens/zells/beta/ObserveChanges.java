package org.rtens.zells.beta;

import org.junit.Assert;
import org.junit.Test;

/**
 * In order to update the UI, changes in cell can be observed.
 */
public class ObserveChanges extends CellsTest {

    @Test
    public void NotifyAboutNewChild() {
        givenTheCell("foo");
        givenIAmObserving("foo");
        whenICreate_In("bar", "foo");
        thenIShouldBeNotifiedAboutAChange();
    }

    @Test
    public void DoNotNotifyAboutSibling() {
        givenTheCell("foo.bar");
        givenIAmObserving("foo.bar");
        whenICreate_In("baz", "foo");
        thenIShouldNotBeNotifiedAboutAChange();
    }

    @Test
    public void NotifyAboutDeletedCell() {
        givenTheCell("foo.bar.baz");
        givenIAmObserving("foo.bar");
        whenIDelete("foo.bar.baz");
        thenIShouldBeNotifiedAboutAChange();
    }

    @Test
    public void NotifyAboutChangedStem() {
        givenTheCell("foo.bar");
        givenIAmObserving("foo.bar");
        whenIChangeTheStemOf("foo.bar");
        thenIShouldBeNotifiedAboutAChange();
    }

    @Test
    public void NotifyAboutChangedReaction() {
        givenTheCell("foo.bar");
        givenIAmObserving("foo.bar");
        whenIChangeTheReactionOf("foo.bar");
        thenIShouldBeNotifiedAboutAChange();
    }

    @Test
    public void NotifyAboutChangesInStem() {
        givenTheCell("stem");
        givenTheCell_WithTheStem("foo", "°.stem");
        givenIAmObserving("foo");

        whenICreate_In("bar", "stem");
        thenIShouldBeNotifiedAboutAChange();
    }

    @Test
    public void StopObservingOldStem() {
        givenTheCell("stem");
        givenTheCell("other");
        givenTheCell_WithTheStem("foo", "°.stem");
        givenTheCell_WithTheStem("foo", "°.other");
        givenIAmObserving("foo");

        whenICreate_In("bar", "stem");
        thenIShouldNotBeNotifiedAboutAChange();
    }

    @Test
    public void AdoptObservers() {
        givenTheCell("top.one");
        givenTheCell_WithTheStem("stem", "°.top");
        givenTheCell_WithTheStem("foo", "°.stem.one");

        whenICreate_In("a", "stem.one");

        givenIAmObserving("foo");

        whenICreate_In("b", "stem.one");
        thenIShouldBeNotifiedAboutAChange();

        whenICreate_In("c", "top.one");
        thenIShouldBeNotifiedAboutAChange();
    }

    @Test
    public void DoNotNotifyAboutAdoptions() {
        givenTheCell("stem.one");
        givenTheCell_WithTheStem("foo", "°.stem");

        givenIAmObserving("foo");
        whenICreate_In("a", "foo.one");

        thenIShouldNotBeNotifiedAboutAChange();
    }

    private Path wasNotified;

    private void givenIAmObserving(String path) {
        engine.observe(Path.parse(path), (Path cell) -> wasNotified = cell);
    }

    private void whenICreate_In(String child, String parent) {
        engine.create(Path.parse(parent), child);
    }

    private void whenIDelete(String path) {
        engine.delete(Path.parse(path));
    }

    private void whenIChangeTheStemOf(String path) {
        engine.changeStem(Path.parse(path), new Path("°", "cell"));
    }

    private void whenIChangeTheReactionOf(String path) {
        engine.changeReaction(Path.parse(path), (receiver, message) -> {
        });
    }

    private void thenIShouldBeNotifiedAboutAChange() {
        Assert.assertNotNull(wasNotified);
        wasNotified = null;
    }

    private void thenIShouldNotBeNotifiedAboutAChange() {
        Assert.assertNull(wasNotified);
    }
}