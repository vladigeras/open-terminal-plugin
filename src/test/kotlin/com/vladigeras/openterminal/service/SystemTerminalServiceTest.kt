package com.vladigeras.openterminal.service

import com.vladigeras.openterminal.infra.Platform
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class SystemTerminalServiceTest {

    @Test
    fun `platform enum should have MAC value`() {
        assertEquals(Platform.MAC, Platform.valueOf("MAC"))
    }

    @Test
    fun `platform enum should have LINUX value`() {
        assertEquals(Platform.LINUX, Platform.valueOf("LINUX"))
    }

    @Test
    fun `platform enum should have WINDOWS value`() {
        assertEquals(Platform.WINDOWS, Platform.valueOf("WINDOWS"))
    }

    @Test
    fun `platform enum should have exactly 3 values`() {
        assertEquals(3, Platform.entries.size)
    }
}