package com.vladigeras.openterminal.infra

import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType.ERROR
import com.intellij.notification.NotificationType.INFORMATION
import com.intellij.openapi.project.Project

object Notifier {
    private val notificationGroup = NotificationGroupManager.getInstance()
        .getNotificationGroup("OpenTerminalActionNotificationGroup")

    fun showNotificationAboutNoActiveProject(project: Project?) = notificationGroup
        .createNotification("Active project is absent", "You need to open a project", INFORMATION)
        .apply { icon = Icons.TOOL }
        .notify(project)

    fun showError(project: Project?, message: String) = notificationGroup
        .createNotification("Error", message, ERROR)
        .apply { icon = Icons.TOOL }
        .notify(project)
}