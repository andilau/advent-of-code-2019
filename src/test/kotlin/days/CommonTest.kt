package days

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.util.Collections

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
    internal fun `remove All Sublist`() {
        val list = listOf(1, 2, 3, 4, 1, 2, 7, 8, 9)

        assertThat(list.subtractAllSubLists(listOf(1, 2))).containsSequence(3, 4, 7, 8, 9)
        assertThat(list.subtractAllSubLists(listOf(2, 1))).containsSequence(list)
        assertThat(list.subtractAllSubLists(emptyList())).containsSequence(list)
    }

    @Test
    fun `find contained simple null`() {
        val list = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
        val nullTriple = list.findThreeContainedListsOrNull()
        assertThat(nullTriple).isNull()
    }

    @Test
    fun `find contained simple not null`() {
        val list = listOf(1, 2, 1, 2, 3, 4, 5, 6, 7, 8, 9, 8, 9)
        val nullTriple = list.findThreeContainedListsOrNull()
        assertThat(nullTriple).isNotNull
    }

    @Test
    fun `find contained`() {
        val list = listOf(1, 2, 1, 2, 3, 4, 3, 4, 3, 4, 8, 9, 8, 9)
        val (ints1, ints2, ints3) = list.findThreeContainedListsOrNull() ?: error("PUh")
        assertThat(ints1).isEqualTo(listOf(1, 2))
        assertThat(ints2).isEqualTo(listOf(3, 4))
        assertThat(ints3).isEqualTo(listOf(8, 9))
    }
}
