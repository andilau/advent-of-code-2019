package days

@AdventOfCodePuzzle(
    name = "Crossed Wires",
    url = "https://adventofcode.com/2019/day/3",
    date = Date(day = 3, year = 2019)
)
class Day3(lines: List<String>) : Puzzle {
    private val thisWire = Wire.from(lines[0])
    private val thatWire = Wire.from(lines[1])

    override fun partOne() =
        (thisWire intersectWith thatWire)
            .minOfOrNull { point -> point.manhattanDistance() }
            ?: throw IllegalStateException("Wires don't intersect")

    override fun partTwo() =
        (thisWire intersectWith thatWire)
            .minOfOrNull { point -> (thisWire distanceTo point) + (thatWire distanceTo point) }
            ?: throw IllegalStateException("Wires don't intersect")

    class Wire private constructor(descriptions: List<String>) {
        private val points = mutableListOf<Point>()

        init {
            var cursor = Point.ORIGIN
            descriptions.forEach {
                val find = PATTERN.find(it) ?: return@forEach
                val (direction, distance) = find.destructured
                points += (1..distance.toInt()).runningFold(cursor) { point, _ ->
                    when (direction) {
                        "U" -> point.up()
                        "D" -> point.down()
                        "L" -> point.left()
                        "R" -> point.right()
                        else -> throw IllegalArgumentException("Unknown direction: $direction")
                    }
                }.drop(1)
                cursor = points.last()
            }
        }

        infix fun distanceTo(point: Point): Int = points.indexOf(point) + 1

        infix fun intersectWith(other: Wire) = points intersect other.points.toSet()

        companion object {
            private val PATTERN = Regex("""([UDLR])(\d+)""")

            fun from(description: String): Wire {
                return Wire(description.split(","))
            }
        }
    }
}