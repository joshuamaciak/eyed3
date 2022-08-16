package io.read.header

import model.header.Id3v2Header
import java.io.InputStream

interface HeaderReader {
    fun readHeader(stream: InputStream): Id3v2Header
}