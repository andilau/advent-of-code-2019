package days

import kotlin.math.absoluteValue
import kotlin.math.sign

fun String.toLongNumbers(): LongArray =
    split(",").map(String::toLong).toLongArray()

fun <T> Collection<Iterable<T>>.flattenByIndex(): Sequence<T> = sequence {
    var index = 0
    while (true) {
        var found = false
        this@flattenByIndex.forEach {
            it.elementAtOrNull(index)?.let { found = true; yield(it) }
        }
        if (!found) break
        index++
    }
}

data class Point(val x: Int, val y: Int) {
    fun up() = copy(y = y + 1)
    fun down() = copy(y = y - 1)
    fun left() = copy(x = x - 1)
    fun right() = copy(x = x + 1)

    fun manhattanDistance(to: Point = ORIGIN) =
        (x - to.x).absoluteValue + (y - to.y).absoluteValue

    companion object {
        val ORIGIN = Point(0, 0)
    }
}

enum class Direction() {
    NORTH, EAST, SOUTH, WEST;

    fun left() = when (this) {
        NORTH -> WEST
        WEST -> SOUTH
        SOUTH -> EAST
        EAST -> NORTH
    }
    fun right() = left().left().left()
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

fun lcm(x: Long, y: Long, vararg ints: Long): Long =
    ints.fold(x * y / gcd(x, y)) { acc, z -> lcm(acc, z) }

fun gcd(a: Long, b: Long): Long {
    if (b == 0L) return a
    return gcd(b, a % b)
}