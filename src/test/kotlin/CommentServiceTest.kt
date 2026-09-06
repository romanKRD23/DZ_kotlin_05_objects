package ru.netology

import Note
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class CommentServiceTest {
    private lateinit var notes: Notes

    @Before
    fun setUp() {
        notes = Notes()
    }

    @Test
    fun add_shouldCreateCommentForExistingNote() {
        val note = notes.noteService.add(Note(title = "Заметка", text = "Текст"))
        val comment = notes.commentService.add(NoteComment(noteId = note.id, text = "Комментарий"))
        assertEquals(1, comment.id)
    }

    @Test(expected = NotFoundException::class)
    fun add_shouldThrowIfNoteNotFound() {
        notes.commentService.add(NoteComment(noteId = 999, text = "Комментарий"))
    }

    @Test(expected = NotFoundException::class)
    fun add_shouldThrowIfNoteDeleted() {
        val note = notes.noteService.add(Note(title = "Заметка", text = "Текст"))
        notes.noteService.delete(note.id)
        notes.commentService.add(NoteComment(noteId = note.id, text = "Комментарий"))
    }

    @Test
    fun update_shouldChangeCommentText() {
        val note = notes.noteService.add(Note(title = "Заметка", text = "Текст"))
        val comment = notes.commentService.add(NoteComment(noteId = note.id, text = "Старый"))
        notes.commentService.update(comment.copy(text = "Новый"))
        assertEquals("Новый", notes.commentService.getById(comment.id).text)
    }

    @Test(expected = NotFoundException::class)
    fun update_shouldThrowIfCommentNotFound() {
        val note = notes.noteService.add(Note(title = "Заметка", text = "Текст"))
        notes.commentService.update(NoteComment(id = 999, noteId = note.id, text = "x"))
    }

    @Test
    fun delete_shouldSoftDeleteComment() {
        val note = notes.noteService.add(Note(title = "Заметка", text = "Текст"))
        val comment = notes.commentService.add(NoteComment(noteId = note.id, text = "Комментарий"))
        val result = notes.commentService.delete(comment.id)
        assertTrue(result)
    }

    @Test(expected = AlreadyDeletedException::class)
    fun delete_shouldThrowIfAlreadyDeleted() {
        val note = notes.noteService.add(Note(title = "Заметка", text = "Текст"))
        val comment = notes.commentService.add(NoteComment(noteId = note.id, text = "Комментарий"))
        notes.commentService.delete(comment.id)
        notes.commentService.delete(comment.id)
    }

    @Test
    fun restore_shouldRestoreDeletedComment() {
        val note = notes.noteService.add(Note(title = "Заметка", text = "Текст"))
        val comment = notes.commentService.add(NoteComment(noteId = note.id, text = "Комментарий"))
        notes.commentService.delete(comment.id)
        val result = notes.commentService.restore(comment.id)
        assertTrue(result)
        assertEquals("Комментарий", notes.commentService.getById(comment.id).text)
    }

    @Test(expected = AlreadyRestoredException::class)
    fun restore_shouldThrowIfNotDeleted() {
        val note = notes.noteService.add(Note(title = "Заметка", text = "Текст"))
        val comment = notes.commentService.add(NoteComment(noteId = note.id, text = "Комментарий"))
        notes.commentService.restore(comment.id)
    }

    // Каскадное удаление комментариев при удалении заметки
    @Test
    fun deleteNote_shouldCascadeDeleteComments() {
        val note = notes.noteService.add(Note(title = "Заметка", text = "Текст"))
        val c1 = notes.commentService.add(NoteComment(noteId = note.id, text = "Комментарий 1"))
        val c2 = notes.commentService.add(NoteComment(noteId = note.id, text = "Комментарий 2"))

        notes.noteService.delete(note.id)

        // Оба комментария должны быть мягко удалены
        assertTrue(notes.commentService.getAll().isEmpty())
        // Но при этом их можно восстановить
        notes.commentService.restore(c1.id)
        notes.commentService.restore(c2.id)
        assertEquals(2, notes.commentService.getAll().size)
    }
}