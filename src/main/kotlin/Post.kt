data class Post(
    val id: Int,
    val ownerId: Int = 100,
    val fromId: Int = 110,
    val createdBy: Int = 120,
    val date: Int = 1717300000,
    val text: String = "test",
    val replyOwnerId: Int = 0,
    val replyPostId: Int = 0,
    val friendsOnly: Boolean = false
)
