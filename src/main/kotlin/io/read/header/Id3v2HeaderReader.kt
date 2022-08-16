package io.read.header

import model.header.Id3v2Header
import model.header.Id3v2HeaderFlags
import model.header.Id3v2Version
import java.io.InputStream
import java.lang.Exception
import java.nio.charset.StandardCharsets

class Id3v2HeaderReader : HeaderReader {
    override fun readHeader(stream: InputStream): Id3v2Header {
        val headerBytes = ByteArray(ID3V2_HEADER_LEN)
        stream.read(headerBytes)
        val identifier = String(headerBytes.copyOf(ID3V2_HEADER_IDENTIFIER_LEN), StandardCharsets.ISO_8859_1)
        if (identifier != ID3V2_HEADER_IDENTIFIER) {
            throw InvalidIdentifierException("Invalid ID3v2Header identifier: $identifier")
        }

        val version = readVersion(headerBytes)
        val flags = readFlags(headerBytes)
        // TODO: size
        return Id3v2Header(identifier = identifier, version = version, flags = flags, size = 0.toUInt())
    }

    private fun readVersion(headerBytes: ByteArray): Id3v2Version {
        val majorVersion = headerBytes[3].toUByte().toInt()
        val minorVersion = headerBytes[4].toUByte().toInt()
        return if (majorVersion == 3 && minorVersion == 0) {
            Id3v2Version.Version2_3
        } else if (majorVersion == 2 && minorVersion == 0) {
            Id3v2Version.Version2_2
        } else {
            throw UnrecognizedVersionException("Unrecognized ID3v2 version: $majorVersion.$minorVersion")
        }
    }

    private fun readFlags(headerBytes: ByteArray): Id3v2HeaderFlags {
        val flags = headerBytes[5].toInt()
        val unsynchronization = ((flags and 0b10000000) shr (7))
        val extendedHeader = ((flags and 0b01000000) shr (6))
        val experimental = ((flags and 0b00100000) shr (5))

        return Id3v2HeaderFlags(
            unsynchronization = unsynchronization == 1,
            extendedHeader = extendedHeader == 1,
            experimental = experimental == 1
        )
    }

    companion object {
        const val ID3V2_HEADER_LEN = 10
        const val ID3V2_HEADER_IDENTIFIER_LEN = 3
        const val ID3V2_HEADER_IDENTIFIER = "ID3"
    }
}

class UnrecognizedVersionException(message: String) : Exception(message)
class InvalidIdentifierException(message: String) : Exception(message)