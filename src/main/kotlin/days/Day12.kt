package days

@AdventOfCodePuzzle(
    name = "The N-Body Problem",
    url = "https://adventofcode.com/2019/day/12",
    date = Date(day = 12, year = 2019)
)
class Day12(scan: List<String>) : Puzzle {
    private val moons = scan.map(Moon::from)

    override fun partOne(): Int =
        (0 until 1000)
            .fold(moons.deepCopy()) { m, _ -> m.tick() }
            .sumOf { it.energy() }

    override fun partTwo(): Long {
        // X-Y-Z frequencies are independent
        val moonsX = moons.map { it.position.x }
        val moonsY = moons.map { it.position.y }
        val moonsZ = moons.map { it.position.z }
        var frequencyX: Long? = null
        var frequencyY: Long? = null
        var frequencyZ: Long? = null

        generateSequence(moons.deepCopy()) { it.tick() }
            .drop(1)
            .forEachIndexed { index, m ->
                if (frequencyX == null && m.map { it.position.x } == moonsX) frequencyX =
                    index + 2L // <- 1-based & first-dropped
                if (frequencyY == null && m.map { it.position.y } == moonsY) frequencyY = index + 2L
                if (frequencyZ == null && m.map { it.position.z } == moonsZ) frequencyZ = index + 2L

                if (frequencyX != null && frequencyY != null && frequencyZ != null)
                    return lcm(frequencyX!!, frequencyY!!, frequencyZ!!)
            }
        error("Cycle not found.")
    }

    private fun List<Moon>.deepCopy(): List<Moon> = this.map { it.copy() }

    private fun List<Moon>.tick(): List<Moon> {
        // update velocity by applying gravity to pairs of moons
        pairs().forEach { (one, another) ->
            one.updateVelocity(another).also { another.updateVelocity(one) }
        }
        // update position by applying velocity
        forEach { it.updatePosition() }
        return this
    }

    internal fun stepAndMoonsAsString(steps: Int) =
        (0 until steps)
            .fold(moons.deepCopy()) { acc, _ -> acc.tick() }
            .joinToString("\n") { it.shortString() }

    private fun <T> List<T>.pairs(): Iterable<Pair<T, T>> =
        flatMapIndexed { ix, first ->
            drop(ix + 1).map { second -> Pair(first, second) }
        }

    data class Moon(var position: Point3D, var velocity: Point3D = Point3D.ORIGIN) {

        fun updateVelocity(other: Moon) = run { velocity += (position sign other.position) }

        fun updatePosition() = run { position += velocity }

        fun energy() = position.manhattanDistance() * velocity.manhattanDistance()

        fun shortString() = buildString {
            append("pos=<x=${position.x},y=${position.y},z=${position.z}>,")
            append("vel=<x=${velocity.x},y=${velocity.y},z=${velocity.z}>")
        }

        companion object {
            fun from(line: String): Moon =
                POSITIONS_PATTERN.matchEntire(line)?.let {
                    val (x, y, z) = it.destructured
                    Moon(position = Point3D(x.toInt(), y.toInt(), z.toInt()))
                } ?: throw IllegalArgumentException("Can't parse position data from line: $line")

            private val POSITIONS_PATTERN = Regex("""<x=(-?\d+), y=(-?\d+), z=(-?\d+)>""")
        }
    }
}