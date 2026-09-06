package ru.netology

import Comment
import Post
//import PostNotFoundException

object WallService {
    private var posts = emptyArray<Post>()
    public var comments = emptyArray<Comment>()
    private var nextId = 0

    fun add(post: Post): Post {
        val postWithId = post.copy(id = ++nextId)
        posts += postWithId
        return postWithId
    }

    fun update(post: Post): Boolean {
        val index = posts.indexOfFirst { it.id == post.id }
        if (index == -1) return false
        posts[index] = post.copy()
        return true
    }

    fun clear() {
        posts = emptyArray<Post>()
        comments = emptyArray<Comment>()
        nextId = 0
    }
    fun createComment (postId: Int, comment: Comment):Comment {
        val postExists = posts.any { it.id == postId }
        if (!postExists) {
            throw PostNotFoundException ("Пост с id $postId не существует")
        }
        comments = comments.plus(comment)
        return comment
    }
}

fun main() {
    val newPost = WallService.add(
        Post(0, content = "Привет, это мой первый пост!", original = null)
    )
    println("Пост сохранен")
    println("Текст поста: ${newPost.content}")
    println("Его ID: ${newPost.id}")

    val secondPost = WallService.add(
        Post(0, content = "Это текст второго поста", original = null)
    )

    println("Пост #2 сохранен")
    println("Текст поста: ${secondPost.content}")
    println("Его ID: ${secondPost.id}")

    val secondPostLast = WallService.add(
        Post(0, content = "Это текст следующего поста", original = null)
    )
    println("Пост #3 сохранен")
    println("Текст поста: ${secondPostLast.content}")
    println("Его ID: ${secondPostLast.id}")

    val updated = WallService.update(
        newPost.copy(content = "Новый обновлённый текст")
    )

    if (updated) {
        println("Пост успешно обновлён")
    } else {
        println("Не удалось обновить пост (возможно, ID не найден)")
    }
    val newComment= WallService.createComment(
        3, comment = Comment(101, 1, 255, "Отличный пост!!!")
    )
    println(newComment)
}
