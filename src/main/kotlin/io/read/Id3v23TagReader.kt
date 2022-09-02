package io.read

import io.read.header.extended.Id3v2ExtendedHeaderReader
import model.frame.Id3v23Frame
import model.header.extended.Id3v2ExtendedHeader
import model.tag.Tag
import java.io.InputStream

class Id3v23TagReader : Id3v2TagReader(extendedHeaderReader = Id3v2ExtendedHeaderReader()) {
    override fun read(stream: InputStream): Tag {
        val header = headerReader.readHeader(stream)
        val extendedHeader = if (header.flags.extendedHeader) {
            extendedHeaderReader.read(stream, header.flags.unsynchronization)
        } else null

        // Read first frame?
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