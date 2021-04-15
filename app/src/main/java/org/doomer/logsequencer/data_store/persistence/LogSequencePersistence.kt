package org.doomer.logsequencer.data_store.persistence

interface LogSequencePersistence {
    fun recordLogVisitedUrl(logSequenceEntry: LogSequenceEntry): Long
    fun recordLogVisitedUrls(logSequenceEntries: List<LogSequenceEntry>)
    fun getAllLogVisitedUrls(): List<LogSequenceEntry>
    fun getUniqueVisitingIpAddresses(): List<String>
    fun getLogVisitedUrlsForVisitingIpAddress(ipAddress: String): List<LogSequenceEntry>
    fun clear()
    fun close()
}