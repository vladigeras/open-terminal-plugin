package com.vladigeras.openterminal.launcher

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class MacOsTerminalLauncherTest {

    private val launcher = MacOsTerminalLauncher()

    @Test
    fun `buildCommand targets the 'open' utility`() {
        val cmd = launcher.buildCommand("/Users/foo/project")
        assertEquals("open", cmd.exePath)
    }

    @Test
    fun `buildCommand passes -a Terminal and the directory`() {
        val cmd = launcher.buildCommand("/Users/foo/project")
        assertEquals(listOf("-a", "Terminal", "/Users/foo/project"), cmd.parametersList.parameters)
    }

    @Test
    fun `buildCommand sets working directory to the target dir`() {
        val cmd = launcher.buildCommand("/Users/foo/project")
        assertEquals("/Users/foo/project", cmd.workDirectory?.path)
    }

    @Test
    fun `buildCommand keeps paths with spaces as a single argument`() {
        val cmd = launcher.buildCommand("/Users/foo/my project")
        assertEquals(3, cmd.parametersList.parameters.size)
        assertEquals("/Users/foo/my project", cmd.parametersList.parameters[2])
    }

    @Test
    fun `buildCommand keeps paths with quotes as a single argument`() {
        // GeneralCommandLine is responsible for OS-level quoting, we must not
        // pre-escape the characters ourselves.
        val dir = "/Users/foo/weird\"name"
        val cmd = launcher.buildCommand(dir)
        assertEquals(dir, cmd.parametersList.parameters[2])
    }

    @Test
    fun `buildCommand keeps paths with single quotes as a single argument`() {
        val dir = "/Users/foo/it's/ok"
        val cmd = launcher.buildCommand(dir)
        assertEquals(dir, cmd.parametersList.parameters[2])
    }
}
