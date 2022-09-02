package io.read

import java.io.InputStream
import java.lang.Exception

class ReadUtils {
    companion object {
        private const val MPEG_SYNC_FIRST_BYTE = 0xFF

        fun intFromBytes(bytes: List<Int>): Int {
            return bytes[0] shl (24) and
                    bytes[1] shl (16) and
                    bytes[2] shl (8) and
                    bytes[3]
        }

        /**
         * Performs a read of `size` bytes from a stream of bytes. If unsynchronized is true,
         * this method will synchronize as it reads.
         */
        fun readSynchronized(stream: InputStream, size: Int, unsynchronized: Boolean): List<Int> {
            val bytes = mutableListOf<Int>()
            var prev: Int? = null
            while (bytes.size < size) {
                val byte = stream.read()
                if (byte == -1) {
                    throw UnexpectedEndOfStreamException()
                }
                if (!unsynchronized || prev != MPEG_SYNC_FIRST_BYTE || byte != 0x00) {
                    bytes.add(byte)
                }
                prev = byte
            }
            return bytes
        }
    }
}

class UnexpectedEndOfStreamException : Exception("Stream ended unexpectedly while reading header")
