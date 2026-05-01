package com.vladigeras.openterminal.service

import com.vladigeras.openterminal.infra.Platform
import com.vladigeras.openterminal.infra.Platform.LINUX
import com.vladigeras.openterminal.infra.Platform.MAC
import com.vladigeras.openterminal.infra.Platform.WINDOWS
import com.vladigeras.openterminal.launcher.LinuxTerminalLauncher
import com.vladigeras.openterminal.launcher.MacOsTerminalLauncher
import com.vladigeras.openterminal.launcher.WindowsTerminalLauncher
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.condition.EnabledOnOs
import org.junit.jupiter.api.condition.OS
import kotlin.test.assertTrue

class SystemTerminalServiceTest {

    @Test
    @EnabledOnOs(OS.MAC)
    fun `launcherFor MAC returns MacOsTerminalLauncher`() {
        assertTrue(SystemTerminalService.launcherFor(MAC) is MacOsTerminalLauncher)
    }

    @Test
    @EnabledOnOs(OS.LINUX)
    fun `launcherFor LINUX returns LinuxTerminalLauncher`() {
        assertTrue(SystemTerminalService.launcherFor(LINUX) is LinuxTerminalLauncher)
    }

    @Test
    @EnabledOnOs(OS.WINDOWS)
    fun `launcherFor WINDOWS returns WindowsTerminalLauncher`() {
        assertTrue(SystemTerminalService.launcherFor(WINDOWS) is WindowsTerminalLauncher)
    }

    @Test
    @EnabledOnOs(OS.MAC)
    fun `launcherFor returns a fresh instance per call`() {
        // Launchers hold no shared state, but confirm the factory does not
        // accidentally return a shared singleton, which could cause subtle
        // concurrency issues on the pooled executor.
        val a = SystemTerminalService.launcherFor(MAC)
        val b = SystemTerminalService.launcherFor(MAC)
        assertTrue(a !== b)
    }

    @Test
    fun `launcherFor covers every Platform value`() {
        // Regression guard: if a new Platform is added, the when() in
        // launcherFor must be updated — this test will fail to compile
        // or throw otherwise, making the omission impossible to miss.
        Platform.entries.forEach { SystemTerminalService.launcherFor(it) }
    }
}
