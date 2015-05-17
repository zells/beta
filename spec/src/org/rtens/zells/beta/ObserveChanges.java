package org.rtens.zells.beta;

import org.junit.Assert;
import org.junit.Test;
import org.rtens.zells.beta.events.CellCreatedEvent;
import org.rtens.zells.beta.events.CellDeletedEvent;
import org.rtens.zells.beta.events.ChangedReactionEvent;
import org.rtens.zells.beta.events.ChangedStemEvent;

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
        thenIShouldBeNotifiedAbout_For(CellCreatedEvent.class, "°.foo.bar");
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
        thenIShouldBeNotifiedAbout_For(CellDeletedEvent.class, "°.foo.bar.baz");
    }

    @Test
    public void NotifyAboutChangedStem() {
        givenIAmObservingTheRoot();
        givenTheCell("foo.bar");
        whenIChangeTheStemOf("foo.bar");
        thenIShouldBeNotifiedAbout_For(ChangedStemEvent.class, "°.foo.bar");
    }

    @Test
    public void NotifyAboutChangedReaction() {
        givenIAmObservingTheRoot();
        givenTheCell("foo.bar");
        whenIChangeTheReactionOf("foo.bar");
        thenIShouldBeNotifiedAbout_For(ChangedReactionEvent.class, "°.foo.bar");
    }

    private Map<Class, CellEvent> events = new HashMap<>();

    private void givenIAmObserving(String path) {
        engine.observe(Path.parse(path), event -> events.put(event.getClass(), event));
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
        engine.changeStem(Path.parse(path), new Path("foo"));
    }

    private void whenIChangeTheReactionOf(String path) {
        engine.changeReaction(Path.parse(path), (receiver, message) -> {
        });
    }

    private void thenIShouldBeNotifiedAbout_For(Class event, String path) {
        Assert.assertTrue(events.containsKey(event));
        Assert.assertTrue(events.get(event).getPath().equals(Path.parse(path)));
    }

    private void thenIShouldHaveObserved_Events(int count) {
        Assert.assertEquals(count, events.size());
    }
}