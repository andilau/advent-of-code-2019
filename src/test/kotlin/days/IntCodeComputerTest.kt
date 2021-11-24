package days

import days.IntCodeComputer.ParameterMode.IMMEDIATE
import days.IntCodeComputer.ParameterMode.POSITION
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*

@DisplayName("IntCodeComputer")
class IntCodeComputerTest {

    @Test
    @DisplayName("Empty")
    fun empty() {
        val computer = IntCodeComputer(IntArray(0))
        assertThat(computer.memory).isEmpty()
    }

    @Test
    @DisplayName("Halt")
    fun halt() {
        val computer = IntCodeComputer("99".stringToIntArray())
        assertThat(computer.memory).containsExactly(99)
    }

    @Test
    @DisplayName("Input")
    fun input() {
        val computer = IntCodeComputer("99".stringToIntArray()).apply { input = 1 }
        assertThat(computer.input).isEqualTo(1)
        assertThat(computer.run()).isExactlyInstanceOf(IntCodeComputer::class.java)
    }

    @Nested
    @DisplayName("Transform Memory")
    inner class Part1 {
        @TestFactory
        fun testSimplePrograms() = listOf(
            "1,0,0,0,99" to "2,0,0,0,99",
            "2,3,0,3,99" to "2,3,0,6,99",
            "2,4,4,5,99,0" to "2,4,4,5,99,9801",
            "1,1,1,4,99,5,6,0,99" to "30,1,1,4,2,5,6,0,99",
            "1,9,10,3,2,3,11,0,99,30,40,50" to "3500,9,10,70,2,3,11,0,99,30,40,50",
        ).map { (input, output) ->
            DynamicTest.dynamicTest("""Simple Program "$input"" should produce $output""") {
                val memory = input.stringToIntArray()
                assertThat(IntCodeComputer(memory).run().memoryAsString).isEqualTo(output)
            }
        }

        @TestFactory
        fun testSmallProgramsWithImmediateMode() = listOf(
            "1002,4,3,4,33" to "1002,4,3,4,99",
        ).map { (input, output) ->
            DynamicTest.dynamicTest("""Simple Program "$input"" should produce $output""") {
                val memory = input.stringToIntArray()
                assertThat(IntCodeComputer(memory).run().memoryAsString).isEqualTo(output)
            }
        }

    }

    private fun String.stringToIntArray() = split(",").map(String::toInt).toIntArray()

    @Test
    @DisplayName("Test Modes")
    internal fun testModes() {
        val underTest = 1002
        underTest.opcode()
        assertThat(underTest.opcode()).isEqualTo(2)
        assertThat(underTest.parameter1Mode()).isEqualTo(POSITION)
        assertThat(underTest.parameter2Mode()).isEqualTo(IMMEDIATE)
        assertThat(underTest.parameter3Mode()).isEqualTo(POSITION)
    }

    @Test
    @DisplayName("Test Modes")
    internal fun testModes2() {
        val unserTest = 1002
        unserTest.opcode()
        assertThat(unserTest.opcode()).isEqualTo(2)
        assertThat(unserTest.parameter1Mode()).isExactlyInstanceOf(POSITION::class.java)
        assertThat(unserTest.parameter2Mode()).isExactlyInstanceOf(IMMEDIATE::class.java)
        assertThat(unserTest.parameter3Mode()).isExactlyInstanceOf(POSITION::class.java)
    }
}