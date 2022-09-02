package io.read

import io.read.header.Id3v2HeaderReader
import io.read.header.extended.Id3v2ExtendedHeaderReader

abstract class Id3v2TagReader(val extendedHeaderReader: Id3v2ExtendedHeaderReader) : TagReader(headerReader = Id3v2HeaderReader())