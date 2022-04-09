package days

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.util.*

@DisplayName("Common")
class CommonTest {

    @Test
    fun `List should contain or not contain subList`() {
        val list = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9)

        assertThat(Collections.indexOfSubList(list, listOf(1, 2))).isEqualTo(0)
        assertThat(Collections.indexOfSubList(list, listOf(2, 3))).isEqualTo(1)
        assertThat(Collections.indexOfSubList(list, listOf(2, 3, 4))).isEqualTo(1)
        assertThat(Collections.indexOfSubList(list, listOf(2, 3, 4, 5))).isEqualTo(1)
        assertThat(Collections.indexOfSubList(list, emptyList<Int>())).isEqualTo(0)
        assertThat(Collections.indexOfSubList(emptyList<Int>(), emptyList<Int>())).isEqualTo(0)

        assertThat(Collections.indexOfSubList(list, listOf(2, 1))).isEqualTo(-1)
        assertThat(Collections.indexOfSubList(list, listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 9))).isEqualTo(-1)
        assertThat(Collections.indexOfSubList(emptyList<Int>(), listOf(1))).isEqualTo(-1)
    }

    @Test
    internal fun `remove All Sublists`() {
        val list = listOf(1, 2, 3, 4, 1, 2, 7, 8, 9)

        assertThat(list.subtractAll(listOf(1, 2))).containsSequence(3, 4, 7, 8, 9)
        assertThat(list.subtractAll(listOf(2, 1))).containsSequence(list)
        assertThat(list.subtractAll(emptyList())).containsSequence(list)
    }
}
