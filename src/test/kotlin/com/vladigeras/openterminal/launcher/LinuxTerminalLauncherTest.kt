package com.vladigeras.openterminal.launcher

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.condition.EnabledOnOs
import org.junit.jupiter.api.condition.OS.LINUX
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@EnabledOnOs(LINUX)
class LinuxTerminalLauncherTest {

    private val launcher = LinuxTerminalLauncher()

    @Test
    fun `buildTerminalCommands returns the expected fallback order`() {
        val commands = launcher.buildTerminalCommands("/home/user/project")
        val executables = commands.map { it.exePath }
        assertEquals(
            listOf("gnome-terminal", "konsole", "xfce4-terminal", "mate-terminal", "xterm"),
            executables
        )
    }

    @Test
    fun `buildTerminalCommands contains exactly 5 candidates`() {
        assertEquals(5, launcher.buildTerminalCommands("/home/user/project").size)
    }

    @Test
    fun `gnome-terminal uses --working-directory flag`() {
        val cmd = launcher.buildTerminalCommands("/home/user/project")[0]
        assertEquals(listOf("--working-directory=/home/user/project"), cmd.parametersList.parameters)
    }

    @Test
    fun `konsole uses --workdir and --new flags`() {
        val cmd = launcher.buildTerminalCommands("/home/user/project")[1]
        assertEquals(
            listOf("--workdir", "/home/user/project", "--new"),
            cmd.parametersList.parameters
        )
    }

    @Test
    fun `xfce4-terminal disables server and uses --working-directory flag`() {
        val cmd = launcher.buildTerminalCommands("/home/user/project")[2]
        assertEquals(
            listOf("--disable-server", "--working-directory=/home/user/project"),
            cmd.parametersList.parameters
        )
    }

    @Test
    fun `mate-terminal uses --working-directory flag`() {
        val cmd = launcher.buildTerminalCommands("/home/user/project")[3]
        assertEquals(
            listOf("--working-directory=/home/user/project"),
            cmd.parametersList.parameters
        )
    }

    @Test
    fun `xterm runs a bash login that cd's into the directory`() {
        val cmd = launcher.buildTerminalCommands("/home/user/project")[4]
        assertEquals(
            listOf("-e", "bash", "-c", "cd '/home/user/project' && exec bash"),
            cmd.parametersList.parameters
        )
    }

    @Test
    fun `xterm command safely escapes single quotes in directory path`() {
        val cmd = launcher.buildTerminalCommands("/home/user/it's/project")[4]
        val bashInline = cmd.parametersList.parameters.last()
        // classic single-quote-escape inside single-quoted bash literal
        assertEquals("cd '/home/user/it'\\''s/project' && exec bash", bashInline)
    }

    @Test
    fun `paths with spaces are preserved as single arguments`() {
        val commands = launcher.buildTerminalCommands("/home/user/my project")
        // konsole keeps dir as a dedicated argument — regression guard
        val konsole = commands[1]
        assertTrue(konsole.parametersList.parameters.contains("/home/user/my project"))
    }

    @Test
    fun `escapeSingleQuotes leaves plain paths untouched`() {
        assertEquals("/home/user/project", launcher.escapeSingleQuotes("/home/user/project"))
    }

    @Test
    fun `escapeSingleQuotes escapes a single quote`() {
        assertEquals("it'\\''s", launcher.escapeSingleQuotes("it's"))
    }

    @Test
    fun `escapeSingleQuotes escapes multiple single quotes`() {
        assertEquals("'\\'''\\''", launcher.escapeSingleQuotes("''"))
    }
}
