data class Video(
    val id: Int,
    val ownerId: Int,
    val title: String,
    val description: String,
    val duration: Int
)
data class Audio(
    val id: Int,
    val ownerId: Int,
    val artist: String,
    val title: String,
    val duration: Int
)
data class Photo(
    val id: Int,
    val ownerId: Int,
    val text: String,
    val date: Int,
    val sizes: Array<String> = emptyArray()
)
data class Doc(
    val id: Int,
    val ownerId: Int,
    val title: String,
    val size: Int,
    val ext: String
)
data class Link(
    val url: String,
    val title: String,
    val caption: String,
    val description: String
)

