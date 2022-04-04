package days

import days.Day15.Block.*
import days.Point.Companion.ORIGIN
import java.util.*

@AdventOfCodePuzzle(
    name = "Oxygen System",
    url = "https://adventofcode.com/2019/day/15",
    date = Date(day = 15, year = 2019)
)
class Day15(input: LongArray) : Puzzle {
    private val computer = CompleteIntCodeComputer(input)
    private val map = mutableMapOf(ORIGIN to SPACE).exploreMap()
    private val locationOxygen = map.entries.first { it.value == OXYGEN }.key

    init {
        map.mapAsString(WALL, Block::symbol)
            // .also { println(it) }
    }

    override fun partOne(): Int = map
        .allowedLocations()
        .findPath(ORIGIN, locationOxygen)
        .lastIndex

    override fun partTwo(): Int = map
        .allowedLocations()
        .findPath(locationOxygen)
        .lastIndex

    private fun Map<Point, Block>.allowedLocations() =
        mapValues {
            when (it.value) {
                WALL -> false
                SPACE, OXYGEN, HERE -> true
            }
        }

    private fun Map<Point, Boolean>.findPath(from: Point, to: Point? = null): List<Point> {
        val queue = PriorityQueue<List<Point>>(Comparator.comparing { size })
            .apply { add(listOf(from)) }
        val visited = mutableSetOf<Point>()
        var longestPath = emptyList<Point>()

        while (queue.isNotEmpty()) {
            val path = queue.poll()
            if (path.last() == to) return path

            if (path.last() in visited) continue
            visited += path.last()

            val next = path.last()
                .neighbors()
                .filter { this.getOrDefault(it, false) }
                .filter { it !in visited }

            if (next.isEmpty()) {
                if (path.size > longestPath.size) longestPath = path
            }

            next.forEach { queue += path + it }
        }

        return longestPath.ifEmpty { error("No path found") }
    }

    private fun MutableMap<Point, Block>.exploreMap(here: Point = ORIGIN): MutableMap<Point, Block> {
        here.neighbors()
            .filter { it !in this }
            .forEach { to ->
                val direction = direction(to - here)
                val statusCode = computer.process(direction)
                val block = Block.from(statusCode)
                this[to] = block
                when (block) {
                    SPACE, OXYGEN -> {
                        exploreMap(to)
                        // go back
                        val back = direction(here - to)
                        computer.process(back)
                    }
                    else -> {}
                }
            }
        return this
    }

    enum class Block(val id: Int, val symbol: Char) {
        WALL(0, '#'),
        SPACE(1, ' '),
        OXYGEN(2, 'O'),
        HERE(3, 'X');

        companion object {
            fun from(id: Int) = values()
                .firstOrNull { it.id == id }
                ?: error("Invalid block id: $id")
        }
    }

    private fun CompleteIntCodeComputer.process(direction: Int): Int {
        input = direction.toLong()
        run()
        return output.toInt()
    }

    // north (1), south (2), west (3), and east (4)
    private fun direction(movement: Point): Int =
        when (movement) {
            ORIGIN.up() -> 1
            ORIGIN.down() -> 2
            ORIGIN.left() -> 3
            ORIGIN.right() -> 4
            else -> throw IllegalArgumentException("Unknown direction: $movement")
        }
}