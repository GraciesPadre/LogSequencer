package org.doomer.logsequencer.data_store.persistence

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import org.doomer.logsequencer.data_store.persistence.LogSequenceDatabaseSchema.LogSequenceDatabaseSchema.DATABASE_NAME
import org.doomer.logsequencer.data_store.persistence.LogSequenceDatabaseSchema.LogSequenceDatabaseSchema.DATABASE_VERSION
import org.doomer.logsequencer.data_store.persistence.LogSequenceDatabaseSchema.LogSequenceDatabaseSchema.TABLE_NAME
import org.doomer.logsequencer.data_store.persistence.LogSequenceDatabaseSchema.LogSequenceDatabaseSchema.LOG_ENTRY_ID
import org.doomer.logsequencer.data_store.persistence.LogSequenceDatabaseSchema.LogSequenceDatabaseSchema.LOG_SEQUENCE_VISITING_URL
import org.doomer.logsequencer.data_store.persistence.LogSequenceDatabaseSchema.LogSequenceDatabaseSchema.LOG_SEQUENCE_VISITED_URL

class LogSequenceDatabaseHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("create table $TABLE_NAME ($LOG_ENTRY_ID INTEGER primary key autoincrement, $LOG_SEQUENCE_VISITING_URL TEXT, $LOG_SEQUENCE_VISITED_URL TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
}