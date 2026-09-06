package ru.netology

import ru.netology.Notes
import Note
import org.junit.Assert.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class NoteServiceTest {
    private lateinit var notes: Notes

    @Before
    fun setUp() {
        notes = Notes()
    }

    @Test
    fun add_shouldAssignId() {
        val note = notes.noteService.add(Note(title = "Заметка", text = "Текст"))
        assertEquals(1, note.id)
    }

    @Test
    fun getById_shouldReturnNote() {
        val added = notes.noteService.add(Note(title = "Заметка", text = "Текст"))
        val found = notes.noteService.getById(added.id)
        assertEquals(added, found)
    }

    @Test(expected = NotFoundException::class)
    fun getById_shouldThrowIfNotFound() {
        notes.noteService.getById(999)
    }

    @Test
    fun update_shouldChangeText() {
        val added = notes.noteService.add(Note(title = "Заметка", text = "Старый"))
        notes.noteService.update(added.copy(text = "Новый"))
        assertEquals("Новый", notes.noteService.getById(added.id).text)
    }

    @Test(expected = NotFoundException::class)
    fun update_shouldThrowIfNotFound() {
        notes.noteService.update(Note(id = 999, title = "x", text = "y"))
    }

    @Test
    fun delete_shouldSoftDelete() {
        val added = notes.noteService.add(Note(title = "Заметка", text = "Текст"))
        val result = notes.noteService.delete(added.id)
        assertTrue(result)
    }

    @Test(expected = NotFoundException::class)
    fun getById_shouldThrowAfterDelete() {
        val added = notes.noteService.add(Note(title = "Заметка", text = "Текст"))
        notes.noteService.delete(added.id)
        notes.noteService.getById(added.id) // удалённая заметка не возвращается
    }

    @Test(expected = AlreadyDeletedException::class)
    fun delete_shouldThrowIfAlreadyDeleted() {
        val added = notes.noteService.add(Note(title = "Заметка", text = "Текст"))
        notes.noteService.delete(added.id)
        notes.noteService.delete(added.id) // повторное удаление
    }

    @Test(expected = AlreadyDeletedException::class)
    fun update_shouldThrowIfNoteDeleted() {
        val added = notes.noteService.add(Note(title = "Заметка", text = "Текст"))
        notes.noteService.delete(added.id)
        notes.noteService.update(added.copy(text = "Новый"))
    }

    @Test
    fun restore_shouldRestoreDeletedNote() {
        val added = notes.noteService.add(Note(title = "Заметка", text = "Текст"))
        notes.noteService.delete(added.id)
        val result = notes.noteService.restore(added.id)
        assertTrue(result)
        // После восстановления заметка снова доступна
        assertEquals("Текст", notes.noteService.getById(added.id).text)
    }

    @Test(expected = AlreadyRestoredException::class)
    fun restore_shouldThrowIfNotDeleted() {
        val added = notes.noteService.add(Note(title = "Заметка", text = "Текст"))
        notes.noteService.restore(added.id) // заметка не была удалена
    }
}

