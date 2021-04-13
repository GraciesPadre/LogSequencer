package org.doomer.logsequencer.data_store.persistence

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.core.content.contentValuesOf
import org.doomer.logsequencer.data_store.persistence.LogSequenceDatabaseSchema.LogSequenceDatabaseSchema.LOG_ENTRY_ID
import org.doomer.logsequencer.data_store.persistence.LogSequenceDatabaseSchema.LogSequenceDatabaseSchema.LOG_SEQUENCE_VISITING_URL
import org.doomer.logsequencer.data_store.persistence.LogSequenceDatabaseSchema.LogSequenceDatabaseSchema.LOG_SEQUENCE_VISITED_URL
import org.doomer.logsequencer.data_store.persistence.LogSequenceDatabaseSchema.LogSequenceDatabaseSchema.TABLE_NAME
import java.text.DateFormat
import java.text.DateFormat.getDateInstance

class LogSequenceDatabase(private val db: SQLiteDatabase, private val dateFormatter: DateFormat): LogSequencePersistence {
    constructor(context: Context): this(LogSequenceDatabaseHelper(context).writableDatabase, getDateInstance())

    override fun recordLogVisitedEvent(logSequenceEntry: LogSequenceEntry): Long {
        val dbTableEntry = contentValuesOf(
            LOG_SEQUENCE_VISITING_URL to logSequenceEntry.visitingUrl,
            LOG_SEQUENCE_VISITED_URL to logSequenceEntry.visitedUrl
        )

        return db.insert(TABLE_NAME, null, dbTableEntry)
    }

    override fun recordLogVisitedEvents(logSequenceEntries: List<LogSequenceEntry>) {
        TODO("Not yet implemented")
    }

    override fun getAllLogVisitedEvents(): List<LogSequenceEntry> {
        val result = mutableListOf<LogSequenceEntry>()

        val cursor = LogSequenceEntryWrapper(
            db.query(
                TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                LOG_ENTRY_ID
            )
        )

        cursor.use { dbCursor ->
            dbCursor.moveToFirst()

            while (!dbCursor.isAfterLast) {
                result.add(dbCursor.getLogSequenceEntry())
                dbCursor.moveToNext()
            }
        }

        return result
    }

    override fun getUniqueVisitingUrls(): List<String> {
        TODO("Not yet implemented")
    }

    override fun clear() {
        db.delete(TABLE_NAME, null, null)
    }

    override fun close() {
        db.close()
    }


}
