package com.vladigeras.openterminal

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.diagnostic.Logger
import java.io.IOException

class OpenTerminalAction(
    private val platformDetector: PlatformDetector = PlatformDetector()
) : AnAction() {

    private val logger = Logger.getInstance(OpenTerminalAction::class.java)

    override fun actionPerformed(e: AnActionEvent) {
        logger.info("Opening terminal")
        openSystemTerminal()
    }

    private fun openSystemTerminal() {
        try {
            val platform = platformDetector.detect()
            logger.info("Opening terminal for platform $platform")
            val command = platform.command.split(" ").toTypedArray()
            Runtime.getRuntime().exec(command).waitFor()
            logger.info("Terminal opened successfully")
        } catch (e: IOException) {
            logger.error("Failed to open terminal", e)
        } catch (e: InterruptedException) {
            logger.error("Failed to open terminal", e)
        }
    }
}