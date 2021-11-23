package days

@AdventOfCodePuzzle(
    name = "Secure Container",
    url = "https://adventofcode.com/2019/day/4",
    date = Date(day = 4, year = 2019)
)
class Day4(line: String) : Puzzle {
    private val range = line
        .split("-")
        .let { it.first().toInt()..it.last().toInt() }

    override fun partOne() =
        range
            .map(Int::toString)
            .count { it.hasDigitsOrderNeverDecrease() && it.hasSameTwoAdjacentDigits() }


    override fun partTwo() =
        range
            .map(Int::toString)
            .count { it.hasDigitsOrderNeverDecrease() && it.hasExactlyTwoAdjacentDigits() }

    private fun String.hasDigitsOrderNeverDecrease() =
        zipWithNext().all { it.first <= it.second }

    private fun String.hasSameTwoAdjacentDigits() =
        zipWithNext().any { it.first == it.second }

    private fun String.hasExactlyTwoAdjacentDigits() =
        groupBy { it }.any { it.value.size == 2 }
}