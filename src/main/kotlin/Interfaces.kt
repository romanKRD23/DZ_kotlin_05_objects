package ru.netology

interface HasId {
    val id: Int
}

interface SoftDeletable : HasId {
    val isDeleted: Boolean
}