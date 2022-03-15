package days

import kotlin.math.sign

@AdventOfCodePuzzle(
    name = "Care Package",
    url = "https://adventofcode.com/2019/day/13",
    date = Date(day = 13, year = 2019)
)
class Day13(val program: LongArray) : Puzzle {

    override fun partOne(): Int = outputAsSequence(program)
        .windowed(3, 3)
        .count { it.last() == BLOCK }

    override fun partTwo(): Int {
        val map = program.clone().apply { this[0] = 2 }
        val computer = CompleteIntCodeComputer(map)

        var score = 0
        var paddle = 0
        while (true) {
            computer.run()
            try {
                val x = computer.output.toInt()
                computer.output.toInt()
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

    private fun outputAsSequence(program: LongArray) = sequence {
        val computerWithOutput = CompleteIntCodeComputer(program).run()
        while (true) {
            try {
                yield(computerWithOutput.output)
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