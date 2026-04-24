package com.vladigeras.openterminal.launcher

import com.intellij.execution.configurations.GeneralCommandLine

class MacOsTerminalLauncher : TerminalLauncher {
    override fun launch(dir: String) {
        val escaped = dir.replace("\"", "\\\"")
        val script = "tell application \"Terminal\" to do script \"cd \\\"$escaped\\\"\""
        GeneralCommandLine("osascript", "-e", script).createProcess()
    }
}