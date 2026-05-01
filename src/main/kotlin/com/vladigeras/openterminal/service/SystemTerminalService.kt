package com.vladigeras.openterminal.service

import com.intellij.execution.ExecutionException
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.project.Project
import com.vladigeras.openterminal.infra.Notifier
import com.vladigeras.openterminal.infra.Platform
import com.vladigeras.openterminal.infra.Platform.*
import com.vladigeras.openterminal.infra.PlatformDetector
import com.vladigeras.openterminal.launcher.LinuxTerminalLauncher
import com.vladigeras.openterminal.launcher.MacOsTerminalLauncher
import com.vladigeras.openterminal.launcher.TerminalLauncher
import com.vladigeras.openterminal.launcher.WindowsTerminalLauncher
import java.io.IOException

private val logger = Logger.getInstance(SystemTerminalService::class.java)

object SystemTerminalService {

    fun openTerminal(directory: String, project: Project) {
        val platform = PlatformDetector.detect()
        val launcher = launcherFor(platform)
        ApplicationManager.getApplication().executeOnPooledThread {
            try {
                launcher.launch(directory)
                logger.info("Terminal opened successfully for $platform in: $directory")
            } catch (e: Exception) {
                if (e is ExecutionException || e is IOException || e is IllegalStateException) {
                    logger.warn("Failed to open terminal for platform $platform", e)
                } else {
                    logger.error("Unexpected error while opening terminal for platform $platform", e)
                }
                Notifier.showError(project, "Failed to open terminal for platform $platform: ${e.message}")
            }
        }
    }

    internal fun launcherFor(platform: Platform): TerminalLauncher = when (platform) {
        MAC -> MacOsTerminalLauncher()
        LINUX -> LinuxTerminalLauncher()
        WINDOWS -> WindowsTerminalLauncher()
    }
}
