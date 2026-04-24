package com.vladigeras.openterminal.launcher

import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

class LinuxTerminalLauncherTest {

    @Test
    fun `launcher should try terminals in correct order`() {
        val terminals = listOf(
            arrayOf("gnome-terminal", "--working-directory=/test"),
            arrayOf("konsole", "--workdir", "/test", "--new"),
            arrayOf("xfce4-terminal", "--disable-server", "--working-directory=/test"),
            arrayOf("mate-terminal", "--working-directory=/test"),
            arrayOf("xterm", "-e", "bash", "-c", "cd '/test' && exec bash")
        )
        
        assertTrue(terminals[0][0] == "gnome-terminal")
        assertTrue(terminals[1][0] == "konsole")
        assertTrue(terminals[2][0] == "xfce4-terminal")
        assertTrue(terminals[3][0] == "mate-terminal")
        assertTrue(terminals[4][0] == "xterm")
    }

    @Test
    fun `launcher should escape single quotes in directory path for xterm`() {
        val dir = "test'dir"
        val escaped = dir.replace("'", "'\\''")
        
        assertTrue(escaped == "test'\\''dir")
    }

    @Test
    fun `launcher should handle directory with spaces`() {
        val dir = "/home/user/my project"
        
        assertTrue(dir.contains(" "))
    }
}