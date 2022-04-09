package days

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.util.Collections

@DisplayName("Common")
class CommonTest {

    @Test
    fun test() {
        val list = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
        val pattern = listOf(1, 2)

        val expected = Collections.indexOfSubList(list, pattern)
        assertThat(expected).isEqualTo(0)
    }
}
