package org.doomer.logsequencer.log_entry_graph

class LogEntryVertex(val logEntryUrl: String) {
    private val logEntryEdges = mutableMapOf<String, LogEntryEdge>()

    fun addEdge(logEntryVertex: LogEntryVertex): LogEntryVertex {
        val existingEdge = logEntryEdges[logEntryVertex.logEntryUrl]
        if (existingEdge == null) {
            logEntryEdges[logEntryVertex.logEntryUrl] = LogEntryEdge(logEntryVertex, 1)
        } else {
            logEntryEdges[logEntryVertex.logEntryUrl] = LogEntryEdge(logEntryVertex, existingEdge.count + 1)
        }

        return this
    }

    fun removeEdge(logEntryEdge: LogEntryEdge): LogEntryVertex {
        logEntryEdges.remove(logEntryEdge.logEntryVertex.logEntryUrl)
        return this
    }

    fun getEdgeWithLargestNumConnections() : LogEntryEdge? {
        var result: LogEntryEdge? = null
        var largestNumConnections = 0

        for (logEntryEdge in logEntryEdges) {
            if (logEntryEdge.value.count > largestNumConnections) {
                largestNumConnections = logEntryEdge.value.count
                result = logEntryEdge.value
            }
        }

        return result
    }
}
