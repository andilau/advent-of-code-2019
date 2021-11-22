import days.Day1
import days.Day2
import days.Day3
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory
import util.InputReader


@DisplayName("All Solutions")
class SolutionsTest {
    @TestFactory
    fun testAdventOfCode() = listOf(
        Day1(InputReader.getInputAsListOfInt(1)) to Pair(3_380_731, 5_068_210),
        Day2(InputReader.getInputAsString(2)) to Pair(6_627_023, 4019),
        Day3(InputReader.getInputAsList(3)) to Pair(1626, 27330),
    )
        .map { (day, answers) ->
            DynamicTest.dynamicTest("${day.javaClass.simpleName} -> ${answers.first} / ${answers.second}") {
                with(day) {
                    assertThat(partOne()).isEqualTo(answers.first)
                    assertThat(partTwo()).isEqualTo(answers.second)
                }
            }
        }
}