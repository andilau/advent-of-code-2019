package days

@AdventOfCodePuzzle(
    name = "Sensor Boost",
    url = "https://adventofcode.com/2019/day/9",
    date = Date(day = 9, year = 2019)
)
class Day9(val input: String) : Puzzle {
    private val code = input.toLongNumbers()

    override fun partOne() = CompleteIntCodeComputer(code).apply { input = 1 }.run().output

    override fun partTwo() = CompleteIntCodeComputer(code).apply { input = 2 }.run().output

    private fun String.toLongNumbers(): LongArray =
        split(",").map(String::toLong).toLongArray()
}