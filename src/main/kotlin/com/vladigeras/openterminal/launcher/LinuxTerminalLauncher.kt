package com.vladigeras.openterminal.launcher

import com.intellij.execution.ExecutionException
import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.openapi.diagnostic.Logger
import java.io.IOException

private val logger = Logger.getInstance(LinuxTerminalLauncher::class.java)

class LinuxTerminalLauncher : TerminalLauncher {

    override fun launch(dir: String) {
        val commands = buildTerminalCommands(dir)
        var lastEx: Exception? = null
        for (cmd in commands) {
            try {
                cmd.createProcess()
                logger.info("Terminal opened via: ${cmd.exePath}")
                return
            } catch (e: Exception) {
                // ExecutionException / IOException just mean "this emulator isn't
                // installed, try the next one". Anything else is a real bug.
                if (e !is ExecutionException && e !is IOException) throw e
                lastEx = e
            }
        }
        throw IllegalStateException("No compatible terminal emulator found in \$PATH", lastEx)
    }

    /**
     * Returns the ordered list of terminal launch commands to try on Linux.
     * The first command to successfully spawn wins.
     */
    internal fun buildTerminalCommands(dir: String): List<GeneralCommandLine> = listOf(
        GeneralCommandLine("gnome-terminal", "--working-directory=$dir"),
        GeneralCommandLine("konsole", "--workdir", dir, "--new"),
        GeneralCommandLine("xfce4-terminal", "--disable-server", "--working-directory=$dir"),
        GeneralCommandLine("mate-terminal", "--working-directory=$dir"),
        GeneralCommandLine("xterm", "-e", "bash", "-c", "cd '${escapeSingleQuotes(dir)}' && exec bash"),
    )

    /**
     * Escapes single quotes for safe embedding inside a bash single-quoted string.
     * `foo'bar` -> `foo'\''bar`.
     */
    internal fun escapeSingleQuotes(value: String): String = value.replace("'", "'\\''")
}
