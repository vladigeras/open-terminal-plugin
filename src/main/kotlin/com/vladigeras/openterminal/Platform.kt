package com.vladigeras.openterminal

enum class Platform(val command: String) {
    MAC("open -a Terminal"),
    LINUX("x-terminal-emulator"),
    WINDOWS("cmd /c start cmd")
}