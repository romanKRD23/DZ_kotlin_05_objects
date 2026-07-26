package ru.netology
import Likes
import Post

object WallService{
    private val posts = mutableListOf<Post>()
    private var nextId = 1

    fun add (post: Post): Post {
        val postWithId = post.copy (id = nextId)
        posts.add(postWithId)
        nextId++
        return postWithId
    }
    fun update (post: Post): Boolean{
    val index = posts.indexOfFirst {it.id == post.id}
    if (index == -1) return false
    posts[index] = post
        return true
    }
    fun getAll() = posts.toList()
}

fun main() {
    val newPost = WallService.add(
        Post(0, text = "Привет, это мой первый пост!")
    )

    println("Пост сохранен")
    println("Текст поста: ${newPost.text}")
    println("Его ID: ${newPost.id}")

    val secondPost = WallService.add(
        Post(2, text = "Это текст второго поста")
    )

    println("Пост #2 сохранен")
    println("Текст поста: ${secondPost.text}")
    println("Его ID: ${secondPost.id}")

    val updated = WallService.update(
        newPost.copy(text = "Новый обновлённый текст")
    )

    if (updated) {
        println("Пост успешно обновлён!")
        println("Все посты: ${WallService.getAll().joinToString { it.text }}")
    } else {
        println("Не удалось обновить: пост с таким ID не найден.")
    }
}