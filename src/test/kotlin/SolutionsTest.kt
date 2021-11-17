import days.Day1
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory

@DisplayName("Advent of Code solutions")
class SolutionsTest {
    private val input = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9)

    @TestFactory
    fun testAdventOfCode() = listOf(
        Day1(input) to Pair(45, 362_880),
    )
        .map { (day, answers) ->
            DynamicTest.dynamicTest("${day.javaClass.simpleName} -> ${answers.first} / ${answers.second}") {
                assertThat(day.partOne()).isEqualTo(answers.first)
                assertThat(day.partTwo()).isEqualTo(answers.second)
            }
        }
}