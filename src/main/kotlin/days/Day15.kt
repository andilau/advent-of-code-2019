package days

@AdventOfCodePuzzle(
    name = "Oxygen System",
    url = "https://adventofcode.com/2019/day/15",
    date = Date(day = 15, year = 2019)
)
class Day15(input: LongArray) : Puzzle {
    val computer = CompleteIntCodeComputer(input)

    override fun partOne(): Int {
        return RepairDroid().position.manhattanDistance()
    }


    override fun partTwo(): Any {
        return 0L
    }

    data class RepairDroid(val position: Point = Point.ORIGIN) {

    }
}