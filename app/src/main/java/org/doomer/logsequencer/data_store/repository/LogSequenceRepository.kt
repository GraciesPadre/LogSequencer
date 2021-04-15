package org.doomer.logsequencer.data_store.repository

import org.doomer.logsequencer.data_store.persistence.LogSequenceEntry

interface LogSequenceRepository {
    fun recordLogVisitedUrl(logSequenceEntry: LogSequenceEntry): Boolean
    fun recordLogVisitedUrls(logSequenceEntries: List<LogSequenceEntry>)
    fun getUniqueVisitingIpAddresses(): List<String>
    fun getLogVisitedUrlsForVisitingIpAddress(visitingUrl: String): List<LogSequenceEntry>
    fun clear()
    fun close()
}