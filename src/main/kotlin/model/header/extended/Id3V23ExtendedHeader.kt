package model.header.extended

class Id3V23ExtendedHeader(val size: UInt, val flags: Id3v2ExtendedHeaderFlags, val paddingSize: UInt, val crcData: UInt)

class Id3v2ExtendedHeaderFlags(val crcDataPresent: Boolean)
