package days

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.SoftAssertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("Day 4")
class Day4Test {

    @Nested
    @DisplayName("Part 1")
    inner class Part1 {
        @Test
        @DisplayName("Same Two Adjacent Digits")
        fun sameTwoAdjacentDigits() {
            assertThat(Day4("111111-111111").partOne()).isEqualTo(1)
            assertThat(Day4("122345-122345").partOne()).isEqualTo(1)
            assertThat(Day4("123789-123789").partOne()).isEqualTo(0)
            assertThat(Day4("135679-135679").partOne()).isEqualTo(0)
        }

        @Test
        @DisplayName("Digits Never Decrease")
        fun hasDigitsNeverDecrease() {
            assertThat(Day4("111111-111111").partOne()).isEqualTo(1)
            assertThat(Day4("111123-111123").partOne()).isEqualTo(1)
            assertThat(Day4("123444-123444").partOne()).isEqualTo(1)
            assertThat(Day4("223450-223450").partOne()).isEqualTo(0)
        }

        @Test
        @DisplayName("Combined")
        fun testEmpty() {
            assertThat(Day4("111111-111111").partOne()).isEqualTo(1)
        }
    }

    @Nested
    @DisplayName("Part 2")
    inner class Part2 {
        var ok = listOf("122345", "112233", "111122")
        var nook = listOf("111111", "123444", "123789", "135679")

        @Test
        @DisplayName("Exactly Two Adjacent Digits")
        fun sameTwoAdjacentDigits() {
            with(SoftAssertions()) {
                ok.forEach {
                    assertThat(Day4("$it-$it").partTwo()).isEqualTo(1)
                }
                nook.forEach {
                    assertThat(Day4("$it-$it").partTwo()).isEqualTo(0)
                }
                assertAll()
            }
        }
    }
}