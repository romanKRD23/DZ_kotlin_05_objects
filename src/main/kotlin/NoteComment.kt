package ru.netology

data class NoteComment(
    override val id: Int = 0,
    val noteId: Int = 0,
    val text: String = "",
    override val isDeleted: Boolean = false
) : SoftDeletable