package com.vladigeras.openterminal

import com.vladigeras.openterminal.infra.Platform
import com.vladigeras.openterminal.infra.PlatformDetector
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class PlatformDetectorTest {

    @Test
    fun `detect should return valid platform for current OS`() {
        val result = PlatformDetector.detect()
        assertTrue(result in Platform.entries)
        assertEquals(3, Platform.entries.size)
    }

    @Test
    fun `platform enum should have all expected values`() {
        assertEquals(Platform.MAC, Platform.valueOf("MAC"))
        assertEquals(Platform.LINUX, Platform.valueOf("LINUX"))
        assertEquals(Platform.WINDOWS, Platform.valueOf("WINDOWS"))
    }

    @Test
    fun `platform entries should contain exactly 3 values`() {
        val platforms = Platform.entries.toList()
        assertEquals(3, platforms.size)
        assertTrue(platforms.contains(Platform.MAC))
        assertTrue(platforms.contains(Platform.LINUX))
        assertTrue(platforms.contains(Platform.WINDOWS))
    }
}