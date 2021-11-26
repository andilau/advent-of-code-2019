package days

@AdventOfCodePuzzle(
    name = "Amplification Circuit",
    url = "https://adventofcode.com/2019/day/7",
    date = Date(day = 7, year = 2019)
)
class Day7(private val program: IntArray) : Puzzle {
    override fun partOne() =
        listOf(0, 1, 2, 3, 4)
            .permutations()
            .map(::runInLine)
            .maxOrNull()
            ?: throw IllegalStateException("No elements found")

    override fun partTwo() =
        listOf(5, 6, 7, 8, 9)
            .permutations()
            .map(::runInFeedbackLoop)
            .maxOrNull()
            ?: throw IllegalStateException("No elements found")

    private fun runInLine(configuration: List<Int>) =
        configuration.fold(0) { signal, phase ->
            IntCodeComputer(program)
                .apply { input = phase }
                .apply { input = signal }
                .run()
                .output
                .first()
        }

    private fun runInFeedbackLoop(configuration: List<Int>): Int {
        val amps: List<IntCodeComputer> = configuration
            .map { phase ->
                IntCodeComputer(program).apply { input = phase }
            }
            .also { it.first().input = 0 }

        val pairs = amps.zipWithNext().toList() + Pair(amps.last(), amps.first())
        while (amps.any { !it.halted() }) {
            pairs.forEach { (before, after) ->
                after.input = before.runBlocking().output.last()
            }
        }
        return amps.last().output.last()
    }

    private fun <T> List<T>.permutations(): Set<List<T>> {
        if (this.isEmpty()) return setOf(emptyList())

        val result: MutableSet<List<T>> = mutableSetOf()
        for (i in this.indices) {
            (this - this[i]).permutations().forEach { item ->
                result.add(item + this[i])
            }
        }
        return result
    }
}