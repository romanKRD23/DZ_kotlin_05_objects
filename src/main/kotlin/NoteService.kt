package ru.netology

import Note

class NoteService(
    private val commentService: CommentService? = null
) : CrudService<Note>() {

    override fun copyWithId(item: Note, id: Int) = item.copy(id = id)
    override fun copyWithDeleted(item: Note, deleted: Boolean) = item.copy(isDeleted = deleted)

    override fun delete(id: Int): Boolean {
        val result = super.delete(id)

        // Мягкое удаление комментариев при удалении заметки
        commentService?.deleteByNoteId(id)
        return result
    }
}