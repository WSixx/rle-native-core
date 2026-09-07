package br.com.lucad.rlecore

class RleNative {

    companion object {
        init {
            System.loadLibrary("rlecore")
        }
    }
}
