package days

import kotlin.math.absoluteValue

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

    fun manhattanDistance(to: Point = ORIGIN) = (x - to.x).absoluteValue + (y - to.y).absoluteValue

    companion object {
        val ORIGIN = Point(0, 0)
    }
}