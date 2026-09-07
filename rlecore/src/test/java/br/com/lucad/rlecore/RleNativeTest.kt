package br.com.lucad.rlecore

import org.junit.Assert.assertNotNull
import org.junit.Test

class RleNativeTest {
    @Test
    fun testRleNativeInstantiable() {
        val clazz = RleNative::class.java
        assertNotNull(clazz)
    }
}
