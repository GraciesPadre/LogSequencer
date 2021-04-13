package org.doomer.logsequencer.data_store.repository

import org.doomer.logsequencer.data_store.persistence.LogSequenceEntry

interface LogSequenceRepository {
    fun recordLogVisitedEvent(logSequenceEntry: LogSequenceEntry): Boolean
    fun recordLogVisitedEvents(logSequenceEntries: List<LogSequenceEntry>)
    fun getUniqueVisitingUrls(): List<String>
    fun getLogVisitedEventsForVisitingUrl(visitingUrl: String): List<LogSequenceEntry>
    fun clear()
    fun close()
}