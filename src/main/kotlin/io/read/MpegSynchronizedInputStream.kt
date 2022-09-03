package io.read

import java.io.InputStream

/** An input stream that reads a potentially-unsynchronized stream as a synchronized stream of bytes. */
class MpegSynchronizedInputStream(private val inputStream: InputStream, private val unsynchronized: Boolean) :
    InputStream() {
    var prevByte: Int? = null
    override fun read(): Int {
        var byte = inputStream.read()
        if (!unsynchronized) {
            prevByte = byte
            return byte
        }
        if (prevByte == MPEG_SYNC_FIRST_BYTE && byte == 0x00) {
            // skip this byte because it was a false sync remedy
            byte = inputStream.read()
        }
        prevByte = byte
        return byte
    }

    companion object {
        private const val MPEG_SYNC_FIRST_BYTE = 0xFF
    }
}