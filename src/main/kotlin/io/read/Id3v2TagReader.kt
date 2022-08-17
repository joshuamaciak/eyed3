package io.read

import io.read.header.HeaderReader
import io.read.header.Id3v2HeaderReader
import model.header.Id3v2Header
import model.header.Id3v2HeaderFlags
import model.header.Id3v2Version
import java.io.InputStream
import java.nio.charset.StandardCharsets

abstract class Id3v2TagReader : TagReader(headerReader = Id3v2HeaderReader()) {

}