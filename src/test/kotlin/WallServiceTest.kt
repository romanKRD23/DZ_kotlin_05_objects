import org.junit.Assert.*
import org.junit.Test
import ru.netology.WallService

class WallServiceTest {

    @Test
    fun `add assigns id = 1 to the first post`() {
        val post = Post(id = 0, text = "Первый пост")
        val added = WallService.add(post)

        assertEquals(1, added.id)
        assertEquals("Первый пост", added.text)
        // Проверяем, что оригинальный пост не изменился
        assertEquals(0, post.id)
    }

    @Test
    fun `add generates strictly increasing unique ids`() {
        val p1 = WallService.add(Post(id = 0, text = "Пост 1"))
        val p2 = WallService.add(Post(id = 0, text = "Пост 2"))
        val p3 = WallService.add(Post(id = 0, text = "Пост 3"))

        assertEquals(1, p1.id)
        assertEquals(2, p2.id)
        assertEquals(3, p3.id)
    }

    @Test
    fun `update changes text and keeps id, returns true`() {
        val original = WallService.add(Post(id = 0, text = "Старый текст"))
        val updatedData = original.copy(text = "Новый текст")

        val result = WallService.update(updatedData)

        assertTrue("update должен вернуть true", result)

        val all = WallService.getAll()
        val found = all.find { it.id == original.id }
        assertNotNull("Пост должен остаться в списке", found)
        assertEquals("Новый текст", found!!.text)
        assertEquals(original.id, found.id) // ID не должен измениться
    }

    @Test
    fun `update returns false if post with given id does not exist`() {
        val existing = WallService.add(Post(id = 0, text = "Есть"))

        val nonExisting = Post(id = 999, text = "Не существует")
        val result = WallService.update(nonExisting)

        assertFalse("update должен вернуть false, если пост не найден", result)

        val all = WallService.getAll()
        assertEquals(1, all.size)
        assertEquals(existing.text, all.first().text)
    }

}