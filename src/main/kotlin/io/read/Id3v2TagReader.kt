package io.read

import model.header.Id3v2Header
import model.header.Id3v2HeaderFlags
import model.header.Id3v2Version
import java.io.InputStream
import java.nio.charset.StandardCharsets

abstract class Id3v2TagReader : TagReader() {
    fun readHeader(stream: InputStream): Id3v2Header {
        val headerBytes = ByteArray(ID3V2_HEADER_LEN)
        stream.read(headerBytes)
        val identifier = String(headerBytes.copyOf(ID3V2_HEADER_IDENTIFIER_LEN), StandardCharsets.ISO_8859_1)
        if (identifier != "ID3") {
            throw Error("Invalid ID3v2Header identifier: $identifier")
        }

        val version = readVersion(headerBytes)
        val flags = readFlags()

        return Id3v2Header(identifier = identifier, version = version, flags = flags)
    }

    private fun readVersion(headerBytes: ByteArray): Id3v2Version {
        val majorVersion = headerBytes[3].toUByte().toInt()
        val minorVersion = headerBytes[4].toUByte().toInt()
        return if (majorVersion == 3 && minorVersion == 0) {
            Id3v2Version.Version2_3
        } else if (majorVersion == 2 && minorVersion == 0) {
            Id3v2Version.Version2_2
        } else {
            throw Error("Unrecognized ID3v2 version: $majorVersion.$minorVersion")
        }
    }

    private fun readFlags(): Id3v2HeaderFlags {

    }

    companion object {
        const val ID3V2_HEADER_LEN = 10
        const val ID3V2_HEADER_IDENTIFIER_LEN = 3
    }
}