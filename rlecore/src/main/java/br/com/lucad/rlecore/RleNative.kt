package br.com.lucad.rlecore

class RleNative {

    external fun compressRle(input: ByteArray): ByteArray
  //  external fun decompressRle(input: ByteArray): ByteArray

    companion object {
        init {
            System.loadLibrary("rlecore")
        }
    }
}
