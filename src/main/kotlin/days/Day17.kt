package days

private const val ROBOT = "<>^v"

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
                if (c == SCAFFOLD || c in ROBOT) Point(column, row) to c
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

        val from = scaffold.filterValues { it in ROBOT }.firstNotNullOfOrNull { it.key } ?: error("No start")
        val to = scaffold.filterValues { it == SCAFFOLD }.keys.filter { it.neighbors().count { it in scaffold } == 1 }.first()

        println("start = ${from}")
        println("to = ${to}")
        val path = scaffold.mapValues { true }.findPath(from, to)
        println("mapValues = ${path}")

        /*Manual solution:
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

        val computer = CompleteIntCodeComputer(program.clone().apply { this[0] = 2L })
        computer.inputMultiple(input)
        return computer.run().outputAsSequence().last().toInt()
    }

    private fun CompleteIntCodeComputer.inputMultiple(string: String) {
        string.map { it.code }
            .forEach {
                this.input = it.toLong()
            }
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

    companion object {
        private const val SCAFFOLD: Char = '#'
    }
}