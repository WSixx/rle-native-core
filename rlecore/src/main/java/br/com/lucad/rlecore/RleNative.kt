package br.com.lucad.rlecore

class RleNative {
    external fun parseLog(text: String, keyword: String): IntArray?

    companion object {
        init {
            System.loadLibrary("rlecore")
        }
    }
}
