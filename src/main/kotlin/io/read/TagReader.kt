package io.read

import io.read.frame.Id3v2FrameReader
import io.read.header.HeaderReader
import model.tag.Tag

abstract class TagReader(val headerReader: HeaderReader, val frameReader: Id3v2FrameReader) : Reader<Tag>