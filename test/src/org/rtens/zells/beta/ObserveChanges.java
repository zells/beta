package org.rtens.zells.beta;

import org.junit.Ignore;
import org.junit.Test;

/**
 * In order to update the UI, changes in cell can be observed.
 */
public class ObserveChanges {

    @Test
    @Ignore
    public void _FailIfCellDoesNotExist() {
//        whenIObserve("foo");
//        thenItShouldThrowAnException("[foo] does not exist.");
    }

    @Test
    @Ignore
    public void _NotifyAboutNewChild() {
//        givenACell("foo");
//        givenIAmObserving("foo");
//        whenICreate_Under("bar", "foo");
//        thenIShouldBeNotifiedAboutNewCell("foo/bar");
    }

    @Test
    @Ignore
    public void _NotifyAboutNewGrandChild() {
//        givenACell("foo/bar");
//        givenIAmObserving("foo");
//        whenICreate_Under("baz", "foo/bar");
//        thenIShouldNeNotifiedAboutThe_Cell("created", "foo/bar/baz");
    }

    @Test
    @Ignore
    public void _NotifyAboutDeletedCell() {
//        givenACell("foo/bar/baz");
//        givenIAmObserving("foo");
//        whenIDelete("foo/bar/baz");
//        thenIShouldBeNotifiedAboutThe_Cell("deleted", "foo/bar/baz");
    }

    @Test
    @Ignore
    public void _NotifyAboutChangedStem() {
//        givenACell("foo/bar");
//        givenIAmObserving("foo");
//        whenIChangeTheStemOf_To("foo/bar", "new/stem");
//        thenIShouldBeNotifiedAboutThe_Cell("changedStem", "foo/bar");
    }

    @Test
    @Ignore
    public void _NotifyAboutChangedResponse() {
//        givenACell("foo/bar");
//        givenIAmObserving("foo");
//        whenIChangeTheResponseOf("foo/bar");
//        thenIShouldBeNotifiedAboutThe_Cell("changedResponse", "foo/bar");
    }

    @Test
    @Ignore
    public void _NotifyAboutChangedName() {
//        givenACell("foo/bar");
//        givenIAmObserving("foo");
//        whenIChangeTheNameOf_To("foo/bar", "baz");
//        thenIShouldBeNotifiedAboutThe_Cell("deleted", "foo/bar");
//        thenIShouldBeNotifiedAboutThe_Cell("created", "foo/baz");
    }

    @Test
    @Ignore
    public void _StopObservation() {
//        givenACell("foo");
//        givenIAmObserving("foo");
//        whenIStopObserving("foo");
//        whenICreate_Under("bar", "foo");
//        thenIShouldGetNotification();
    }
}