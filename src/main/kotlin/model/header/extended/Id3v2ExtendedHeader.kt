package model.header.extended

class Id3v2ExtendedHeader(
    val size: Int,
    val flags: Id3v2ExtendedHeaderFlags,
    val sizeOfPadding: Int,
    val crcData: ByteArray?
)

class Id3v2ExtendedHeaderFlags(val crcDataPresent: Boolean)
