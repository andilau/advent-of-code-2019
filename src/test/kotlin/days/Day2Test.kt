package days

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.TestFactory

@DisplayName("Day 2")
class Day2Test {

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
        ).map { (code, value) ->
            DynamicTest.dynamicTest("""Simple Program "$code"" should produce $value""") {
                assertThat(Day2(code).testProgram().joinToString(",")).isEqualTo(value)
            }
        }
    }
}