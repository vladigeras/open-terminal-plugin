package com.vladigeras.openterminal.action

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys.PROJECT
import com.intellij.openapi.diagnostic.Logger
import com.vladigeras.openterminal.infra.Notifier
import com.vladigeras.openterminal.service.SystemTerminalService


private val logger = Logger.getInstance(OpenTerminalAction::class.java)

class OpenTerminalAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        val project = e.getData(PROJECT)
        project ?: run {
            logger.info("There is no active project")
            Notifier.showNotificationAboutNoActiveProject(e.project)
            return
        }
        val rootPath = project.basePath
        rootPath ?: run {
            Notifier.showError(project, "Project root directory is undefined.")
            return
        }
        SystemTerminalService.openTerminal(rootPath, project);
    }
}