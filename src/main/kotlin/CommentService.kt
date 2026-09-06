package ru.netology

class CommentService(
    private val noteService: NoteService
) : CrudService<NoteComment>() {

    override fun copyWithId(item: NoteComment, id: Int) = item.copy(id = id)
    override fun copyWithDeleted(item: NoteComment, deleted: Boolean) =
        item.copy(isDeleted = deleted)

    // Проверка, что родительская заметка существует
    override fun add(item: NoteComment): NoteComment {
        noteService.getById(item.noteId) // NotFoundException, если заметки нет
        return super.add(item)
    }

    // Проверка, что родительская заметка существует (при обновлении)
    override fun update(item: NoteComment): Boolean {
        noteService.getById(item.noteId)
        return super.update(item)
    }

    // Удаление всех комментариев заметки (мягкое)
    fun deleteByNoteId(noteId: Int) {
        items.filter { it.noteId == noteId && !it.isDeleted }.forEach { comment ->
            val index = items.indexOf(comment)
            items[index] = copyWithDeleted(comment, true)
        }
    }
}
