package days

private const val SCAFFOLD: Char = '#'

@AdventOfCodePuzzle(
    name = "Set and Forget",
    url = "https://adventofcode.com/2019/day/17",
    date = Date(day = 17, year = 2019)
)
class Day17(val program: LongArray) : Puzzle {
    private val computer = CompleteIntCodeComputer(program.clone())
    private val scaffold = computer.outputAsSequence()
        .map { it.toInt().toChar() }
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
        val input =
            "A,B,A,B,C,B,C,A,B,C\n" +
                    "R,4,R,10,R,8,R,4\n" +
                    "R,10,R,6,R,4\n" +
                    "R,4,L,12,R,6,L,12\n" +
                    "N\n"

        check(program[0] == 1L)
        val computer = CompleteIntCodeComputer(program.clone().apply { this[0] = 2L })

        input.map { it.code }
            .forEach {
                computer.input = it.toLong()
            }
        computer.run()

        return computer.outputAsSequence().last().toInt()
    }

    private fun CompleteIntCodeComputer.outputAsSequence() = sequence {
        if (!halted) run()

        while (true) {
            try {
                output
            } catch (e: Exception) {
                break
            }
                .also { this.yield(it) }
        }
    }
}