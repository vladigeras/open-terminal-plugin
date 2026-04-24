package com.vladigeras.openterminal.launcher

import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.openapi.diagnostic.Logger
import java.io.IOException

private val logger = Logger.getInstance(LinuxTerminalLauncher::class.java)

class LinuxTerminalLauncher : TerminalLauncher {
    override fun launch(dir: String) {
        val terminals = arrayOf(
            arrayOf("gnome-terminal", "--working-directory=$dir"),
            arrayOf("konsole", "--workdir", dir, "--new"),
            arrayOf("xfce4-terminal", "--disable-server", "--working-directory=$dir"),
            arrayOf("mate-terminal", "--working-directory=$dir"),
            arrayOf("xterm", "-e", "bash", "-c", "cd '" + dir.replace("'", "'\\''") + "' && exec bash")
        )
        var lastEx: Exception? = null
        for (cmd in terminals) {
            try {
                GeneralCommandLine(*cmd).createProcess()
                logger.info("Terminal opened via: " + cmd[0])
                return
            } catch (e: IOException) {
                lastEx = e
            }
        }
        throw IllegalStateException("No compatible terminal emulator found in \$PATH", lastEx)
    }
}