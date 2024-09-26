package br.com.caju.challenge.util

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class CentsToRealTest {

    @Test
    fun `CentsToReal instance should be defined `() {
        assertIs<CentsToReal>(CentsToReal())
    }

    @Test
    fun `convert should return 100 when 1_00 is passed`() {
        val result = CentsToReal.convert(1.00)
        assertEquals(100, result)
    }

    @Test
    fun `convert should return 250 when 2_50 is passed`() {
        val result = CentsToReal.convert(2.50)
        assertEquals(250, result)
    }

    @Test
    fun `convert should return 12345 when 123_45 is passed`() {
        val result = CentsToReal.convert(123.45)
        assertEquals(12345, result)
    }

    @Test
    fun `convert should return 0 when 0_0 is passed`() {
        val result = CentsToReal.convert(0.0)
        assertEquals(0, result)
    }

    @Test
    fun `convert should return negative cents for negative real values`() {
        val result = CentsToReal.convert(-5.75)
        assertEquals(-575, result)
    }
}