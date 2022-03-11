package days

import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.sqrt

@AdventOfCodePuzzle(
    name = "Monitoring Station",
    url = "https://adventofcode.com/2019/day/10",
    date = Date(day = 10, year = 2019)
)
class Day10(input: List<String>) : Puzzle {
     private val asteroids: List<Asteroid> = parseAsteroids(input)


    override fun partOne() = asteroids
        .findOptimalBase().asteroidsInSight()

    override fun partTwo() = asteroids
        .findOptimalBase()
        .targets()
        .drop(199).first()
        .let { (it.x * 100) + it.y }

    fun findOptimalBase() = asteroids.findOptimalBase()

    fun targetsAsteroids(base: Asteroid) = base.targets()

    private fun List<Asteroid>.findOptimalBase() =
        maxByOrNull { base ->
            base.asteroidsInSight()
        } ?: throw IllegalStateException("Best Base not found")

    private fun Asteroid.asteroidsInSight() =
        asteroids.filter { it != this }
            .map { relativeTo(it) }
            .distinctBy { it.angle() }
            .size

    private fun Asteroid.targets() =
        asteroids
            .filter { it != this }
            .groupBy { it.relativeTo(this).angle() }
            .mapValues { it.value.sortedBy { it.relativeTo(this).distance() } }
            .toSortedMap()
            .values
            .flattenByIndex()

    private fun parseAsteroids(input: List<String>) = input.flatMapIndexed { y, line ->
        line.mapIndexedNotNull { x, what ->
            if (what == ASTEROID) Asteroid(x, y) else null
        }
    }

    data class Asteroid(val x: Int, val y: Int) {
        infix fun Int.to(that: Int): Asteroid = Asteroid(this, that)

        fun relativeTo(base: Asteroid): Asteroid =
            x - base.x to y - base.y

        private operator fun plus(base: Asteroid): Asteroid =
            x + base.x to y + base.y

        private operator fun minus(that: Asteroid): Asteroid =
            x - that.x to y - that.y

        fun distance() = sqrt((x * x) + (y * y).toDouble())

        /*  https://math.stackexchange.com/questions/1201337/finding-the-angle-between-two-points"
         */
        fun angle() = (PI / 2 + atan2(y.toDouble(), x.toDouble()))
            .let { if (it >= 0) it else it + 2 * PI }
    }

    companion object {
        const val ASTEROID = '#'
    }
}