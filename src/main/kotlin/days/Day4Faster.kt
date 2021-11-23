package days

@AdventOfCodePuzzle(
    name = "Secure Container",
    url = "https://adventofcode.com/2019/day/4",
    date = Date(day = 4, year = 2019)
)
class Day4Faster(line: String) : Puzzle {
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
        (0..length - 2).none {
            (this[it] > this[it + 1])
        }

    private fun String.hasSameTwoAdjacentDigits() =
       (0..length - 2).any {
            this[it] == this[it + 1]
        }

    private fun String.hasExactlyTwoAdjacentDigits() =
            any { indexOf(it) == lastIndexOf(it) - 1 }
}