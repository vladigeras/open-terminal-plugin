package com.vladigeras.openterminal.infra

import com.vladigeras.openterminal.infra.Platform.LINUX
import com.vladigeras.openterminal.infra.Platform.MAC
import com.vladigeras.openterminal.infra.Platform.WINDOWS
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.condition.EnabledOnOs
import org.junit.jupiter.api.condition.OS
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class PlatformDetectorTest {

    @Test
    @EnabledOnOs(OS.MAC)
    fun `detect should return valid platform for MAC OS`() {
        assertEquals(MAC, PlatformDetector.detect())
    }

    @Test
    @EnabledOnOs(OS.LINUX)
    fun `detect should return valid platform for LINUX OS`() {
        assertEquals(LINUX, PlatformDetector.detect())
    }

    @Test
    @EnabledOnOs(OS.WINDOWS)
    fun `detect should return valid platform for WINDOWS OS`() {
        assertEquals(WINDOWS, PlatformDetector.detect())
    }

    @Test
    fun `detect should return valid platform for current OS`() {
        val result = PlatformDetector.detect()
        assertTrue(result in Platform.entries)
        assertEquals(3, Platform.entries.size)
    }

    @Test
    fun `platform enum should have all expected values`() {
        assertEquals(MAC, Platform.valueOf("MAC"))
        assertEquals(LINUX, Platform.valueOf("LINUX"))
        assertEquals(WINDOWS, Platform.valueOf("WINDOWS"))
    }

    @Test
    fun `platform entries should contain exactly 3 values`() {
        val platforms = Platform.entries.toList()
        assertEquals(3, platforms.size)
        assertTrue(platforms.contains(MAC))
        assertTrue(platforms.contains(LINUX))
        assertTrue(platforms.contains(WINDOWS))
    }
}