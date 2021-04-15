package org.doomer.logsequencer.log_entry_graph

import org.doomer.logsequencer.data_store.persistence.LogSequenceEntry
import org.doomer.logsequencer.data_store.repository.LogSequenceRepository
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class LogEntryGraphTest {
    @Test
    fun testAddingEntriesFor1VisitingIpAddress() {
        val logSequenceRepository = mock(LogSequenceRepository::class.java)

        val logEntryGraphImpl = LogEntryGraphImpl(logSequenceRepository)
        logEntryGraphImpl.addVisitedUrlsToGraph(listOf(
            LogSequenceEntry("a", "1"),
            LogSequenceEntry("a", "2"),
            LogSequenceEntry("a", "3"),
            LogSequenceEntry("a", "1"),
            LogSequenceEntry("a", "2"),
            LogSequenceEntry("a", "3"),
            LogSequenceEntry("a", "4"),
        ))

        val vertex1 = logEntryGraphImpl.getVertex("1")
        assertNotNull(vertex1)
        assertEquals("LogEntryVertex(visitedUrl='1', logEntryEdges={2=LogEntryEdge(logEntryVertex=LogEntryVertex(visitedUrl='2', logEntryEdges={}), count=2)})", vertex1.toString())

        val vertex2 = logEntryGraphImpl.getVertex("2")
        assertNotNull(vertex2)
        assertEquals("LogEntryVertex(visitedUrl='2', logEntryEdges={3=LogEntryEdge(logEntryVertex=LogEntryVertex(visitedUrl='3', logEntryEdges={}), count=2)})", vertex2.toString())

        val vertex3 = logEntryGraphImpl.getVertex("3")
        assertNotNull(vertex3)
        assertEquals("LogEntryVertex(visitedUrl='3', logEntryEdges={1=LogEntryEdge(logEntryVertex=LogEntryVertex(visitedUrl='1', logEntryEdges={}), count=1), 4=LogEntryEdge(logEntryVertex=LogEntryVertex(visitedUrl='4', logEntryEdges={}), count=1)})", vertex3.toString())
    }

    /*@Test
    fun testMakeLogSequenceGraphWithNoVisitedUrls() {
        val logSequenceRepository = mock(LogSequenceRepository::class.java)
        `when`(logSequenceRepository.getUniqueVisitingIpAddresses()).thenReturn(listOf("a", "b"))

        val logEntryGraphImpl = LogEntryGraphImpl(logSequenceRepository)
        val rootVertex = logEntryGraphImpl.makeLogSequenceGraph()

        Assert.assertNull(rootVertex.getEdgeWithLargestNumConnections())
    }



    @Test
    fun testMakeLogSequenceGraphEmptyVisitedUrls() {
        val logSequenceRepository = mock(LogSequenceRepository::class.java)
        `when`(logSequenceRepository.getUniqueVisitingIpAddresses()).thenReturn(listOf("a", "b"))
        `when`(logSequenceRepository.getLogVisitedUrlsForVisitingIpAddress("a")).thenReturn(listOf())
        `when`(logSequenceRepository.getLogVisitedUrlsForVisitingIpAddress("b")).thenReturn(listOf())

        val logEntryGraphImpl = LogEntryGraphImpl(logSequenceRepository)
        val rootVertex = logEntryGraphImpl.makeLogSequenceGraph()

        Assert.assertNull(rootVertex.getEdgeWithLargestNumConnections())
    }

    @Test
    fun testMakeLogSequenceGraph1VisitedUrl() {
        val logSequenceRepository = mock(LogSequenceRepository::class.java)
        `when`(logSequenceRepository.getUniqueVisitingIpAddresses()).thenReturn(listOf("a", "b"))
        `when`(logSequenceRepository.getLogVisitedUrlsForVisitingIpAddress("a")).thenReturn(listOf(
            LogSequenceEntry("a", "1")
        ))
        `when`(logSequenceRepository.getLogVisitedUrlsForVisitingIpAddress("b")).thenReturn(listOf(
            LogSequenceEntry("b", "1")
        ))

        val logEntryGraphImpl = LogEntryGraphImpl(logSequenceRepository)
        val rootVertex = logEntryGraphImpl.makeLogSequenceGraph()

        val edgeWithMostConnections = rootVertex.getEdgeWithLargestNumConnections()
        Assert.assertNotNull(edgeWithMostConnections)
        Assert.assertEquals(2, edgeWithMostConnections?.count)
        Assert.assertEquals("1", edgeWithMostConnections?.logEntryVertex?.visitedUrl)
    }

    @Test
    fun testMakeLogSequenceGraph3VisitedUrls() {
        val logSequenceRepository = mock(LogSequenceRepository::class.java)
        `when`(logSequenceRepository.getUniqueVisitingIpAddresses()).thenReturn(listOf("a", "b"))
        `when`(logSequenceRepository.getLogVisitedUrlsForVisitingIpAddress("a")).thenReturn(listOf(
            LogSequenceEntry("a", "1"),
            LogSequenceEntry("a", "2"),
            LogSequenceEntry("a", "3")
        ))
        `when`(logSequenceRepository.getLogVisitedUrlsForVisitingIpAddress("b")).thenReturn(listOf(
            LogSequenceEntry("b", "1"),
            LogSequenceEntry("b", "2"),
            LogSequenceEntry("b", "3")
        ))

        val logEntryGraphImpl = LogEntryGraphImpl(logSequenceRepository)
        val rootVertex = logEntryGraphImpl.makeLogSequenceGraph()

        val edgeWithMostConnections = rootVertex.getEdgeWithLargestNumConnections()
        Assert.assertNotNull(edgeWithMostConnections)
        Assert.assertEquals(2, edgeWithMostConnections?.count)
        Assert.assertEquals("1", edgeWithMostConnections?.logEntryVertex?.visitedUrl)
    }

    @Test
    fun testAdding1VisitedUrlToVertex() {
        val logSequenceRepository = mock(LogSequenceRepository::class.java)
        `when`(logSequenceRepository.getUniqueVisitingUrls()).thenReturn(listOf("a", "b"))
        `when`(logSequenceRepository.getLogVisitedEventsForVisitingUrl("a")).thenReturn(listOf(
            LogSequenceEntry("a", "1")
        ))
        `when`(logSequenceRepository.getLogVisitedEventsForVisitingUrl("b")).thenReturn(listOf(
            LogSequenceEntry("b", "1")
        ))

        val logEntryGraphImpl = LogEntryGraphImpl(logSequenceRepository)
        val startingVertexes = logEntryGraphImpl.makeStartingVertexes()

        val vertex = logEntryGraphImpl.addVisitedUrlsToVertex(logSequenceRepository.getLogVisitedEventsForVisitingUrl("a"), startingVertexes[0])

        val edgeWithMostConnections = vertex.getEdgeWithLargestNumConnections()

        Assert.assertNotNull(edgeWithMostConnections)
        Assert.assertEquals(1, edgeWithMostConnections?.count)
        Assert.assertEquals("1", edgeWithMostConnections?.logEntryVertex?.visitedUrl)
    }

    @Test
    fun testAdding3VisitedUrlsToVertex() {
        val logSequenceRepository = mock(LogSequenceRepository::class.java)
        `when`(logSequenceRepository.getUniqueVisitingUrls()).thenReturn(listOf("a", "b"))
        `when`(logSequenceRepository.getLogVisitedEventsForVisitingUrl("a")).thenReturn(listOf(
            LogSequenceEntry("a", "1"),
            LogSequenceEntry("a", "2"),
            LogSequenceEntry("a", "3")
        ))
        `when`(logSequenceRepository.getLogVisitedEventsForVisitingUrl("b")).thenReturn(listOf(
            LogSequenceEntry("b", "1"),
            LogSequenceEntry("b", "2"),
            LogSequenceEntry("b", "3")
        ))

        val logEntryGraphImpl = LogEntryGraphImpl(logSequenceRepository)
        val startingVertexes = logEntryGraphImpl.makeStartingVertexes()

        val vertex = logEntryGraphImpl.addVisitedUrlsToVertex(logSequenceRepository.getLogVisitedEventsForVisitingUrl("a"), startingVertexes[0])

        val edgeWithMostConnections = vertex.getEdgeWithLargestNumConnections()

        Assert.assertNotNull(edgeWithMostConnections)
        Assert.assertEquals(1, edgeWithMostConnections?.count)
        println(edgeWithMostConnections)
    }*/
}



