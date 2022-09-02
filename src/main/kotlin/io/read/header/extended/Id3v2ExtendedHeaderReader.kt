package io.read.header.extended

import io.read.ReadUtils
import model.header.extended.Id3v2ExtendedHeader
import model.header.extended.Id3v2ExtendedHeaderFlags
import java.io.InputStream

class Id3v2ExtendedHeaderReader {
    fun read(stream: InputStream, unsynchronized: Boolean): Id3v2ExtendedHeader {
        val size = readSize(stream, unsynchronized)
        val flags = readExtendedFlags(stream, unsynchronized)
        val sizeOfPadding = readSize(stream, unsynchronized)
        val crcData = if (flags.crcDataPresent) readCrcData(stream, unsynchronized) else null
        return Id3v2ExtendedHeader(size = size, flags = flags, sizeOfPadding = sizeOfPadding, crcData = crcData)
    }


    private fun readSize(stream: InputStream, unsynchronized: Boolean): Int {
        val sizeBytes = ReadUtils.readSynchronized(stream, LEN_SIZE, unsynchronized)
        return ReadUtils.intFromBytes(sizeBytes)
    }

    private fun readExtendedFlags(stream: InputStream, unsynchronized: Boolean): Id3v2ExtendedHeaderFlags {
        val flagBytes = ReadUtils.readSynchronized(stream, LEN_FLAGS, unsynchronized)
        val crcDataPresent = (flagBytes[0] and 0b10000000 shr (7)) == 1
        return Id3v2ExtendedHeaderFlags(crcDataPresent = crcDataPresent)
    }

    private fun readCrcData(stream: InputStream, unsynchronized: Boolean): ByteArray {
        return ReadUtils.readSynchronized(stream, LEN_CRC32, unsynchronized).map { it.toByte() }.toByteArray()
    }

    companion object {
        private const val LEN_SIZE = 4
        private const val LEN_FLAGS = 2
        private const val LEN_CRC32 = 4
    }
}