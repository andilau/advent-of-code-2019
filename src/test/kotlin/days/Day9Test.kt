package days

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*

@DisplayName("Day 9")
class Day9Test {
    @Nested
    @DisplayName("Computer")
    inner class Computer {
        @Test
        fun `computer halts`() {
            val code = "99".toLongArray()
            val computer = CompleteIntCodeComputer(code).run()
            assertThat(computer.halted).isTrue
        }

        @TestFactory
        fun testSimplePrograms() = listOf(
            "1,0,0,0,99" to "2,0,0,0,99",
            "2,3,0,3,99" to "2,3,0,6,99",
            "2,4,4,5,99,0" to "2,4,4,5,99,9801",
            "1,1,1,4,99,5,6,0,99" to "30,1,1,4,2,5,6,0,99",
            "1,9,10,3,2,3,11,0,99,30,40,50" to "3500,9,10,70,2,3,11,0,99,30,40,50",
        ).map { (input, output) ->
            DynamicTest.dynamicTest("""Simple Program "$input"" should produce $output""") {
                val memory = input.toLongArray()
                assertThat(CompleteIntCodeComputer(memory).run().memoryAsString).isEqualTo(output)
            }
        }
    }

    @Nested
    @DisplayName("Examples")
    inner class Examples {
        @Test
        fun `program takes no input and produces a copy of itself as output`() {
            val intCode = "109,1,204,-1,1001,100,1,100,1008,100,16,101,1006,101,0,99".toLongArray()
            val computer = CompleteIntCodeComputer(intCode).run()

            intCode.forEach { code ->
                assertThat(computer.output).isEqualTo(code)
            }
        }

        @Test
        fun `1102,34915192,34915192,7,4,7,99,0 should output a 16-digit number`() {
            val intCode = "1102,34915192,34915192,7,4,7,99,0".toLongArray()
            val output = CompleteIntCodeComputer(intCode).run().output
            assertThat(output.toString().length).isEqualTo(16)
        }

        @Test
        fun `104,1125899906842624,99 should output the large number in the middle`() {
            val intCode = "104,1125899906842624,99".toLongArray()
            val output = CompleteIntCodeComputer(intCode).run().output
            assertThat(output).isEqualTo(1125899906842624)
        }
    }

    private fun String.toLongArray() = split(",", "\n").map(String::toLong).toLongArray()
}