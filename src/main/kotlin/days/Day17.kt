package days

private const val SCAFFOLD: Char = '#'

@AdventOfCodePuzzle(
    name = "Set and Forget",
    url = "https://adventofcode.com/2019/day/17",
    date = Date(day = 17, year = 2019)
)
class Day17(input: LongArray) : Puzzle {
    private val computer = CompleteIntCodeComputer(input)
    private val scaffold = outputFromComputer(computer)
        .joinToString("")
        .lines()
        .flatMapIndexed { row, line ->
            line.mapIndexedNotNull { column, c ->
                if (c == SCAFFOLD || c in "<>^v") Point(column, row) to c
                else null
            }
        }
        .toMap()

    override fun partOne(): Int =
        scaffold
            .keys
            .filter { it.neighbors().all { neighbor -> neighbor in scaffold } }
            .sumOf { it.x * it.y }

    override fun partTwo(): Int {
        scaffold
            .mapAsString('.') { it }
            .also { println(it) }
/* Manual solution:
        "R4,R10,R8,R4,"     -> A
        "R10,R6,R4,"        -> B
        "R4,R10,R8,R4,"     -> A
        "R10,R6,R4,"        -> B
        "R4,L12,R6,L12,"    -> C
        "R10,R6,R4,"        -> B
        "R4,L12,R6,L12,"    -> C
        "R4,R10,R8,R4,"     -> A
        "R10,R6,R4"         -> B
        "R4,L12,R6,L12"     -> C
*/
        return 0
    }

    private fun outputFromComputer(computer: CompleteIntCodeComputer) = sequence {
        while (!computer.halted) {
            computer.run(true)

            val char = try {
                computer.output.toInt().toChar()
            } catch (e: Exception) {
                // println(e)
            }
            yield(char)
        }
    }
}