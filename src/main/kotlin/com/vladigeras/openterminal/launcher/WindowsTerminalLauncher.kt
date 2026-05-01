package com.vladigeras.openterminal.launcher

import com.intellij.execution.ExecutionException
import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.openapi.diagnostic.Logger
import java.io.IOException

private val logger = Logger.getInstance(WindowsTerminalLauncher::class.java)

class WindowsTerminalLauncher : TerminalLauncher {

    override fun launch(dir: String) {
        val normalized = normalizePath(dir)
        val commandBuilders: List<(String) -> GeneralCommandLine> = listOf(
            ::buildWindowsTerminalCommand,
            ::buildCmdStartCommand,
        )
        var lastEx: Exception? = null
        for (build in commandBuilders) {
            val cmd = build(normalized)
            try {
                cmd.createProcess()
                logger.info("Terminal opened via: ${cmd.exePath}")
                return
            } catch (e: Exception) {
                // ExecutionException (GeneralCommandLine) and IOException (JDK process)
                // are the expected spawn failures; both mean "try the next candidate".
                if (e !is ExecutionException && e !is IOException) throw e
                lastEx = e
                logger.info("Failed to open terminal via ${cmd.exePath}: ${e.message}")
            }
        }
        throw IllegalStateException("No compatible terminal emulator found on Windows (tried: wt.exe, cmd.exe)", lastEx)
    }

    /**
     * Normalizes the path separators for Windows. IDE-provided project base paths
     * typically use forward slashes, while Windows tools expect backslashes.
     */
    internal fun normalizePath(dir: String): String = dir.replace("/", "\\")

    /**
     * Builds a command for the modern Windows Terminal (wt.exe).
     * `-d <dir>` opens a new tab/window with the given starting directory.
     * Available on Windows 10/11 when Windows Terminal is installed.
     */
    internal fun buildWindowsTerminalCommand(dir: String) = GeneralCommandLine("wt.exe", "-d", dir)

    /**
     * Builds a command for the classic cmd.exe.
     *
     * We cannot just do `GeneralCommandLine("cmd.exe", "/k", ...)`: when a GUI
     * process (IDEA's javaw.exe) spawns cmd.exe directly, the child has no
     * attached console host and no visible window appears. Using `start` forces
     * Windows to allocate a fresh console and spawn cmd.exe in it.
     *
     * The empty "" after `start` is the window title — it is required because
     * `start` otherwise treats the first quoted argument as the title.
     *
     * `/D <dir>` sets the starting directory without manual quoting; GeneralCommandLine
     * handles quoting of paths containing spaces.
     */
    internal fun buildCmdStartCommand(dir: String) =
        GeneralCommandLine("cmd.exe", "/c", "start", "", "/D", dir, "cmd.exe")
}
