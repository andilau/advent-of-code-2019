package days

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*
import java.io.ByteArrayOutputStream
import java.io.PrintStream


@DisplayName("Day 7")
class Day8Test {
    private val outContent = ByteArrayOutputStream()
    private val errContent = ByteArrayOutputStream()

    init {
        System.setOut(PrintStream(outContent))
        System.setErr(PrintStream(errContent))
    }

    @Nested
    @DisplayName("Part 1")
    inner class Part1 {
        init {
            Day8.TALL = 2
            Day8.WIDE = 3
        }

        @TestFactory
        internal fun examples(): List<DynamicTest> = listOf(
            "000000" + "000000" to 0,
            "000111" + "111222" to 3 * 3,
            "111222" + "000111" to 3 * 3,
            "000111" + "111111" to 6 * 0,
            "000111" + "222222" to 6 * 0,
            "000111" + "111112" to 5 * 1,
            "000111" + "222221" to 5 * 1,
        )
            .map { (input, expected) ->
                DynamicTest.dynamicTest("""Image "$input" should count $expected pixels""") {
                    val actual = Day8(input).partOne()
                    assertThat(actual).isEqualTo(expected)
                }
            }
    }

    @Nested
    @DisplayName("Part 2")
    inner class Part2 {
        init {
            Day8.TALL = 2
            Day8.WIDE = 2
        }

        @Test
        @DisplayName("Should Return Unit Type")
        internal fun shouldReturnUnit() {
            val expected = Day8("0222112222120000").partTwo()
            assertThat(expected).isInstanceOf(Unit.javaClass)
        }

        @Test
        @DisplayName("Should Print To Stdout")
        internal fun shouldPrintToStdout() {
            Day8("0222" + "1122" + "2212" + "0000").partTwo()
            assertThat(outContent.toString())
                .isNotBlank()
                .isEqualToIgnoringNewLines("0110")
                .isEqualTo("01\n10\n")
        }
    }

    internal companion object {
        private val originalOut = System.out
        private val originalErr = System.err

        @AfterAll
        fun restore() {
            System.setOut(originalOut)
            System.setErr(originalErr)
        }
    }
}
