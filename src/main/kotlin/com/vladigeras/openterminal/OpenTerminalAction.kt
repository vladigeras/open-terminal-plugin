package com.vladigeras.openterminal

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.diagnostic.Logger

class OpenTerminalAction : AnAction() {

    private val logger = Logger.getInstance(OpenTerminalAction::class.java)

    override fun actionPerformed(e: AnActionEvent) {
        logger.info("OpenTerminal action clicked")
    }
}