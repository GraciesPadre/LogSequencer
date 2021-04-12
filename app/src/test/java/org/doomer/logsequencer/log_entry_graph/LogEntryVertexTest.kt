package org.doomer.logsequencer.log_entry_graph

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

    @Test
    fun testRemovingEdgeWhenNoEdgesExist() {
        val one = LogEntryVertex("1").removeEdge(LogEntryEdge(LogEntryVertex("2"), 1))
        assertNull(one.getEdgeWithLargestNumConnections())
    }

    @Test
    fun testRemovingEdgeWhenThatEdgeDoesNotExist() {
        val one = LogEntryVertex("1")
        val two = LogEntryVertex("2")
        val edge = one.addEdge(two).addEdge(two).getEdgeWithLargestNumConnections()
        assertEquals("2", edge?.logEntryVertex?.logEntryUrl)
        assertEquals(2, edge?.count)

        val oneAfterRemovingEdge = one.removeEdge(LogEntryEdge(LogEntryVertex("3"), 1))
        val edgeAfterRemovingEdge = oneAfterRemovingEdge.getEdgeWithLargestNumConnections()

        assertEquals("2", edgeAfterRemovingEdge?.logEntryVertex?.logEntryUrl)
        assertEquals(2, edgeAfterRemovingEdge?.count)
    }

    @Test
    fun testRemovingEdge() {
        val one = LogEntryVertex("1")
        val two = LogEntryVertex("2")
        val three = LogEntryVertex("3")
        val edge = one.addEdge(two).addEdge(two).addEdge(three).addEdge(three).addEdge(three).getEdgeWithLargestNumConnections()
        assertEquals("3", edge?.logEntryVertex?.logEntryUrl)
        assertEquals(3, edge?.count)

        val oneAfterRemovingEdge = one.removeEdge(LogEntryEdge(LogEntryVertex("3"), 0))
        val edgeAfterRemovingEdge = oneAfterRemovingEdge.getEdgeWithLargestNumConnections()

        assertEquals("2", edgeAfterRemovingEdge?.logEntryVertex?.logEntryUrl)
        assertEquals(2, edgeAfterRemovingEdge?.count)
    }
}