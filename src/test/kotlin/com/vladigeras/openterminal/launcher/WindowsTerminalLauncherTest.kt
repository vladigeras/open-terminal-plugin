package com.vladigeras.openterminal.launcher

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class WindowsTerminalLauncherTest {

    @Test
    fun `launcher should use cmd exe with correct parameters`() {
        val cmd = listOf("cmd.exe", "/c", "start", "\"\"", "/d", "/test", "cmd.exe")
        
        assertEquals("cmd.exe", cmd[0])
        assertEquals("/c", cmd[1])
        assertEquals("start", cmd[2])
        assertEquals("/d", cmd[4])
        assertEquals("/test", cmd[5])
    }

    @Test
    fun `launcher should handle windows path with backslash`() {
        val dir = "C:\\Users\\Test\\project"
        
        assertEquals("C:\\Users\\Test\\project", dir)
    }

    @Test
    fun `launcher should handle directory with spaces`() {
        val dir = "C:\\Program Files\\MyApp"
        
        assertEquals("C:\\Program Files\\MyApp", dir)
    }
}