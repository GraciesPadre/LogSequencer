package org.doomer.logsequencer.connection_graph

import org.junit.Test

import org.junit.Assert.*

class LogEntryVertexTest {
    @Test
    fun testGettingEdgeWithNoConnections() {
        assertNull(LogEntryVertex("1").getEdgeWithLargestNumConnections())
    }

    @Test
    fun testAddingVertexToVertex() {
        val one = LogEntryVertex("1")
        val edge = one.addEdge(LogEntryVertex("2")).getEdgeWithLargestNumConnections()
        assertEquals("2", edge?.logEntryVertex?.logEntryUrl)
        assertEquals(1, edge?.count)
    }

    @Test
    fun testAddingTheSameVertexToVertexTwice() {
        val one = LogEntryVertex("1")
        val two = LogEntryVertex("2")
        val edge = one.addEdge(two).addEdge(two).getEdgeWithLargestNumConnections()
        assertEquals("2", edge?.logEntryVertex?.logEntryUrl)
        assertEquals(2, edge?.count)
    }
}