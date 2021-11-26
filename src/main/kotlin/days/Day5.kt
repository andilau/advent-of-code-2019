package days

@AdventOfCodePuzzle(
    name = "Sunny with a Chance of Asteroids",
    url = "https://adventofcode.com/2019/day/5",
    date = Date(day = 5, year = 2019)
)
class Day5(private val program: IntArray) : Puzzle {
    override fun partOne() =
        IntCodeComputer(program)
            .apply { input = 1 }
            .run()
            .output
            .last()

    override fun partTwo() =
        IntCodeComputer(program)
            .apply { input = 5 }
            .run()
            .output
            .first()
}