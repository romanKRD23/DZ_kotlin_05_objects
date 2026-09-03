data class PhotoAttachment (
    override val type: String = "photo",
    val photo: Photo
) : Attachment
data class VideoAttachment(
    override val type: String = "video",
    val video: Video
) : Attachment

data class AudioAttachment(
    override val type: String = "audio",
    val audio: Audio
) : Attachment

data class DocAttachment(
    override val type: String = "doc",
    val doc: Doc
) : Attachment
data class LinkAttachment(
    override val type: String = "link",
    val link: Link
) : Attachment