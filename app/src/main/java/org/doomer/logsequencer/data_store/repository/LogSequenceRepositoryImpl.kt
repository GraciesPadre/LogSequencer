package org.doomer.logsequencer.data_store.repository

import org.doomer.logsequencer.data_store.persistence.LogSequenceEntry
import org.doomer.logsequencer.data_store.persistence.LogSequencePersistence

class LogSequenceRepositoryImpl(private val logSequencePersistence: LogSequencePersistence): LogSequenceRepository {
    override fun recordLogVisitedEvent(logSequenceEntry: LogSequenceEntry) = logSequencePersistence.recordLogVisitedEvent(logSequenceEntry) > -1

    override fun recordLogVisitedEvents(logSequenceEntries: List<LogSequenceEntry>) = logSequencePersistence.recordLogVisitedEvents(logSequenceEntries)

    override fun getUniqueVisitingUrls() = logSequencePersistence.getUniqueVisitingUrls()

    override fun getLogVisitedEventsForVisitingUrl(visitingUrl: String) = logSequencePersistence.getLogVisitedEventsForVisitingUrl(visitingUrl)

    override fun clear() = logSequencePersistence.clear()

    override fun close() = logSequencePersistence.close()
}