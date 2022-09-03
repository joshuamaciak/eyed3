package io.read.header

import io.read.Reader
import model.header.Id3v2Header
import java.io.InputStream

interface HeaderReader: Reader<Id3v2Header> {
}