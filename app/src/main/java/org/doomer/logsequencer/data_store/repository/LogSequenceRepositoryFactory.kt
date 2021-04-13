package org.doomer.logsequencer.data_store.repository

import android.content.Context
import org.doomer.logsequencer.data_store.persistence.LogSequenceDatabase

object LogSequenceRepositoryFactory {
    private var logSequenceRepository: LogSequenceRepository? = null

    @Synchronized
    fun getLogSequenceRepository(context: Context): LogSequenceRepository {
        if (logSequenceRepository == null) {
            logSequenceRepository = LogSequenceRepositoryImpl(LogSequenceDatabase(context))
        }

        return logSequenceRepository as LogSequenceRepository
    }
}