import days.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory
import util.InputReader


@DisplayName("All Solutions")
class SolutionsTest {
    @TestFactory()
    fun testAdventOfCode() = listOf(
        Day1(InputReader.getInputAsListOfInt(1)) to Pair(3_380_731, 5_068_210),
        Day2(InputReader.getInputAsIntArray(2)) to Pair(6_627_023, 4019),
        Day2Computer(InputReader.getInputAsIntArray(2)) to Pair(6_627_023, 4019),
        Day3(InputReader.getInputAsList(3)) to Pair(1626, 27330),
        Day4(InputReader.getInputAsString(4)) to Pair(945, 617),
        Day4Faster(InputReader.getInputAsString(4)) to Pair(945, 617),
        Day5(InputReader.getInputAsIntArray(5)) to Pair(9_938_601, 4_283_952),
        Day6(InputReader.getInputAsList(6)) to Pair(247_089, 442),
        Day7(InputReader.getInputAsIntArray(7)) to Pair(45_730, 5_406_484),
        Day8(InputReader.getInputAsString(8)) to Pair(1965, Unit),
        Day9(InputReader.getInputAsString(9)) to Pair(3460311188L, 42202L),
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