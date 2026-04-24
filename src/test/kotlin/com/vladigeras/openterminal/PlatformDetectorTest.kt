package com.vladigeras.openterminal

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class PlatformDetectorTest {

    private lateinit var detector: PlatformDetector

    @BeforeEach
    fun setUp() {
        detector = PlatformDetector()
    }

    @ParameterizedTest
    @CsvSource(
        "Mac OS X, MAC",
        "macOS, MAC",
        "Mac OS, MAC",
        "Linux, LINUX",
        "Ubuntu, LINUX",
        "Windows 10, WINDOWS",
        "Windows Server 2019, WINDOWS",
        "Windows, WINDOWS"
    )
    fun `detect should return correct platform based on os name`(osName: String, expectedPlatform: String) {
        System.setProperty(PlatformDetector.OS_NAME_PROPERTY, osName)

        val result = detector.detect()

        assertEquals(Platform.valueOf(expectedPlatform), result)
    }

    @Test
    fun `detect should throw UnsupportedOperationException for unsupported OS`() {
        System.setProperty(PlatformDetector.OS_NAME_PROPERTY, "FreeBSD")

        assertThrows<UnsupportedOperationException> {
            detector.detect()
        }
    }

    @Test
    fun `detect should throw UnsupportedOperationException when os name property is null`() {
        System.clearProperty(PlatformDetector.OS_NAME_PROPERTY)

        assertThrows<UnsupportedOperationException> {
            detector.detect()
        }
    }

    @Test
    fun `detect should return valid platform for current OS without mocking`() {
        val result = detector.detect()

        assertTrue(result in Platform.entries, "Platform should be one of MAC, LINUX, or WINDOWS")
        assertEquals(Platform.entries.size, 3, "Should have exactly 3 platforms")
    }

    @AfterEach
    fun tearDown() {
        System.clearProperty(PlatformDetector.OS_NAME_PROPERTY)
    }
}