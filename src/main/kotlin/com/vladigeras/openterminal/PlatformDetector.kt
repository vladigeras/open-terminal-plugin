package com.vladigeras.openterminal

import com.intellij.openapi.util.SystemInfo

object PlatformDetector {

    fun detect(): Platform = when {
        SystemInfo.isMac -> Platform.MAC
        SystemInfo.isLinux -> Platform.LINUX
        SystemInfo.isWindows -> Platform.WINDOWS
        else -> throw UnsupportedOperationException("Unsupported OS: ${SystemInfo.OS_NAME}")
    }
}