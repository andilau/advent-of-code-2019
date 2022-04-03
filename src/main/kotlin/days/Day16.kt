package days

import kotlin.math.absoluteValue

@AdventOfCodePuzzle(
    name = "Flawed Frequency Transmission",
    url = "https://adventofcode.com/2019/day/16",
    date = Date(day = 16, year = 2019)
)
class Day16(signalAsString: String) : Puzzle {
    private val signal = signalAsString.map { Character.digit(it, 10) }
    private val cycles = (0..signal.size)
        .map { index ->
            CYCLE_PATTERN.cycle(signal.size, index + 1)
        }

    override fun partOne(): String =
        signal
            .processSignal(100, cycles)
            .take(8)
            .joinToString("")

    override fun partTwo(): String {
        val offset = signal.take(7).joinToString("").toInt()
        val signalRepeated = (1..10_000).flatMap { signal }.toIntArray()

        check(offset >= signalRepeated.size / 2) { "Offset must be between `N/2` and `N`" }

        repeat(100) {
            (signalRepeated.lastIndex downTo offset).forEach { index ->
                signalRepeated[index - 1] = (signalRepeated[index - 1] + signalRepeated[index]) % 10
            }
        }

        return signalRepeated
            .drop(offset)
            .take(8)
            .joinToString("")
    }

    private fun List<Int>.processSignal(times: Int, cycles: List<IntArray>) =
        (1..times)
            .fold(this.toIntArray()) { signal, _ -> signal.phase(cycles) }

    internal fun processSignal(repeat: Int) =
        (1..repeat).fold(signal.toIntArray()) { s, _ -> s.phase(cycles) }.joinToString("")

    companion object {
        val CYCLE_PATTERN = intArrayOf(0, 1, 0, -1)

        private fun IntArray.phase(cycles: List<IntArray>): IntArray =
            indices.map { index ->
                val cycle = cycles[index]
                mapIndexed { ix, number -> number * cycle[ix] }
                    .sum()
                    .let { (it % 10).absoluteValue }
            }.toIntArray()

        private fun IntArray.cycle(length: Int, cycleCount: Int): IntArray =
            (0..length / cycleCount)
                .flatMap { nth -> List(cycleCount) { this[nth % size] } }
                .drop(1)
                .toIntArray()
    }
}