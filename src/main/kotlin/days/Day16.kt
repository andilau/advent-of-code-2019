package days

import kotlin.math.absoluteValue

@AdventOfCodePuzzle(
    name = "Flawed Frequency Transmission",
    url = "https://adventofcode.com/2019/day/16",
    date = Date(day = 16, year = 2019)
)
class Day16(val signalAsString: String) : Puzzle {
    private val signal = signalAsString.map { Character.digit(it, 10) }

    override fun partOne(): String =
        signal
            .signalFor(100)
            .take(8)
            .joinToString("")

    override fun partTwo(): Int {
        val offset = signalAsString.take(7).toInt()
        val inputRepeated = (offset until 10_000 * signal.size)
        return 0
    }

    private fun List<Int>.signalFor(repeat: Int) =
        (1..repeat).fold(this) { s, _ -> s.phase(BASE_PATTERN) }

    internal fun signalFor(repeat: Int) =
        (1..repeat).fold(signal) { s, _ -> s.phase(BASE_PATTERN) }.joinToString("")

    companion object {
        val BASE_PATTERN = listOf(0, 1, 0, -1)

        private fun List<Int>.phase(pattern: List<Int>): List<Int> {
            return List(this.size) { index ->
                val cycle = pattern.cycle(size, index + 1)
                this.mapIndexed { ix, element -> element * cycle[ix] }
                    .sum()
                    .let { (it % 10).absoluteValue }
            }
        }

        private fun List<Int>.cycle(length: Int, cycleCount: Int): List<Int> {
            return (0..length / cycleCount)
                .flatMap { nth -> List(cycleCount) { this[nth % size] } }
                .drop(1)
        }
    }
}
