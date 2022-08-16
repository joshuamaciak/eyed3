package io.read

import io.read.header.Id3v2HeaderReader
import model.frame.Id3v23Frame
import model.header.Id3v2Header
import model.header.extended.Id3V23ExtendedHeader
import model.tag.Tag
import java.io.InputStream
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets

class Id3v23TagReader : Id3v2TagReader() {
    override fun read(stream: InputStream): Tag {
        val header = headerReader.readHeader(stream)
        val extendedHeader = if (header.flags.extendedHeader) {
            readExtendedHeader(stream)
        } else null

        //

        // for f of frames
        //   read f
        TODO()
    }

    fun readExtendedHeader(stream: InputStream): Id3V23ExtendedHeader {
        TODO("implement readExtendedHeader()")
    }

    fun readFrames(stream: InputStream): List<Id3v23Frame> {
        val frames = emptyList<Id3v23Frame>()
        // while !stream.end
        //   f = readFrame()
        //   frames.add(f)
        //
        return frames
    }
}