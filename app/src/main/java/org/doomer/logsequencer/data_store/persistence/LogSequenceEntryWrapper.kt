package org.doomer.logsequencer.data_store.persistence

import android.database.Cursor
import android.database.CursorWrapper
import org.doomer.logsequencer.data_store.persistence.LogSequenceDatabaseSchema.LogSequenceDatabaseSchema.LOG_SEQUENCE_VISITED_URL
import org.doomer.logsequencer.data_store.persistence.LogSequenceDatabaseSchema.LogSequenceDatabaseSchema.LOG_SEQUENCE_VISITING_URL

class LogSequenceEntryWrapper(cursor: Cursor): CursorWrapper(cursor) {
    fun getLogSequenceEntry() : LogSequenceEntry {
        val visitingUrl = getString(getColumnIndex(LOG_SEQUENCE_VISITING_URL))
        val visitedUrl = getString(getColumnIndex(LOG_SEQUENCE_VISITED_URL))

        return LogSequenceEntry(visitingUrl, visitedUrl)
    }
}