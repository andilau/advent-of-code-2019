package days

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("Day 1")
class Day1Test {
    val ints = listOf(1, 2, 3)

    @Nested
    @DisplayName("Part 1")
    inner class Part1 {
        @Test
        fun shouldReturnSumOfEmptyList() {
            assertThat(Day1(emptyList()).partOne()).isEqualTo(0)
        }

        @Test
        fun shouldReturnSumOfSimpleList() {
            assertThat(Day1(ints).partOne()).isEqualTo(6)
        }
    }

    @Nested
    @DisplayName("Part 2")
    inner class Part2 {
        @Test
        fun shouldReturnProductOfEmptyList() {
            assertThat(Day1(emptyList()).partTwo()).isEqualTo(1)
        }

        @Test
        fun shouldReturnProductOfSimpleList() {
            assertThat(Day1(ints).partTwo()).isEqualTo(6)
        }
    }
}