package org.doomer.logsequencer.data_store.persistence

class LogSequenceDatabaseSchema {
    object LogSequenceDatabaseSchema {
        const val DATABASE_NAME = "log_sequence_database"
        const val DATABASE_VERSION = 1
        const val TABLE_NAME = "log_sequence_events"
        const val LOG_ENTRY_ID = "_id"
        const val LOG_SEQUENCE_VISITING_URL = "visiting_url"
        const val LOG_SEQUENCE_VISITED_URL = "visited_url"
    }
}
