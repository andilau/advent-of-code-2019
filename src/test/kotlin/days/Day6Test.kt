package days

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("Day 6")
class Day6Test {
    @Nested
    @DisplayName("Part 1")
    inner class Part1 {
        @Test
        @DisplayName("Example")
        fun example() {
            // Arrange
            val input = """
                COM)B
                B)C
                C)D
                D)E
                E)F
                B)G
                G)H
                D)I
                E)J
                J)K
                K)L
                """.trimIndent()

            // Act
            val actual = Day6(input.lines()).partOne()

            // Assert
            assertThat(actual).isEqualTo(42)
        }
    }

    @Nested
    @DisplayName("Part 2")
    inner class Part2 {
        @Test
        @DisplayName("Example")
        fun example() {
            // Arrange
            val input = """
                COM)B
                B)C
                C)D
                D)E
                E)F
                B)G
                G)H
                D)I
                E)J
                J)K
                K)L
                K)YOU
                I)SAN""".trimIndent()

            // Act
            val actual = Day6(input.lines()).partTwo()

            // Assert
            assertThat(actual).isEqualTo(4)
        }
    }
}