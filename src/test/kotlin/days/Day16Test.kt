package days

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.TestFactory

@DisplayName("Day 16")
class Day16Test {

    @Nested
    @DisplayName("Part 1")
    inner class Part1 {

        @TestFactory
        fun `test simple signal phase`(): List<DynamicTest> =
            listOf("12345678", "48226158", "34040438", "03415518", "01029498")
                .mapIndexed { index, expected ->
                    DynamicTest.dynamicTest("$index -> $expected") {
                        val actual = Day16("12345678").processSignal(index )
                        assertThat(actual).isEqualTo(expected)
                    }
                }

        @TestFactory
        fun `first eight digits of the final output list after 100 phases`(): List<DynamicTest> =
            listOf(
                "80871224585914546619083218645595" to "24176176",
                "19617804207202209144916044189917" to "73745418",
                "69317163492948606335995924319873" to "52432133",
            ).map { (input, output) ->
                DynamicTest.dynamicTest("$input -> $output") {
                    assertThat(Day16(input).partOne()).isEqualTo(output)
                }
            }
    }

    @Nested
    @DisplayName("Part 2")
    inner class Part2 {
        @TestFactory
        fun `first eight digits of the final output list after 100 phases`(): List<DynamicTest> =
            listOf(
                "03036732577212944063491565474664" to "84462026",
                "02935109699940807407585447034323" to "78725270",
                "03081770884921959731165446850517" to "53553731",
            ).map { (input, output) ->
                DynamicTest.dynamicTest("$input -> $output") {
                    assertThat(Day16(input).partTwo()).isEqualTo(output)
                }
            }


    }
}