package com.vladigeras.openterminal.launcher

import com.intellij.execution.configurations.GeneralCommandLine

class MacOsTerminalLauncher : TerminalLauncher {

    override fun launch(dir: String) {
        buildCommand(dir).createProcess()
    }

    internal fun buildCommand(dir: String) = GeneralCommandLine("open", "-a", "Terminal", dir)
        .withWorkDirectory(dir)
}
