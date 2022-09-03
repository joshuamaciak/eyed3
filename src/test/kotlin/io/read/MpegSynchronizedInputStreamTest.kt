package io.read

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.io.ByteArrayInputStream

internal class MpegSynchronizedInputStreamTest {

    @Nested
    inner class Synchronized {
        @Test
        fun `should read bytes normally when unsynchronized is false`() {
            val syncStream =
                MpegSynchronizedInputStream(ByteArrayInputStream(bytesNoSyncSignal), unsynchronized = false)

            val readBytes = ByteArray(bytesNoSyncSignal.size)
            syncStream.read(readBytes)

            assertArrayEquals(readBytes, bytesNoSyncSignal)
        }

        @Test
        fun `should read bytes normally when unsynchronized is false and source contains unsynchronization`() {
            val syncStream =
                MpegSynchronizedInputStream(ByteArrayInputStream(bytesUnsynchronization), unsynchronized = false)

            val readBytes = ByteArray(bytesUnsynchronization.size)
            syncStream.read(readBytes)

            assertArrayEquals(readBytes, bytesUnsynchronization)
        }
    }

    @Nested
    inner class Unsynchronized {
        @Test
        fun `should read bytes normally when unsynchronized is true and no unsynchronization is present`() {
            val syncStream =
                MpegSynchronizedInputStream(ByteArrayInputStream(bytesNoSyncSignal), unsynchronized = true)

            val readBytes = ByteArray(bytesNoSyncSignal.size)
            syncStream.read(readBytes)

            assertArrayEquals(readBytes, bytesNoSyncSignal)
        }

        @Test
        fun `should remove 0x00 when unsynchronized is true and source contains unsynchronization`() {
            val syncStream =
                MpegSynchronizedInputStream(ByteArrayInputStream(bytesUnsynchronization), unsynchronized = true)

            val readBytes = ByteArray(bytesUnsynchronization.size)
            val bytesRead = syncStream.read(readBytes)

            assertEquals(3, bytesRead)
            assertArrayEquals(readBytes.sliceArray(0..2), byteArrayOf(0x00, -1, 0x03))
        }
    }

    companion object {
        val bytesNoSyncSignal = byteArrayOf(0x00, 0x01, 0x02, 0x03)
        val bytesUnsynchronization = byteArrayOf(0x00, -1 /* = 0xFF signed*/, 0x00, 0x03)
    }
}