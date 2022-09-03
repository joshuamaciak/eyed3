package io.read

import io.read.header.extended.Id3v2ExtendedHeaderReader
import model.tag.Id3v2Tag
import model.tag.Tag
import java.io.InputStream

class Id3v23TagReader : Id3v2TagReader(extendedHeaderReader = Id3v2ExtendedHeaderReader()) {
    override fun read(stream: InputStream): Tag {
        val header = headerReader.read(stream)
        val synchronizedStream = MpegSynchronizedInputStream(stream, header.flags.unsynchronization)
        val extendedHeader = if (header.flags.extendedHeader) {
            extendedHeaderReader.read(synchronizedStream)
        } else null
        val frames = frameReader.read(synchronizedStream)
        return Id3v2Tag(header = header, extendedHeader = extendedHeader, frames = frames)
    }
}
