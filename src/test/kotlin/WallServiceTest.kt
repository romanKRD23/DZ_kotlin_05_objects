import org.junit.Assert.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import ru.netology.WallService

class WallServiceTest {
    @Before
    fun clearBeforeTest() {
        WallService.clear()
    }

    @Test
    fun add() {

        val first = WallService.add(Post(0, original = null, content = "Первый"))
        val second = WallService.add(Post(0, original = null, content = "Второй"))

        assertEquals(1, first.id)
        assertEquals(2, second.id)
    }
        @Test
        fun `update changes text and keeps id, returns true`() {
            val original = WallService.add(Post(id = 0, original = null, content = "Старый текст"))
            val updatedData = original.copy(content = "Новый текст")

            val result = WallService.update(updatedData)

            assertTrue("update должен вернуть true", result)
        }

        @Test
        fun `update returns false if post with given id does not exist`() {
            val existing = WallService.add(Post(id = 0, original = null, content = "Есть"))

            val nonExisting = Post(id = 999, original = null, content = "Не существует")
            val result = WallService.update(nonExisting)

            assertFalse("update должен вернуть false, если пост не найден", result)

        }
    @Test
    fun `createComment adds comment when post exists`() {
        val postId=3
        val comment = Comment(101, 1, 255, "Отличный пост!!!")

        val result = WallService.createComment(3, comment)
        assertEquals(1, WallService.comments.size)   // в массиве комментариев теперь 1 элемент
    }

    @Test(expected = PostNotFoundException::class)
    fun `createComment throws PostNotFoundException when post does not exist`() {
        // Arrange
        val nonExistingId = 999   // такого поста точно нет
        val comment = Comment(id = 101, fromId = 3, date = 256, text = "Комментарий к несуществующему посту")

        WallService.createComment(nonExistingId, comment)
    }
}