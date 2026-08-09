import org.junit.Assert.*
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

        val first = WallService.add(Post(0, content = "Первый"))
        val second = WallService.add(Post(0, content = "Второй"))

        assertEquals(1, first.id)
        assertEquals(2, second.id)
        @Test
        fun `update changes text and keeps id, returns true`() {
            val original = WallService.add(Post(id = 0, content = "Старый текст"))
            val updatedData = original.copy(content = "Новый текст")

            val result = WallService.update(updatedData)

            assertTrue("update должен вернуть true", result)
        }

        @Test
        fun `update returns false if post with given id does not exist`() {
            val existing = WallService.add(Post(id = 0, content = "Есть"))

            val nonExisting = Post(id = 999, content = "Не существует")
            val result = WallService.update(nonExisting)

            assertFalse("update должен вернуть false, если пост не найден", result)


        }

    }
}