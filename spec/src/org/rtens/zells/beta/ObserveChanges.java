package org.rtens.zells.beta;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * In order to update the UI, changes in cell can be observed.
 */
public class ObserveChanges extends CellsTest {

    @Test
    public void NotifyAboutNewChild() {
        givenTheCell("foo");
        givenIAmObservingTheRoot();
        whenICreate_In("bar", "foo");
        thenIShouldBeNotifiedAbout_For(CellEvent.Type.Created, "°.foo.bar");
    }

    @Test
    public void DoNotNotifyAboutSibling() {
        givenTheCell("foo.bar");
        givenIAmObserving("foo.bar");
        whenICreate_In("baz", "foo");
        thenIShouldHaveObserved_Events(0);
    }

    @Test
    public void NotifyAboutDeletedCell() {
        givenIAmObservingTheRoot();
        givenTheCell("foo.bar.baz");
        whenIDelete("foo.bar.baz");
        thenIShouldBeNotifiedAbout_For(CellEvent.Type.Deleted, "°.foo.bar.baz");
    }

    @Test
    public void NotifyAboutChangedStem() {
        givenIAmObservingTheRoot();
        givenTheCell("foo.bar");
        whenIChangeTheStemOf("foo.bar");
        thenIShouldBeNotifiedAbout_For(CellEvent.Type.ChangedStem, "°.foo.bar");
    }

    @Test
    public void NotifyAboutChangedReaction() {
        givenIAmObservingTheRoot();
        givenTheCell("foo.bar");
        whenIChangeTheReactionOf("foo.bar");
        thenIShouldBeNotifiedAbout_For(CellEvent.Type.ChangedReaction, "°.foo.bar");
    }

    @Test
    public void NotifyAboutChangesInStem() {
        givenTheCell("stem");
        givenTheCell_WithTheStem("foo", "°.stem");
        givenIAmObserving("foo");

        whenICreate_In("bar", "stem");
        thenIShouldHaveObserved_Events(1);
        thenIShouldBeNotifiedAbout_For(CellEvent.Type.Created, "foo.bar");
    }

    @Test
    public void StopObservingOldStem() {
        givenTheCell("stem");
        givenTheCell("other");
        givenTheCell_WithTheStem("foo", "°.stem");
        givenTheCell_WithTheStem("foo", "°.other");
        givenIAmObserving("foo");

        whenICreate_In("bar", "stem");
        thenIShouldHaveObserved_Events(0);
    }

    @Test
    public void AdoptObservers() {
        givenTheCell("top.one");
        givenTheCell_WithTheStem("stem", "°.top");
        givenTheCell_WithTheStem("foo", "°.stem.one");

        whenICreate_In("a", "stem.one");

        givenIAmObserving("foo");

        whenICreate_In("b", "stem.one");
        thenIShouldHaveObserved_Events(1);
        thenIShouldBeNotifiedAbout_For(CellEvent.Type.Created, "foo.b");

        whenICreate_In("c", "top.one");
        thenIShouldHaveObserved_Events(2);
        thenIShouldBeNotifiedAbout_For(CellEvent.Type.Created, "foo.c");
    }

    @Test
    public void AdoptionsMultiplyEvents() {
        givenTheCell("two.a");
        givenTheCell_WithTheStem("one", "°.two");

        whenICreate_In("b", "one.a");

        givenIAmObservingTheRoot();
        whenICreate_In("c", "two.a");

        thenIShouldHaveObserved_Events(3);
        thenIShouldBeNotifiedAbout_For(CellEvent.Type.Created, "°.two.a.c");
        thenIShouldBeNotified_TimeAbout_For(2, CellEvent.Type.Created, "°.one.a.c");
    }

    @Test
    public void DoNotNotifyAboutAdoptions() {
        givenTheCell("stem.one");
        givenTheCell_WithTheStem("foo", "°.stem");

        givenIAmObservingTheRoot();
        whenICreate_In("a", "foo.one");

        thenIShouldHaveObserved_Events(1);
        thenIShouldBeNotifiedAbout_For(CellEvent.Type.Created, "°.foo.one.a");
    }

    private List<CellEvent> events = new ArrayList<>();

    private void givenIAmObserving(String path) {
        engine.observe(Path.parse(path), events::add);
    }

    private void givenIAmObservingTheRoot() {
        givenIAmObserving("°");
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

    private void thenIShouldBeNotifiedAbout_For(CellEvent.Type type, String path) {
        thenIShouldBeNotified_TimeAbout_For(1, type, path);
    }

    private void thenIShouldHaveObserved_Events(int count) {
        Assert.assertEquals(count, events.size());
    }

    private void thenIShouldBeNotified_TimeAbout_For(int count, CellEvent.Type type, String path) {
        for (CellEvent event : events) {
            if (event.getType().equals(type) && event.getPath().equals(Path.parse(path))) {
                count--;
            }
        }
        Assert.assertEquals("Could not find [" + type + "] event for [" + path + "]", 0, count);
    }
}