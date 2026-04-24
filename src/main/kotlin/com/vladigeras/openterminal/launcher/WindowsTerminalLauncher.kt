package com.vladigeras.openterminal.launcher

import com.intellij.execution.configurations.GeneralCommandLine

class WindowsTerminalLauncher : TerminalLauncher {
    override fun launch(dir: String) {
        GeneralCommandLine("cmd.exe", "/c", "start", "\"\"", "/d", dir, "cmd.exe")
            .withWorkDirectory(dir)
            .createProcess()
    }
}