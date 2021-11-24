package days

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.TestFactory

@DisplayName("Day 5")
class Day5Test {
    @Nested
    @DisplayName("Part 1")
    inner class Part1 {
        @TestFactory
        fun testSmallPrograms() = listOf(
            "1,0,0,0,99" to "2,0,0,0,99",
            "2,3,0,3,99" to "2,3,0,6,99",
            "2,4,4,5,99,0" to "2,4,4,5,99,9801",
            "1,1,1,4,99,5,6,0,99" to "30,1,1,4,2,5,6,0,99",
            "1,9,10,3,2,3,11,0,99,30,40,50" to "3500,9,10,70,2,3,11,0,99,30,40,50",
        ).map { (before, after) ->
            DynamicTest.dynamicTest("""Simple Program "$before"" should produce "$after"""") {
                val program = before.toIntArray()
                assertThat(IntCodeComputer(program).run().memoryAsString).isEqualTo(after)
            }
        }

        /*
        3,9,8,9,10,9,4,9,99,-1,8 - Using position mode, consider whether the input is equal to 8; output 1 (if it is) or 0 (if it is not).
        3,9,7,9,10,9,4,9,99,-1,8 - Using position mode, consider whether the input is less than 8; output 1 (if it is) or 0 (if it is not).
        3,3,1108,-1,8,3,4,3,99 - Using immediate mode, consider whether the input is equal to 8; output 1 (if it is) or 0 (if it is not).
        3,3,1107,-1,8,3,4,3,99 - Using immediate mode, consider whether the input is less than 8; output 1 (if it is) or 0 (if it is not).
         */
        @TestFactory
        fun testInstructions() = listOf(
            "3,9,8,9,10,9,4,9,99,-1,8" to 1,
            "3,9,7,9,10,9,4,9,99,-1,8" to 0,
            "3,3,1108,-1,8,3,4,3,99" to 1,
            "3,3,1107,-1,8,3,4,3,99" to 0,
        ).map { (program, returns) ->
            DynamicTest.dynamicTest("""Simple Program "$program" should return with "$returns"""") {
                val output = IntCodeComputer(program.toIntArray()).apply { input = 8 }.run().output.first()
                assertThat(output).isEqualTo(returns)
            }
        }
    }

    private fun String.toIntArray() = split(",").map(String::toInt).toIntArray()
}