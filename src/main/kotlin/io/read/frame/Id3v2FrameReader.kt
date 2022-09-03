package io.read.frame

import io.read.Reader
import model.frame.Id3v2Frame
import java.io.InputStream

class Id3v2FrameReader : Reader<List<Id3v2Frame>> {
    override fun read(stream: InputStream): List<Id3v2Frame> {
        TODO("Not yet implemented")
    }
}