package days

@AdventOfCodePuzzle(
    name = "Set and Forget",
    url = "https://adventofcode.com/2019/day/17",
    date = Date(day = 17, year = 2019)
)
class Day17(val program: LongArray) : Puzzle {

    private val computer = CompleteIntCodeComputer(program.clone())
    private val scaffold: Map<Point, Char> =
        computer
            .outputAsCharSequence()
            .buildScaffold()
            .also { map -> println(map.mapAsString('.') { it }) }

    override fun partOne(): Int =
        scaffold
            .keys
            .filter { it.neighbors().all { neighbor -> neighbor in scaffold } }
            .sumOf { it.x * it.y }

    override fun partTwo(): Int {
        val from = scaffold
            .filterValues { it in ROBOT }
            .firstNotNullOfOrNull { it.key }
            ?: error("No start found")

        val strides = scaffold
            .mapValues { true }
            .findPathStrides(from, Point.ORIGIN.up())
            .toList()

        val three = strides
            .findThreeContainedListsOrNull()
            ?: error("Cant find three contained lists")

        val compressedString = strides
            .sequenceOfLists(three.toList())
            .map(three.toList()::indexOf)
            .map { it.toChar() + 'A'.code }
            .joinToString(",")

        val subListStrings = three.toList()
            .map { list ->
                list.joinToString(",") { (turn, length) ->
                    "${turn.toString().take(1)},$length"
                }
            }

        val instructions = listOf(
            compressedString,
            subListStrings[0],
            subListStrings[1],
            subListStrings[2],
            "N" // No playback
        ).joinToString("\n", postfix = "\n")

        return CompleteIntCodeComputer(program.clone().apply { this[0] = 2L })
            .inputMultipleFrom(instructions)
            .run()
            .outputAsCharSequence().last().code
    }

    private fun Sequence<Char>.buildScaffold() =
        joinToString("")
            .lines()
            .flatMapIndexed { row, line ->
                line.mapIndexedNotNull { column, c ->
                    if (c == SCAFFOLD || c in ROBOT) Point(column, row) to c
                    else null
                }
            }
            .toMap()

    private fun CompleteIntCodeComputer.inputMultipleFrom(string: String) =
        apply {
            string
                .map(Char::code)
                .forEach { input = it.toLong() }
        }

    private fun CompleteIntCodeComputer.outputAsCharSequence() = sequence {
        if (!halted) run()
        while (true) {
            runCatching { output }
                .getOrNull()
                ?.let { yield(it.toInt().toChar()) }
                ?: break
        }
    }

    companion object {
        private const val SCAFFOLD = '#'
        private const val ROBOT = "<>^v"
    }
}
