package com.vladigeras.openterminal.launcher

import com.intellij.execution.configurations.GeneralCommandLine

class MacOsTerminalLauncher : TerminalLauncher {
    override fun launch(dir: String) {
        val escaped = dir.replace("\"", "\\\"")
        GeneralCommandLine("open", "-a", "Terminal", escaped)
            .withWorkDirectory(dir)
            .createProcess()
    }
}