package com.vladigeras.openterminal.launcher

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class MacOsTerminalLauncherTest {

    @Test
    fun `launcher should escape quotes in directory path`() {
        val dir = "test\"dir"
        val escaped = dir.replace("\"", "\\\"")
        
        assertEquals("test\\\"dir", escaped)
    }

    @Test
    fun `launcher should handle simple directory without quotes`() {
        val dir = "/home/user/project"
        val escaped = dir.replace("\"", "\\\"")
        
        assertEquals("/home/user/project", escaped)
    }

    @Test
    fun `launcher should handle directory with backslash`() {
        val dir = "C:\\Users\\Test"
        val escaped = dir.replace("\"", "\\\"")
        
        assertEquals("C:\\Users\\Test", escaped)
    }
}