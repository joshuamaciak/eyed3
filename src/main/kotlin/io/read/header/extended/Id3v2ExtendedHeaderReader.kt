package io.read.header.extended

import io.read.UnexpectedEndOfStreamException
import model.header.extended.Id3v2ExtendedHeader
import model.header.extended.Id3v2ExtendedHeaderFlags
import java.io.InputStream
import java.nio.ByteBuffer

class Id3v2ExtendedHeaderReader {
    fun read(stream: InputStream): Id3v2ExtendedHeader {
        val size = readSize(stream)
        val flags = readExtendedFlags(stream)
        val sizeOfPadding = readSize(stream)
        val crcData = if (flags.crcDataPresent) readCrcData(stream) else null
        return Id3v2ExtendedHeader(size = size, flags = flags, sizeOfPadding = sizeOfPadding, crcData = crcData)
    }

    private fun readSize(stream: InputStream): Int {
        val sizeBytes = ByteArray(LEN_SIZE)
        val bytesRead = stream.read(sizeBytes)
        if (bytesRead != sizeBytes.size) {
            throw UnexpectedEndOfStreamException()
        }
        return ByteBuffer.wrap(sizeBytes).int
    }

    private fun readExtendedFlags(stream: InputStream): Id3v2ExtendedHeaderFlags {
        val flagBytes = ByteArray(LEN_FLAGS)
        val bytesRead = stream.read(flagBytes)
        if (bytesRead != flagBytes.size) {
            throw UnexpectedEndOfStreamException()
        }
        val crcDataPresent = (flagBytes[0].toInt() and 0b10000000 shr (7)) == 1
        return Id3v2ExtendedHeaderFlags(crcDataPresent = crcDataPresent)
    }

    private fun readCrcData(stream: InputStream): ByteArray {
        val crcBytes = ByteArray(LEN_CRC32)
        val bytesRead = stream.read(crcBytes)
        if (bytesRead != crcBytes.size) {
            throw UnexpectedEndOfStreamException()
        }
        return crcBytes
    }

    companion object {
        private const val LEN_SIZE = 4
        private const val LEN_FLAGS = 2
        private const val LEN_CRC32 = 4
    }
}