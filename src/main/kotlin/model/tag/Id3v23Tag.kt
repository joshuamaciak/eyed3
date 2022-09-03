package model.tag

import model.frame.Id3v2Frame
import model.header.Id3v2Header
import model.header.extended.Id3v2ExtendedHeader

class Id3v2Tag(val header: Id3v2Header, val extendedHeader: Id3v2ExtendedHeader?, val frames: List<Id3v2Frame>): Tag()