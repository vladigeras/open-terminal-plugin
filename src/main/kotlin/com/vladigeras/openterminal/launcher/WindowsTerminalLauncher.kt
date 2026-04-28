package com.vladigeras.openterminal.launcher

import com.intellij.execution.configurations.GeneralCommandLine

class WindowsTerminalLauncher : TerminalLauncher {
    override fun launch(dir: String) {
        val escapedDir = dir.replace("/", "\\")
        GeneralCommandLine("cmd.exe", "/k", "cd /d \"$escapedDir\"").createProcess()
    }
}