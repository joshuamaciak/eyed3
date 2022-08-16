package io.read

import io.read.header.HeaderReader
import io.read.header.Id3v2HeaderReader
import model.tag.Tag
import java.io.InputStream

abstract class TagReader(val headerReader: HeaderReader) {
    abstract fun read(stream: InputStream): Tag
}