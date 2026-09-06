package ru.netology

abstract class CrudService<T : SoftDeletable> {
    protected val items = mutableListOf<T>()
    protected var nextId = 0

    protected abstract fun copyWithId(item: T, id: Int): T
    protected abstract fun copyWithDeleted(item: T, deleted: Boolean): T

    // ДОБАВЛЕНИЕ
    open fun add(item: T): T {
        val newItem = copyWithId(item, nextId++)
        items.add(newItem)
        return newItem
    }

    // ЧТЕНИЕ
    fun getById(id: Int): T {
        return items.find { it.id == id && !it.isDeleted }
            ?: throw NotFoundException("Элемент с id $id не найден")
    }

    fun getAll(): List<T> = items.filter { !it.isDeleted }

    // ОБНОВЛЕНИЕ
    open fun update(item: T): Boolean {
        val existing = items.find { it.id == item.id }
            ?: throw NotFoundException("Элемент с id ${item.id} не найден")

        if (existing.isDeleted) {
            throw AlreadyDeletedException("Нельзя редактировать удалённый элемент (id ${item.id})")
        }

        val index = items.indexOf(existing)
        items[index] = item
        return true
    }

    // УДАЛЕНИЕ (мягкое)
    open fun delete(id: Int): Boolean {
        val existing = items.find { it.id == id }
            ?: throw NotFoundException("Элемент с id $id не найден")

        if (existing.isDeleted) {
            throw AlreadyDeletedException("Элемент с id $id уже удалён")
        }

        val index = items.indexOf(existing)
        items[index] = copyWithDeleted(existing, true)
        return true
    }

    // ВОССТАНОВЛЕНИЕ
    open fun restore(id: Int): Boolean {
        val existing = items.find { it.id == id }
            ?: throw NotFoundException("Элемент с id $id не найден")

        if (!existing.isDeleted) {
            throw AlreadyRestoredException("Элемент с id $id не был удалён")
        }

        val index = items.indexOf(existing)
        items[index] = copyWithDeleted(existing, false)
        return true
    }
}