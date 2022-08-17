package model.header

class Id3v2Header(val identifier: String, val version: Id3v2Version, val flags: Id3v2HeaderFlags, val size: Int)

sealed class Id3v2Version {
    object Version2_3 : Id3v2Version()
    object Version2_2 : Id3v2Version()
}

class Id3v2HeaderFlags(val unsynchronization: Boolean, val extendedHeader: Boolean, val experimental: Boolean)
