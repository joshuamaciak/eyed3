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

    @Test
    fun `should throw when input is too small`() {
        stream = ByteArrayInputStream(identifier + version23 + ByteArray(1))
        assertThrows<UnexpectedEndOfStreamException> { headerReader.readHeader(stream) }
    }
    @Nested
    inner class Identifier {
        @Test
        fun `should decode identifier when present`() {
            val header = headerReader.readHeader(stream)
            assertEquals(Id3v2HeaderReader.ID3V2_HEADER_IDENTIFIER, header.identifier)
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
            assertEquals(Id3v2Version.Version2_3, header.version)
        }

        @Test
        fun `should set version to 2_2 when v2 is present`() {
            stream = ByteArrayInputStream(identifier + version22 + ByteArray(5))
            val header = headerReader.readHeader(stream)
            assertEquals(Id3v2Version.Version2_2, header.version)
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

    @Nested
    inner class Size {
        @Test
        fun `should decode synchsafe size`() {
            stream = ByteArrayInputStream(identifier + version23 + flagsUnsynchronization + synchsafeSize)
            val header = headerReader.readHeader(stream)
            assertEquals(header.size, 556679)
        }

        @Test
        fun `should throw when size is invalid`() {
            stream = ByteArrayInputStream(identifier + version23 + flagsUnsynchronization + invalidSize)
            assertThrows<InvalidSizeException> { headerReader.readHeader(stream) }
        }
    }

    companion object {
        private val identifier = byteArrayOf(0x49, 0x44, 0x33)
        private val identifierUnknown = byteArrayOf(0x00, 0x00, 0x00)
        private val version23 = byteArrayOf(0x03, 0x00)
        private val version22 = byteArrayOf(0x02, 0x00)
        private val versionUnknown = byteArrayOf(0x01, 0x00)

        // synchsafe version of 0x087E87 == 556679
        private val synchsafeSize = byteArrayOf(0x0, 0b00100001, 0b01111101, 0b00000111)
        private val invalidSize = byteArrayOf(0x0, 0x80.toByte(), 0x81.toByte(), 0x80.toByte())

        private const val flagsUnsynchronization = 0b10000000.toByte()
        private const val flagsExtendedHeader = 0b01000000.toByte()
        private const val flagsExperimental = 0b00100000.toByte()
    }
}
