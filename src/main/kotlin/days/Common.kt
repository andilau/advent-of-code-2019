package days

import days.Stride.Turn
import java.util.Collections.indexOfSubList
import java.util.PriorityQueue
import kotlin.math.absoluteValue
import kotlin.math.sign

fun String.toLongNumbers(): LongArray =
    split(",").map(String::toLong).toLongArray()

fun lcm(x: Long, y: Long, vararg ints: Long): Long =
    ints.fold(x * y / gcd(x, y)) { acc, z -> lcm(acc, z) }

fun gcd(a: Long, b: Long): Long {
    if (b == 0L) return a
    return gcd(b, a % b)
}

fun <T> Collection<Iterable<T>>.flattenByIndex(): Sequence<T> = sequence {
    var index = 0
    while (true) {
        var found = false
        this@flattenByIndex.forEach { iterable ->
            iterable.elementAtOrNull(index)?.let { found = true; yield(it) }
        }
        if (!found) break
        index++
    }
}

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

fun Map<Point, Boolean>.findPath(from: Point, to: Point? = null): List<Point> {
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

data class Stride(val turn: Turn, val length: Int) {
    enum class Turn { LEFT, RIGHT }

    override fun toString(): String = "${turn.toString().first()}$length"
}

fun Map<Point, Boolean>.findPathStrides(from: Point, orientation: Point) = sequence {
    val path = mutableListOf(from)
    var stride: Stride? = null

    while (true) {
        val direction = path
            .takeLast(2)
            .takeIf { list -> list.size == 2 }
            ?.let { list -> list.last() - list.first() }
            ?: orientation

        stride = when {
            path.last() + direction in this@findPathStrides.keys -> {
                path += (path.last() + direction)
                stride?.copy(length = stride.length + 1)
            }
            path.last() + direction.rotateLeft() in this@findPathStrides.keys -> {
                path += (path.last() + direction.rotateLeft())
                stride?.let { yield(it) }
                Stride(Turn.LEFT, 1)
            }
            path.last() + direction.rotateRight() in this@findPathStrides.keys -> {
                path += (path.last() + direction.rotateRight())
                stride?.let { yield(it) }
                Stride(Turn.RIGHT, 1)
            }
            else -> {
                stride?.let { yield(it) }
                break
            }
        }
    }
}

fun <T> List<T>.findThreeContainedListsOrNull(): Triple<List<T>, List<T>, List<T>>? {
    require(size >= 3) { "Cant compress list" }
    if (size <= 3) return Triple(listOf(this[0]), listOf(this[1]), listOf(this[2]))

    return (2..size / 3)
        .flatMap { i -> (2..size / 3 - i).map { j -> Pair(i, j) } }
        .mapNotNull { (i, j) ->
            val first = take(j)
            val second = drop(j).dropLast(i)
            val third = takeLast(i)
            val substituted = second.subtractAllSubLists(first).subtractAllSubLists(third)
            (1..size / 2).asSequence()
                .filter { substituted.size % it == 0 }
                .firstOrNull { size ->
                    substituted
                        .windowed(size, size)
                        .zipWithNext()
                        .all { it.first == it.second }
                }
                ?.let { Triple(first, substituted.take(it), third) }
        }
        .minByOrNull { it.toList().sumOf { it.size } }
}

fun <T> List<T>.subtractAllSubLists(pattern: List<T>): List<T> {
    val list = this.toMutableList()
    if (pattern.isEmpty()) return list
    while (true) {
        val indexOfSubList = indexOfSubList(list, pattern)
        if (indexOfSubList == -1) break
        repeat(pattern.size) { list.removeAt(indexOfSubList) }
    }
    return list
}

fun <T> List<T>.sequenceOfLists(lists: List<List<T>>) = sequence {
    val list = this@sequenceOfLists.toMutableList()
    while (list.isNotEmpty()) {
        lists
            .filter { indexOfSubList(list, it) == 0 }
            .forEach {
                repeat(it.size) { list.removeAt(0) }
                yield(it)
            }
    }
}
