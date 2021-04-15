package org.doomer.logsequencer.data_store.repository

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.doomer.logsequencer.data_store.persistence.LogSequenceDatabase
import org.doomer.logsequencer.data_store.persistence.LogSequenceEntry
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.IllegalStateException

@RunWith(AndroidJUnit4::class)
class LogSequenceRepositoryTest {
    private lateinit var logSequenceRepository: LogSequenceRepository

    @Before
    fun setup() {
        logSequenceRepository = LogSequenceRepositoryImpl(LogSequenceDatabase(InstrumentationRegistry.getInstrumentation().targetContext))
        logSequenceRepository.clear()
    }

    @After
    fun teardown() {
        logSequenceRepository.close()
    }

    @Test
    fun testPreconditions() {
        Assert.assertNotNull(logSequenceRepository)
        Assert.assertEquals(0, logSequenceRepository.getUniqueVisitingIpAddresses().size)
    }

    @Test
    fun testRecordLogVisitedEvent() {
        Assert.assertTrue(logSequenceRepository.recordLogVisitedUrl(LogSequenceEntry("a", "1")))

        val logEvents = logSequenceRepository.getUniqueVisitingIpAddresses()

        Assert.assertEquals(1, logEvents.size)
        Assert.assertEquals("a", logEvents[0])

        for (element in logEvents) {
            Assert.assertEquals(LogSequenceEntry("a", "1"), logSequenceRepository.getLogVisitedUrlsForVisitingIpAddress(element)[0])
        }
    }

    @Test
    fun testClear() {
        Assert.assertTrue(logSequenceRepository.recordLogVisitedUrl(LogSequenceEntry("a", "1")))

        val logEvents = logSequenceRepository.getUniqueVisitingIpAddresses()

        Assert.assertEquals(1, logEvents.size)
        Assert.assertEquals("a", logEvents[0])

        for (element in logEvents) {
            Assert.assertEquals(LogSequenceEntry("a", "1"), logSequenceRepository.getLogVisitedUrlsForVisitingIpAddress(element)[0])
        }

        logSequenceRepository.clear()

        val logEventsAfterClear = logSequenceRepository.getUniqueVisitingIpAddresses()

        Assert.assertEquals(0, logEventsAfterClear.size)
    }

    @Test
    fun testClose() {
        val localRepo = LogSequenceRepositoryFactory.getLogSequenceRepository(InstrumentationRegistry.getInstrumentation().targetContext)

        Assert.assertTrue(localRepo.recordLogVisitedUrl(LogSequenceEntry("a", "1")))

        val logEvents = localRepo.getUniqueVisitingIpAddresses()

        Assert.assertEquals(1, logEvents.size)
        Assert.assertEquals("a", logEvents[0])

        for (element in logEvents) {
            Assert.assertEquals(LogSequenceEntry("a", "1"), localRepo.getLogVisitedUrlsForVisitingIpAddress(element)[0])
        }

        localRepo.close()

        var error: IllegalStateException? = null

        try {
            localRepo.getUniqueVisitingIpAddresses()
        } catch (e: IllegalStateException) {
            error = e
        }

        Assert.assertNotNull(error)
    }

    @Test
    fun testRecordLogVisitedEvents() {
        val one = LogSequenceEntry("a", "1")
        val two = LogSequenceEntry("b", "2")
        val three = LogSequenceEntry("c", "3")

        val logSequenceEntries = mutableListOf<LogSequenceEntry>()
        for (i in 1..10) {
            logSequenceEntries.add(one)
            logSequenceEntries.add(two)
            logSequenceEntries.add(three)
        }

        logSequenceRepository.recordLogVisitedUrls(logSequenceEntries)

        val visitingUrls = logSequenceRepository.getUniqueVisitingIpAddresses()

        Assert.assertEquals(3, visitingUrls.size)

        val logEntries = logSequenceRepository.getLogVisitedUrlsForVisitingIpAddress("c")

        Assert.assertEquals(10, logEntries.size)

        for (logEntry in logEntries) {
            Assert.assertEquals("3", logEntry.visitedUrl)
        }
    }
}
