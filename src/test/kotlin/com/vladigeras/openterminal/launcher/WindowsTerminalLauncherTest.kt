package com.vladigeras.openterminal.launcher

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.condition.EnabledOnOs
import org.junit.jupiter.api.condition.OS.WINDOWS
import kotlin.test.assertEquals

@EnabledOnOs(WINDOWS)
class WindowsTerminalLauncherTest {

    private val launcher = WindowsTerminalLauncher()

    @Test
    fun `normalizePath converts forward slashes to backslashes`() {
        assertEquals("C:\\Users\\Test\\project", launcher.normalizePath("C:/Users/Test/project"))
    }

    @Test
    fun `normalizePath leaves native backslash paths unchanged`() {
        assertEquals("C:\\Users\\Test\\project", launcher.normalizePath("C:\\Users\\Test\\project"))
    }

    @Test
    fun `normalizePath normalizes mixed separators`() {
        assertEquals("C:\\Users\\Test\\sub\\dir", launcher.normalizePath("C:/Users\\Test/sub\\dir"))
    }

    @Test
    fun `normalizePath preserves spaces in path`() {
        assertEquals("C:\\Program Files\\MyApp", launcher.normalizePath("C:/Program Files/MyApp"))
    }

    @Test
    fun `buildWindowsTerminalCommand targets wt exe`() {
        val cmd = launcher.buildWindowsTerminalCommand("C:\\projects\\demo")
        assertEquals("wt.exe", cmd.exePath)
    }

    @Test
    fun `buildWindowsTerminalCommand passes directory via -d flag`() {
        val cmd = launcher.buildWindowsTerminalCommand("C:\\projects\\demo")
        assertEquals(listOf("-d", "C:\\projects\\demo"), cmd.parametersList.parameters)
    }

    @Test
    fun `buildWindowsTerminalCommand keeps path with spaces as a single argument`() {
        val cmd = launcher.buildWindowsTerminalCommand("C:\\Program Files\\MyApp")
        // Must stay exactly 2 args: GeneralCommandLine handles OS-level quoting,
        // the path must not leak into multiple tokens.
        assertEquals(2, cmd.parametersList.parameters.size)
        assertEquals("C:\\Program Files\\MyApp", cmd.parametersList.parameters[1])
    }

    @Test
    fun `buildCmdStartCommand targets cmd exe`() {
        val cmd = launcher.buildCmdStartCommand("C:\\projects\\demo")
        assertEquals("cmd.exe", cmd.exePath)
    }

    @Test
    fun `buildCmdStartCommand uses 'start' with empty title and opens a new cmd window`() {
        val cmd = launcher.buildCmdStartCommand("C:\\projects\\demo")
        // The empty "" after `start` is the mandatory window title placeholder —
        // otherwise `start` would treat the path as the title and no console
        // window would appear. This was the root cause of the original defect.
        assertEquals(
            listOf("/c", "start", "", "/D", "C:\\projects\\demo", "cmd.exe"),
            cmd.parametersList.parameters
        )
    }

    @Test
    fun `buildCmdStartCommand preserves path with spaces as a single argument`() {
        val cmd = launcher.buildCmdStartCommand("C:\\Program Files\\MyApp")
        val params = cmd.parametersList.parameters
        assertEquals(6, params.size)
        assertEquals("/D", params[3])
        assertEquals("C:\\Program Files\\MyApp", params[4])
    }

    @Test
    fun `buildCmdStartCommand preserves empty window title as its own argument`() {
        val cmd = launcher.buildCmdStartCommand("C:\\demo")
        // The "" must be at index 2, not merged away — regression guard.
        assertEquals("", cmd.parametersList.parameters[2])
    }
}
