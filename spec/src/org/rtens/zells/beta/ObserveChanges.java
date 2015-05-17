package org.rtens.zells.beta;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

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
        thenIShouldBeNotifiedAbout_For(CellEvent.Type.Created, "foo.bar");
    }

    @Test
    public void AdoptObservers() {
        givenTheCell("top.one");
        givenTheCell_WithTheStem("stem", "°.top");
        givenTheCell_WithTheStem("foo", "°.stem.one");

        whenICreate_In("a", "stem.one");

        givenIAmObserving("foo");
        whenICreate_In("b", "stem.one");
        thenIShouldBeNotifiedAbout_For(CellEvent.Type.Created, "foo.b");

        givenIAmObserving("foo");
        whenICreate_In("c", "top.one");
        thenIShouldBeNotifiedAbout_For(CellEvent.Type.Created, "foo.c");
    }

    private Map<CellEvent.Type, CellEvent> events = new HashMap<>();

    private void givenIAmObserving(String path) {
        engine.observe(Path.parse(path), event -> events.put(event.getType(), event));
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
        Assert.assertTrue("No [" + type + "] in " + events.keySet(), events.containsKey(type));
        Assert.assertEquals(Path.parse(path), events.get(type).getPath());
    }

    private void thenIShouldHaveObserved_Events(int count) {
        Assert.assertEquals(count, events.size());
    }
}