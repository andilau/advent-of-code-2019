package days

import kotlin.math.absoluteValue
import kotlin.math.sign

data class Point(val x: Int, val y: Int) {
    fun up() = copy(y = y - 1)
    fun down() = copy(y = y + 1)
    fun left() = copy(x = x - 1)
    fun right() = copy(x = x + 1)

    fun manhattanDistance(to: Point = ORIGIN) =
        (x - to.x).absoluteValue + (y - to.y).absoluteValue

    fun neighbors() = listOf(up(), left(), down(), right())

    operator fun minus(other: Point): Point = Point(x - other.x, y - other.y)

    operator fun plus(other: Point): Point = Point(x + other.x, y + other.y)

    fun rotateLeft() = Point(y, -x)

    fun rotateRight() = Point(-y, x)

    companion object {
        val ORIGIN = Point(0, 0)
    }
}

data class Point3D(val x: Int, val y: Int, val z: Int) {
    infix fun sign(other: Point3D) = Point3D(
        (other.x - x).sign,
        (other.y - y).sign,
        (other.z - z).sign,
    )

    operator fun plus(other: Point3D) = Point3D(x + other.x, y + other.y, z + other.z)

    fun manhattanDistance(to: Point3D = ORIGIN) =
        (x - to.x).absoluteValue + (y - to.y).absoluteValue + (z - to.z).absoluteValue

    companion object {
        val ORIGIN = Point3D(0, 0, 0)
    }
}

enum class Direction {
    NORTH, EAST, SOUTH, WEST;

    fun left() = when (this) {
        NORTH -> WEST
        WEST -> SOUTH
        SOUTH -> EAST
        EAST -> NORTH
    }

    fun right() = left().left().left()

    companion object {
        fun from(movement: Point) =
            when (movement) {
                Point.ORIGIN.up() -> NORTH
                Point.ORIGIN.down() -> SOUTH
                Point.ORIGIN.left() -> WEST
                Point.ORIGIN.right() -> EAST
                else -> throw IllegalArgumentException("Unknown direction: $movement")
            }
    }
}

fun <T> Map<Point, T>.mapAsString(default: T, mapping: (T) -> Char) =
    buildString {
        val map = this@mapAsString
        val yRange = keys.minOf(Point::y)..keys.maxOf(Point::y)
        val xRange = (keys.minOf(Point::x)..keys.maxOf(Point::x))
        for (y in yRange) {
            val line = xRange
                .map { x -> map.getOrDefault(Point(x, y), default) }
                .map { mapping(it) }
                .joinToString("")
            appendLine(line)
        }
    }
