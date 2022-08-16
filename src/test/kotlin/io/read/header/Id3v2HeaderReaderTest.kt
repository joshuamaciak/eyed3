package io.read.header

import model.header.Id3v2Version
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.io.ByteArrayInputStream
import kotlin.test.assertEquals


internal class Id3v2HeaderReaderTest {
    private val headerReader = Id3v2HeaderReader()
    private var stream = ByteArrayInputStream(identifier + version23 + ByteArray(5))

    @Nested
    inner class Identifier {
        @Test
        fun `should decode identifier when present`() {
            val header = headerReader.readHeader(stream)
            assertEquals(header.identifier, Id3v2HeaderReader.ID3V2_HEADER_IDENTIFIER)
        }

        @Test
        fun `should throw when invalid identifier is present`() {
            stream = ByteArrayInputStream(identifierUnknown + version23 + ByteArray(5))
            assertThrows<InvalidIdentifierException> { headerReader.readHeader(stream) }
        }
    }

    @Nested
    inner class Version {
        @Test
        fun `should set version to 2_3 when v3 is present`() {
            val header = headerReader.readHeader(stream)
            assertEquals(header.version, Id3v2Version.Version2_3)
        }

        @Test
        fun `should set version to 2_2 when v2 is present`() {
            stream = ByteArrayInputStream(identifier + version22 + ByteArray(5))
            val header = headerReader.readHeader(stream)
            assertEquals(header.version, Id3v2Version.Version2_2)
        }

        @Test
        fun `should throw when unrecognized version is present`() {
            stream = ByteArrayInputStream(identifier + versionUnknown + ByteArray(5))
            assertThrows<UnrecognizedVersionException> { headerReader.readHeader(stream) }
        }
    }

    @Nested
    inner class Flags {
        @Test
        fun `should set unsychronization to true when flag is set`() {
            stream = ByteArrayInputStream(identifier + version23 + flagsUnsynchronization + ByteArray(4))
            val header = headerReader.readHeader(stream)
            assertTrue(header.flags.unsynchronization)
        }

        @Test
        fun `should set extendedHeader to true when flag is set`() {
            stream = ByteArrayInputStream(identifier + version23 + flagsExtendedHeader + ByteArray(4))
            val header = headerReader.readHeader(stream)
            assertTrue(header.flags.extendedHeader)
        }

        @Test
        fun `should set experimental to true when flag is set`() {
            stream = ByteArrayInputStream(identifier + version23 + flagsExperimental + ByteArray(4))
            val header = headerReader.readHeader(stream)
            assertTrue(header.flags.experimental)
        }
    }

    companion object {
        private val identifier = byteArrayOf(0x49, 0x44, 0x33)
        private val identifierUnknown = byteArrayOf(0x00, 0x00, 0x00)
        private val version23 = byteArrayOf(0x03, 0x00)
        private val version22 = byteArrayOf(0x02, 0x00)
        private val versionUnknown = byteArrayOf(0x01, 0x00)
        private const val flagsUnsynchronization = 0b10000000.toByte()
        private const val flagsExtendedHeader = 0b01000000.toByte()
        private const val flagsExperimental = 0b00100000.toByte()
    }
}