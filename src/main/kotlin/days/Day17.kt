package days

private const val SCAFFOLD: Char = '#'

@AdventOfCodePuzzle(
    name = "Set and Forget",
    url = "https://adventofcode.com/2019/day/17",
    date = Date(day = 17, year = 2019)
)
class Day17(input: LongArray) : Puzzle {
    private val computer = CompleteIntCodeComputer(input)

    override fun partOne(): Int {
        val scaffold = outputfromComputer(computer)
            .joinToString("")
            .lines()
            .flatMapIndexed { row, line ->
                line.mapIndexedNotNull { column, c -> if (c == SCAFFOLD) Point(row, column) else null }
            }
            .toSet()

        return scaffold
            .filter { it.neighbors().all { neighbor -> neighbor in scaffold } }
            .sumOf { it.x * it.y }
    }

    override fun partTwo(): Int {
        return 0
    }

    private fun outputfromComputer(computer: CompleteIntCodeComputer) = sequence {
        while (!computer.halted) {
            computer.run(true)

            val char = try {
                computer.output.toInt().toChar()
            } catch (e: Exception) {
                println(e)
            }
            //println("char = ${char}")
            yield(char)
        }
    }

}