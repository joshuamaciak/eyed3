package io.read.frame.header

import io.read.Reader
import io.read.UnexpectedEndOfStreamException
import model.Flags
import model.frame.header.Id3v23FrameHeader
import model.frame.header.Id3v2FrameFlags
import java.io.InputStream
import java.nio.ByteBuffer
import java.nio.charset.StandardCharsets

class Id3v2FrameHeaderReader : Reader<Id3v23FrameHeader> {
    override fun read(stream: InputStream): Id3v23FrameHeader {
        return Id3v23FrameHeader(id = readId(stream), size = readSize(stream), flags = readFlags(stream))
    }

    private fun readId(stream: InputStream): String {
        val idBytes = ByteArray(LEN_ID)
        val bytesRead = stream.read(idBytes)
        if (bytesRead != idBytes.size) {
            throw UnexpectedEndOfStreamException()
        }
        return idBytes.toString(StandardCharsets.ISO_8859_1)
    }

    private fun readSize(stream: InputStream): Int {
        val sizeBytes = ByteArray(LEN_SIZE)
        val bytesRead = stream.read(sizeBytes)
        if (bytesRead != sizeBytes.size) {
            throw UnexpectedEndOfStreamException()
        }
        return ByteBuffer.wrap(sizeBytes).int
    }

    private fun readFlags(stream: InputStream): Id3v2FrameFlags {
        val flagBytes = ByteArray(LEN_FLAGS_SIZE)
        val bytesRead = stream.read(flagBytes)
        if (bytesRead != flagBytes.size) {
            throw UnexpectedEndOfStreamException()
        }

        val firstFlags = Flags(flagBytes[0])
        val secondFlags = Flags(flagBytes[1])
        return Id3v2FrameFlags(
            tagAlterPreservation = firstFlags.isSet(8),
            fileAlterPreservation = firstFlags.isSet(7),
            readonly = firstFlags.isSet(6),
            compression = secondFlags.isSet(8),
            encryption = secondFlags.isSet(7),
            groupingIdentity = secondFlags.isSet(6),
        )
    }

    companion object {
        private const val LEN_ID = 4
        private const val LEN_SIZE = 4
        private const val LEN_FLAGS_SIZE = 2
    }
}