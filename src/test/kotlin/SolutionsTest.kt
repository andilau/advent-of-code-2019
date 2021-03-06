
import days.Day1
import days.Day10
import days.Day11
import days.Day12
import days.Day13
import days.Day14
import days.Day15
import days.Day16
import days.Day17
import days.Day2
import days.Day2Computer
import days.Day3
import days.Day4
import days.Day4Faster
import days.Day5
import days.Day6
import days.Day7
import days.Day8
import days.Day9
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
        Day2(InputReader.getInputAsIntArray(2)) to Pair(6_627_023, 4019),
        Day2Computer(InputReader.getInputAsIntArray(2)) to Pair(6_627_023, 4019),
        Day3(InputReader.getInputAsList(3)) to Pair(1626, 27_330),
        Day4(InputReader.getInputAsString(4)) to Pair(945, 617),
        Day4Faster(InputReader.getInputAsString(4)) to Pair(945, 617),
        Day5(InputReader.getInputAsIntArray(5)) to Pair(9_938_601, 4_283_952),
        Day6(InputReader.getInputAsList(6)) to Pair(247_089, 442),
        Day7(InputReader.getInputAsIntArray(7)) to Pair(45_730, 5_406_484),
        Day8(InputReader.getInputAsString(8)) to Pair(1965, Unit),
        Day9(InputReader.getInputAsString(9)) to Pair(3_460_311_188L, 42202L),
        Day10(InputReader.getInputAsList(10)) to Pair(256, 1707),
        Day11(InputReader.getInputAsString(11)) to Pair(2373, Unit),
        Day12(InputReader.getInputAsList(12)) to Pair(14_907, 467_081_194_429_464),
        Day13(InputReader.getInputAsLongArray(13)) to Pair(372, 19_297),
        Day14(InputReader.getInputAsList(14)) to Pair(532_506L, 2_595_245L),
        Day15(InputReader.getInputAsLongArray(15)) to Pair(424, 446),
        Day16(InputReader.getInputAsString(16)) to Pair("70856418", "87766336"),
        Day17(InputReader.getInputAsLongArray(17)) to Pair(5788, 648_545),
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
