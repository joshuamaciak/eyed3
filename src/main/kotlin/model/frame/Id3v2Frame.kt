package model.frame

import TextEncoding
import model.frame.header.Id3v2FrameHeader

sealed class Id3v2Frame(val header: Id3v2FrameHeader) {
    sealed class TextInformationFrame(
        header: Id3v2FrameHeader,
        val textEncoding: TextEncoding,
        val information: String
    ) : Id3v2Frame(header) {
        // TODO: define all the text info frames
        class UserDefined(
            header: Id3v2FrameHeader,
            textEncoding: TextEncoding,
            val description: String,
            val value: String
        ) : TextInformationFrame(header, textEncoding, description + value)
    }

    sealed class UrlLinkFrame(header: Id3v2FrameHeader, val url: String) : Id3v2Frame(header) {
        class UserDefined(
            header: Id3v2FrameHeader,
            val textEncoding: TextEncoding,
            val description: String,
            url: String
        ) : UrlLinkFrame(header, url)
    }

    sealed class InvolvedPeopleList(
        header: Id3v2FrameHeader,
        val textEncoding: TextEncoding,
        val people: List<String>
    ) : Id3v2Frame(header)

    sealed class AttachedPicture(
        val textEncoding: TextEncoding,
        val mimeType: String,
        val pictureType: PictureType,
        val description: String,
        val pictureData: ByteArray
    )
}

sealed class PictureType(val id: Byte) {
    object Other : PictureType(0x00)
    object FileIcon32x32 : PictureType(0x01)
    object FileIconOther : PictureType(0x02)
    object CoverFront : PictureType(0x03)
    object CoverBack : PictureType(0x04)
    object LeafletPage : PictureType(0x05)
    object Media : PictureType(0x06)
    object LeadArtist : PictureType(0x07)
    object Artist : PictureType(0x08)
    object Conductor : PictureType(0x09)
    object Band : PictureType(0x0A)
    object Composer : PictureType(0x0B)
    object Lyricist : PictureType(0x0C)
    object RecordingLocation : PictureType(0x0D)
    object DuringRecording : PictureType(0x0E)
    object DuringPerformance : PictureType(0x0F)
    object MovieCapture : PictureType(0x10)
    object ABrightColoredFish : PictureType(0x11)
    object Illustration : PictureType(0x12)
    object BandLogotype : PictureType(0x13)
    object PublisherLogotype : PictureType(0x14)
}