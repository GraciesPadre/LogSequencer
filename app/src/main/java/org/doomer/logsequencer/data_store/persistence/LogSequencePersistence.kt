package org.doomer.logsequencer.data_store.persistence

interface LogSequencePersistence {
    fun recordLogVisitedEvent(logSequenceEntry: LogSequenceEntry): Long
    fun recordLogVisitedEvents(logSequenceEntries: List<LogSequenceEntry>)
    fun getAllLogVisitedEvents(): List<LogSequenceEntry>
    fun getUniqueVisitingUrls(): List<String>
    fun getLogVisitedEventsForVisitingUrl(visitingUrl: String): List<LogSequenceEntry>
    fun clear()
    fun close()
}