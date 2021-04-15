package org.doomer.logsequencer.log_entry_graph

import org.doomer.logsequencer.data_store.persistence.LogSequenceEntry
import org.doomer.logsequencer.data_store.repository.LogSequenceRepository

class LogEntryGraphImpl(private val logSequenceRepository: LogSequenceRepository): LogEntryGraph {
    private val vertexes = mutableMapOf<String, LogEntryVertex>()

    override fun getMostFrequentlyVisitedSequences(): List<String> {
        TODO("Not yet implemented")
    }

    fun makeLogSequenceGraph() {
        // return addLogSequenceGraphToRootNode(LogEntryVertex("", ""), logSequenceRepository.getUniqueVisitingUrls())
        addGraphEntriesForVisitingIpAddresses(logSequenceRepository.getUniqueVisitingIpAddresses())
    }

    fun addGraphEntriesForVisitingIpAddresses(visitingIpAddresses: List<String>) {
        /*for (visitingUrl in visitingUrls) {
            addVisitedUrlsToVertex(
                rootVertex,
                logSequenceRepository.getLogVisitedUrlsForVisitingUrl(visitingUrl)
            )
        }

        return rootVertex*/

        TODO("Not yet implemented")
    }

    fun addVisitedUrlsToGraph(visitedUrls: List<LogSequenceEntry>) {
        if (visitedUrls.isEmpty()) {
            return
        }

        val firstVisitedUrl = visitedUrls[0].visitedUrl
        var sourceVertex = LogEntryVertex(firstVisitedUrl)

        if (vertexes[firstVisitedUrl] == null) {
            vertexes[firstVisitedUrl] = sourceVertex
        }

        for (i in 1 until visitedUrls.size) {
            val recordedSourceIndex = vertexes[sourceVertex.visitedUrl]
            if (recordedSourceIndex != null) {
                sourceVertex = recordedSourceIndex
            }

            val destinationVertex = LogEntryVertex(visitedUrls[i].visitedUrl)
            sourceVertex.addEdge(destinationVertex)

            vertexes[sourceVertex.visitedUrl] = sourceVertex

            sourceVertex = destinationVertex
        }
    }

    fun getVertex(visitedUrl: String): LogEntryVertex? {
        return vertexes[visitedUrl]
    }
}
