package io.read.header.extended


import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.io.ByteArrayInputStream

internal class Id3v2ExtendedHeaderReaderTest {
    private val reader = Id3v2ExtendedHeaderReader()
    private var stream = ByteArrayInputStream(size + flagsCrcPresent + sizePadding + crcData)

    @BeforeEach
    fun setup() {
        stream = ByteArrayInputStream(size + flagsCrcPresent + sizePadding + crcData)
    }

    @Nested
    inner class Size {
        @Test
        fun `should decode size`() {
            val header = reader.read(stream)
            assertEquals(273, header.size)
        }
    }

    @Nested
    inner class Flags {
        @Test
        fun `should set crcDataPresent to true when flag is set`() {
            val header = reader.read(stream)
            assertTrue(header.flags.crcDataPresent)
        }

        @Test
        fun `should set crcDataPresent to false when flag is not set`() {
            stream = ByteArrayInputStream(size + flagsCrcNotPresent + sizePadding)
            val header = reader.read(stream)
            assertFalse(header.flags.crcDataPresent)
        }
    }

    @Nested
    inner class PaddingSize {
        @Test
        fun `should decode padding size`() {
            val header = reader.read(stream)
            assertEquals(563, header.sizeOfPadding)
        }
    }

    @Nested
    inner class CrcData {
        @Test
        fun `should read crcData if crcDataPresent flag is true`() {
            val header = reader.read(stream)
            assertArrayEquals(crcData, header.crcData)
        }

        @Test
        fun `should not read crcData if crcDataPresent flag is false`() {
            stream = ByteArrayInputStream(size + flagsCrcNotPresent + sizePadding + crcData)
            val header = reader.read(stream)
            assertNull(header.crcData)
        }
    }

    companion object {
        private val size = byteArrayOf(0x00, 0x00, 0x01, 0x11)
        private val flagsCrcPresent = byteArrayOf(-127, 0x00)
        private val flagsCrcNotPresent = byteArrayOf(0x00, 0x00)
        private val crcData = byteArrayOf(0x01, 0x02, 0x03, 0x04)
        private val sizePadding = byteArrayOf(0x00, 0x00, 0x02, 0x33)
    }
}
