package com.vladigeras.openterminal

class PlatformDetector {

    companion object {
        const val OS_NAME_PROPERTY = "os.name"
    }

    open fun detect(): Platform {
        val os = System.getProperty(OS_NAME_PROPERTY) ?: throw UnsupportedOperationException("OS name property not found")
        return when {
            os.lowercase().contains("mac") -> Platform.MAC
            os.lowercase().contains("linux") -> Platform.LINUX
            os.lowercase().contains("win") -> Platform.WINDOWS
            else -> throw UnsupportedOperationException("Unsupported OS: $os")
        }
    }
}