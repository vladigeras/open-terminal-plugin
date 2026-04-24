package com.vladigeras.openterminal

import com.vladigeras.openterminal.infra.Platform
import com.vladigeras.openterminal.infra.PlatformDetector
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class PlatformDetectorTest {

    @Test
    fun `detect should return valid platform for current OS`() {
        val result = PlatformDetector.detect()

        assertTrue(result in Platform.entries, "Platform should be one of MAC, LINUX, or WINDOWS")
        assertEquals(Platform.entries.size, 3, "Should have exactly 3 platforms")
    }
}