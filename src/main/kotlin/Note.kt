import ru.netology.SoftDeletable

data class Note(
    override val id: Int = 0,
    val title: String = "",
    val text: String = "",
    val date: Int = 123,
    val note_comments: Int = 11,
    val read_comments: Int= 11,
    val view_url: String = "",
    override val isDeleted: Boolean = false
) : SoftDeletable
