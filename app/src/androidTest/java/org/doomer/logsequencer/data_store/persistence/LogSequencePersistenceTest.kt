package org.doomer.logsequencer.data_store.persistence

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LogSequencePersistenceTest {
    lateinit var db: LogSequenceDatabase

    @Before
    fun setup() {
        db = LogSequenceDatabase(InstrumentationRegistry.getInstrumentation().targetContext)
        db.clear()
    }

    @After
    fun teardown() {
        db.close()
    }

    @Test
    fun testPreconditions() {
        Assert.assertNotNull(db)
        Assert.assertEquals(0, db.getAllLogVisitedEvents().size)
    }

    @Test
    fun testAdding1Entry() {
        Assert.assertTrue(db.recordLogVisitedEvent(LogSequenceEntry("a", "1")) > -1)
        val result = db.getAllLogVisitedEvents()
        Assert.assertEquals(1, result.size)
        Assert.assertEquals("a", result[0].visitingUrl)
        Assert.assertEquals("1", result[0].visitedUrl)
    }

    @Test
    fun testClear() {
        Assert.assertTrue(db.recordLogVisitedEvent(LogSequenceEntry("a", "1")) > -1)
        val result = db.getAllLogVisitedEvents()
        Assert.assertEquals(1, result.size)
        Assert.assertEquals("a", result[0].visitingUrl)
        Assert.assertEquals("1", result[0].visitedUrl)

        db.clear()

        val resultAfterClear = db.getAllLogVisitedEvents()
        Assert.assertEquals(0, resultAfterClear.size)
    }

    @Test
    fun testAdding2Entries() {
        val one = LogSequenceEntry("a", "1")
        val two = LogSequenceEntry("b", "2")
        val three = LogSequenceEntry("c", "3")

        db.recordLogVisitedEvents(listOf(one, two))

        Assert.assertTrue(db.recordLogVisitedEvent(three) > -1)

        val result = db.getAllLogVisitedEvents()

        Assert.assertEquals(3, result.size)
        Assert.assertEquals(one, LogSequenceEntry(result[0].visitingUrl, result[0].visitedUrl))
        Assert.assertEquals(two, LogSequenceEntry(result[1].visitingUrl, result[1].visitedUrl))
        Assert.assertEquals(three, LogSequenceEntry(result[2].visitingUrl, result[2].visitedUrl))
    }
}