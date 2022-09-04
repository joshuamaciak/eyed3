package model.frame.header

class Id3v23FrameHeader(val id: String, val size: Int, val flags: Id3v2FrameFlags)

class Id3v2FrameFlags(
    val tagAlterPreservation: Boolean,
    val fileAlterPreservation: Boolean,
    val readonly: Boolean,
    val compression: Boolean,
    val encryption: Boolean,
    val groupingIdentity: Boolean
)
