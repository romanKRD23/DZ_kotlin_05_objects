import ru.netology.SoftDeletable

data class Note(
    override val id: Int = 0,
    val title: String = "",
    val text: String = "",
    val date: Int,
    val note_comments: Int,
    val read_comments: Int,
    val view_url: Int,
    override val isDeleted: Boolean = false
) : SoftDeletable