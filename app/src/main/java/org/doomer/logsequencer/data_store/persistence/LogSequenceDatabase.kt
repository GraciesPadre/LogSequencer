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
        val sql = "INSERT INTO $TABLE_NAME ($LOG_SEQUENCE_VISITING_URL, $LOG_SEQUENCE_VISITED_URL) VALUES(?, ?)"
        val compiledSql = db.compileStatement(sql)

        db.beginTransaction()

        try {
            compiledSql.clearBindings()

            for (logSequenceEntry in logSequenceEntries) {
                compiledSql.bindString(ColumnIndexes.LOG_SEQUENCE_VISITING_URL_COLUMN_INDEX, logSequenceEntry.visitingUrl)
                compiledSql.bindString(ColumnIndexes.LOG_SEQUENCE_VISITED_URL_COLUMN_INDEX, logSequenceEntry.visitedUrl)

                compiledSql.execute()
            }

            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
        }
    }

    private object ColumnIndexes {
        const val LOG_SEQUENCE_VISITING_URL_COLUMN_INDEX = 1
        const val LOG_SEQUENCE_VISITED_URL_COLUMN_INDEX = 2
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
        val result = mutableListOf<String>()

        val columnIndex = 0

        val cursor = db.query(true, TABLE_NAME, arrayOf(LOG_SEQUENCE_VISITING_URL), null, null, null, null, LOG_ENTRY_ID, null)

        cursor.use { dbCursor ->
            dbCursor.moveToFirst()

            while (!dbCursor.isAfterLast) {
                result.add(dbCursor.getString(columnIndex))
                dbCursor.moveToNext()
            }
        }

        return result
    }

    override fun clear() {
        db.delete(TABLE_NAME, null, null)
    }

    override fun close() {
        db.close()
    }


}
