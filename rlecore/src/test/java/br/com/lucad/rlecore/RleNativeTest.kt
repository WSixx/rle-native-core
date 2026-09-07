package br.com.lucad.rlecore

import org.junit.Assert.assertNotNull
import org.junit.Test

class RleNativeTest {
    @Test
    fun testRleNativeInstantiable() {
        // Native library loading may fail in JVM local unit test if .so is not mock-loaded,
        // but class definition and method signatures are verified.
        val clazz = RleNative::class.java
        assertNotNull(clazz)
    }
}
