package model.tag

import model.frame.Id3v23Frame
import model.header.Id3v2Header
import model.header.extended.Id3V23ExtendedHeader

class Id3v2Tag(val header: Id3v2Header, val extendedHeader: Id3V23ExtendedHeader?, val frames: List<Id3v23Frame>): Tag()