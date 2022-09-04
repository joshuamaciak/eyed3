package io.read.frame.header

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.io.ByteArrayInputStream

internal class Id3v2FrameHeaderReaderTest {
    private val reader = Id3v2FrameHeaderReader()
    private var stream = ByteArrayInputStream(id + size + flags)

    @BeforeEach
    fun setup() {
        stream = ByteArrayInputStream(id + size + flags)
    }

    @Nested
    inner class Id {
        @Test
        fun `should decode id`() {
            val header = reader.read(stream)
            assertEquals("ABCD", header.id)
        }
    }

    @Nested
    inner class Size {
        @Test
        fun `should decode size`() {
            val header = reader.read(stream)
            assertEquals(4456730, header.size)
        }
    }

    @Nested
    inner class Flags {
        @Nested
        inner class TagAlterPreservation {
            @Test
            fun `should set tagAlterPreservation to true when set`() {
                stream = ByteArrayInputStream(id + size + flagsTagAlterPreservation)
                val header = reader.read(stream)
                assertTrue(header.flags.tagAlterPreservation)
            }

            @Test
            fun `should set tagAlterPreservation to false when not set`() {
                stream = ByteArrayInputStream(id + size + flagsNone)
                val header = reader.read(stream)
                assertFalse(header.flags.tagAlterPreservation)
            }
        }

        @Nested
        inner class FileAlterPreservation {
            @Test
            fun `should set fileAlterPreservation to true when set`() {
                stream = ByteArrayInputStream(id + size + flagsFileAlterPreservation)
                val header = reader.read(stream)
                assertTrue(header.flags.fileAlterPreservation)
            }

            @Test
            fun `should set fileAlterPreservation to false when not set`() {
                stream = ByteArrayInputStream(id + size + flagsNone)
                val header = reader.read(stream)
                assertFalse(header.flags.fileAlterPreservation)
            }
        }

        @Nested
        inner class Readonly {
            @Test
            fun `should set readonly to true when set`() {
                stream = ByteArrayInputStream(id + size + flagsReadonly)
                val header = reader.read(stream)
                assertTrue(header.flags.readonly)
            }

            @Test
            fun `should set readonly to false when not set`() {
                stream = ByteArrayInputStream(id + size + flagsNone)
                val header = reader.read(stream)
                assertFalse(header.flags.readonly)
            }
        }

        @Nested
        inner class Compression {
            @Test
            fun `should set compression to true when set`() {
                stream = ByteArrayInputStream(id + size + flagsCompression)
                val header = reader.read(stream)
                assertTrue(header.flags.compression)
            }

            @Test
            fun `should set compression to false when not set`() {
                stream = ByteArrayInputStream(id + size + flagsNone)
                val header = reader.read(stream)
                assertFalse(header.flags.compression)
            }
        }

        @Nested
        inner class Encryption {
            @Test
            fun `should set encryption to true when set`() {
                stream = ByteArrayInputStream(id + size + flagsEncryption)
                val header = reader.read(stream)
                assertTrue(header.flags.encryption)
            }

            @Test
            fun `should set encryption to false when not set`() {
                stream = ByteArrayInputStream(id + size + flagsNone)
                val header = reader.read(stream)
                assertFalse(header.flags.encryption)
            }
        }

        @Nested
        inner class GroupingIdentity {
            @Test
            fun `should set groupingIdentity to true when set`() {
                stream = ByteArrayInputStream(id + size + flagsGroupingIdentity)
                val header = reader.read(stream)
                assertTrue(header.flags.groupingIdentity)
            }

            @Test
            fun `should set groupingIdentity to false when not set`() {
                stream = ByteArrayInputStream(id + size + flagsNone)
                val header = reader.read(stream)
                assertFalse(header.flags.groupingIdentity)
            }
        }
    }

    companion object {
        private val id = byteArrayOf(0x41, 0x42, 0x43, 0x44) // == ABCD
        private val size = byteArrayOf(0x00, 0x44, 0x01, 0x1A) // == 4456730
        private val flags = byteArrayOf(0b11000000.toByte(), 0b01000000)
        private val flagsTagAlterPreservation = byteArrayOf(0b10000000.toByte(), 0x00)
        private val flagsFileAlterPreservation = byteArrayOf(0b01000000, 0x00)
        private val flagsReadonly = byteArrayOf(0b00100000, 0x00)
        private val flagsCompression = byteArrayOf(0x0, 0b10000000.toByte())
        private val flagsEncryption = byteArrayOf(0x0, 0b01000000)
        private val flagsGroupingIdentity = byteArrayOf(0x0, 0b00100000)
        private val flagsNone = byteArrayOf(0x00, 0x00)

    }
}