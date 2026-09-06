package ru.netology

class Notes {
    val noteService = NoteService()
    val commentService = CommentService(noteService)

}