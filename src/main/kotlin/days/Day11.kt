package days

import days.Day11.PaintRobot.PanelColor
import days.Day11.PaintRobot.PanelColor.BLACK
import days.Day11.PaintRobot.PanelColor.WHITE
import days.Direction.*
import days.Point.Companion.ORIGIN

@AdventOfCodePuzzle(
    name = "Space Police",
    url = "https://adventofcode.com/2019/day/11",
    date = Date(day = 11, year = 2019)
)
class Day11(val input: String) : Puzzle {
    private val program = input.toLongNumbers()

    override fun partOne(): Int =
        Panels()
            .paintWith(CompleteIntCodeComputer(program))
            .painted()

    override fun partTwo(): Unit =
        Panels().apply { put(ORIGIN, WHITE) }
            .paintWith(CompleteIntCodeComputer(program))
            .mapAsString(BLACK) { if (it == BLACK) ' ' else '#' }
            .run { print(this) }

    class Panels : MutableMap<Point, PanelColor> by HashMap() {
        fun painted() = size

        fun paintWith(computer: CompleteIntCodeComputer): Panels {
            val robot = PaintRobot(computer)

            while (!robot.halted()) {
                val position = robot.position
                val currColor = getOrDefault(position, BLACK)
                val nextColor = robot.step(currColor)
                this[position] = nextColor
            }
            return this
        }
    }

    data class PaintRobot(
        val computer: CompleteIntCodeComputer,
        var position: Point = ORIGIN,
        var direction: Direction = NORTH
    ) {

        fun halted() = computer.halted

        fun step(panelColor: PanelColor): PanelColor {
            if (computer.halted) throw IllegalStateException("Paint Robot halted.")

            computer
                .apply { input = panelColor.ordinal.toLong() }
                .run()
            val paint = computer.output.toInt()
            val rightOrLeft = computer.output.toInt()

            when (rightOrLeft) {
                0 -> turnLeft()
                1 -> turnRight()
            }
            move()

            return PanelColor.from(paint)
        }

        private fun turnLeft() = direction.left().also { direction = it }
        private fun turnRight() = direction.right().also { direction = it }
        private fun move() = when (direction) {
            NORTH -> position.up()
            EAST -> position.right()
            SOUTH -> position.down()
            WEST -> position.left()
        }.also { position = it }

        enum class PanelColor {
            BLACK, WHITE;

            companion object {
                fun from(index: Int) = when (index) {
                    0 -> BLACK
                    1 -> WHITE
                    else -> throw IllegalArgumentException("Not a color id: $index")
                }
            }
        }
    }
}