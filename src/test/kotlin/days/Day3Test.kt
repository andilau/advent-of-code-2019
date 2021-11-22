package days

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.*

@DisplayName("Day 3")
class Day3Test {
    val examples = listOf(
        listOf("R8,U5,L5,D3", "U7,R6,D4,L4")
                to Pair(6, 30),
        listOf("R75,D30,R83,U83,L12,D49,R71,U7,L72", "U62,R66,U55,R34,D71,R55,D58,R83")
                to Pair(159, 610),
        listOf("R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51", "U98,R91,D20,R16,D67,R40,U7,R15,U6,R7")
                to Pair(135, 410),
    )

    @Nested
    @DisplayName("Part 1")
    inner class Part1 {
        @Test
        @DisplayName("Test Empty")
        fun testEmpty() {
            assertThatThrownBy { Day3(listOf("", "")).partOne() }
                .isInstanceOf(IllegalStateException::class.java)
                .hasMessageContaining("Wires don't intersect")
        }

        @TestFactory
        fun testExamples(): List<DynamicTest> =
            examples.mapIndexed { i, (input, value) ->
                DynamicTest.dynamicTest("""Example ${i + 1} should produce ${value.first}""") {
                    assertThat(Day3(input).partOne()).isEqualTo(value.first)
                }
            }
    }

    @Nested
    @DisplayName("Part 2")
    inner class Part2 {
        @TestFactory
        fun testExamples(): List<DynamicTest> =
            examples.mapIndexed { i, (input, value) ->
                DynamicTest.dynamicTest("""Example ${i + 1} should produce ${value.second}""") {
                    assertThat(Day3(input).partTwo()).isEqualTo(value.second)
                }
            }
    }
}