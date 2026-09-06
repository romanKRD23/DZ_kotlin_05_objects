package ru.netology
class PostNotFoundException(message: String) : Exception(message)
class NotFoundException(message: String) : RuntimeException(message)
class AlreadyDeletedException(message: String) : RuntimeException(message)
class AlreadyRestoredException(message: String) : RuntimeException(message)