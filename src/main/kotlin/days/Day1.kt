package days

@AdventOfCodePuzzle(
    name = "Puzzle Name",
    url = "https://adventofcode.com/2000/day/1",
    date = Date(day = 1, year = 2000)
)
class Day1(private val input: List<Int>) : Puzzle {
    override fun partOne() = input.sum()

    override fun partTwo() = input.foldRight(1) { element, acc -> element * acc }
}