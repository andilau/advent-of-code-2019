package days

import java.util.Collections.indexOfSubList

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

private const val LOWER_LIMIT = 3

fun <T> List<T>.findThreeContainedListsOrNull(): Triple<List<T>, List<T>, List<T>>? {
    require(size >= LOWER_LIMIT) { "Cant compress list" }
    if (size <= LOWER_LIMIT) return Triple(listOf(this[0]), listOf(this[1]), listOf(this[2]))

    return (2..size / LOWER_LIMIT)
        .flatMap { i -> (2..size / LOWER_LIMIT - i).map { j -> Pair(i, j) } }
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
