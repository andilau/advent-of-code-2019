package days

import kotlin.math.sign

@AdventOfCodePuzzle(
    name = "Care Package",
    url = "https://adventofcode.com/2019/day/13",
    date = Date(day = 13, year = 2019)
)
class Day13(val program: LongArray) : Puzzle {

    override fun partOne(): Int =
        CompleteIntCodeComputer(program)
            .run()
            .outputAsSequence()
            .windowed(3, 3)
            .count { it.last() == BLOCK }

    override fun partTwo(): Int {
        val code = program.clone().apply { this[0] = 2 }
        val computer = CompleteIntCodeComputer(code)

        var score = 0
        var paddle = 0
        while (true) {
            computer.run()
            try {
                val x = computer.output.toInt()
                computer.output.toInt()         // y is not relevant
                val block = computer.output

                when {
                    (x == -1) -> score = block.toInt()
                    block == PADDLE -> paddle = x
                    block == BALL -> computer.input = (x - paddle).sign.toLong()
                }
            } catch (e: IllegalStateException) {
                return score
            }
        }
    }

    private fun CompleteIntCodeComputer.outputAsSequence() = sequence {
        while (true) {
            try {
                yield(output)
            } catch (e: Exception) {
                break
            }
        }
    }

    companion object {
        private const val WALL = 1L
        private const val BLOCK = 2L
        private const val PADDLE = 3L
        private const val BALL = 4L
    }
}