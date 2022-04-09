package days

import java.util.PriorityQueue

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
                Stride(Stride.Turn.LEFT, 1)
            }
            path.last() + direction.rotateRight() in this@findPathStrides.keys -> {
                path += (path.last() + direction.rotateRight())
                stride?.let { yield(it) }
                Stride(Stride.Turn.RIGHT, 1)
            }
            else -> {
                stride?.let { yield(it) }
                break
            }
        }
    }
}
