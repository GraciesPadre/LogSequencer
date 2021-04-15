package org.doomer.logsequencer.log_entry_graph

interface LogEntryGraph {
    fun getMostFrequentlyVisitedSequences(): List<String>
}