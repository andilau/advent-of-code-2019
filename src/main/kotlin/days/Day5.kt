package days

@AdventOfCodePuzzle(
    name = "Sunny with a Chance of Asteroids",
    url = "https://adventofcode.com/2019/day/5",
    date = Date(day = 5, year = 2019)
)
class Day5(line: String) : Puzzle {
    private val instructions = line.split(",").map(String::toInt).toIntArray()

    override fun partOne() =
        IntCodeComputer(instructions)
            .apply { input = 1 }
            .run()
            .output
            .last()

    override fun partTwo() =
        IntCodeComputer(instructions)
            .apply { input = 5 }
            .run()
            .output
            .first()
}