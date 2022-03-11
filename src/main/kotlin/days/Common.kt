package days

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