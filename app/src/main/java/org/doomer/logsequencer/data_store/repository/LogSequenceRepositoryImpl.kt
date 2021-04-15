package org.doomer.logsequencer.data_store.repository

import org.doomer.logsequencer.data_store.persistence.LogSequenceEntry
import org.doomer.logsequencer.data_store.persistence.LogSequencePersistence

class LogSequenceRepositoryImpl(private val logSequencePersistence: LogSequencePersistence): LogSequenceRepository {
    override fun recordLogVisitedUrl(logSequenceEntry: LogSequenceEntry) = logSequencePersistence.recordLogVisitedUrl(logSequenceEntry) > -1

    override fun recordLogVisitedUrls(logSequenceEntries: List<LogSequenceEntry>) = logSequencePersistence.recordLogVisitedUrls(logSequenceEntries)

    override fun getUniqueVisitingIpAddresses() = logSequencePersistence.getUniqueVisitingIpAddresses()

    override fun getLogVisitedUrlsForVisitingIpAddress(visitingUrl: String) = logSequencePersistence.getLogVisitedUrlsForVisitingIpAddress(visitingUrl)

    override fun clear() = logSequencePersistence.clear()

    override fun close() = logSequencePersistence.close()
}