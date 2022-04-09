package days

import java.util.*

@AdventOfCodePuzzle(
    name = "Set and Forget",
    url = "https://adventofcode.com/2019/day/17",
    date = Date(day = 17, year = 2019)
)
class Day17(val program: LongArray) : Puzzle {

    private val computer = CompleteIntCodeComputer(program.clone())
    private val scaffold = computer.outputAsSequence()
        .map { it.toInt().toChar() }
        .joinToString("")
        .lines()
        .flatMapIndexed { row, line ->
            line.mapIndexedNotNull { column, c ->
                if (c == SCAFFOLD || c in ROBOT) Point(column, row) to c
                else null
            }
        }
        .toMap()

    override fun partOne(): Int =
        scaffold
            .keys
            .filter { it.neighbors().all { neighbor -> neighbor in scaffold } }
            .sumOf { it.x * it.y }

    override fun partTwo(): Int {
        scaffold
            .mapAsString('.') { it }
            .also { println(it) }

        val from = scaffold.filterValues { it in ROBOT }.firstNotNullOfOrNull { it.key } ?: error("No start")
        val to = scaffold.filterValues { it == SCAFFOLD }.keys.first { it.neighbors().count { it in scaffold } == 1 }

        println("start = $from")
        println("to = $to")
        val strides = scaffold
            .mapValues { true }
            .pathStrides(from, to)
            .toList()
        strides
            .joinToString(",")
            .also { println(it) }

        val three = strides.compressToThree() ?: error("Cant find three contained lists")

        val compressed = strides
            .getOrder(three.toList())
            .translate(three.toList())
            .map { it.toChar() + 65 }
            .joinToString(",")
            .also { println("$it") }
        val map = three.toList()
            .map { list ->
                list.joinToString(",") { (turn, length) ->
                    buildString {
                        append(turn.toString().take(1))
                        append(",")
                        append(length)
                    }
                }
            }

        val i = listOf(compressed) + map + "N"
        println("i = $i")
        val input = i.joinToString("\n", postfix = "\n")
        return CompleteIntCodeComputer(program.clone().apply { this[0] = 2L })
            .inputMultiple(input)
            .run()
            .outputAsSequence().last().toInt()
    }

    private fun <T> List<T>.getOrder(lists: List<List<T>>) = sequence<List<T>> {
        val sub = this@getOrder.toMutableList()
        while (sub.isNotEmpty()) {
            lists
                .filter { Collections.indexOfSubList(sub, it) == 0 }
                .forEach {
                    repeat(it.size) { sub.removeAt(0) }
                    yield(it)
                }
        }
    }

    private fun <T> Sequence<List<T>>.translate(lists: List<List<T>>) =
        map { lists.indexOf(it) }

    private fun <T> List<T>.compressToThree(): Triple<List<T>, List<T>, List<T>>? {
        require(size >= 3) { "Cant compress list" }
        if (size <= 3) return Triple(listOf(this[0]), listOf(this[1]), listOf(this[2]))

        return (2..size / 3)
            .flatMap { i -> (2..size / 3 - i).map { j -> Pair(i, j) } }
            .mapNotNull { (i, j) ->
                val first = take(j)
                val second = drop(j).dropLast(i)
                val third = takeLast(i)
                val substituted = second.subtractAll(first).subtractAll(third)
                (2..size / 2).asSequence()
                    .filter { substituted.size % it == 0 }
                    .firstOrNull { substituted.chunked(it).zipWithNext().all { it.first == it.second } }
                    ?.let { Triple(first, substituted.take(it), third) }
            }
            .minByOrNull { it.toList().sumOf { it.size } }
            ?.also { println("Found: $it") }
    }

    private fun CompleteIntCodeComputer.inputMultiple(string: String) =
        apply {
            string
                .map(Char::code)
                .forEach { input = it.toLong() }
        }

    private fun CompleteIntCodeComputer.outputAsSequence() = sequence {
        if (!halted) run()

        while (true) {
            try {
                output
            } catch (_: IllegalStateException) {
                break
            }
                .also { this.yield(it) }
        }
    }

    data class Stride(val turn: Turn, val length: Int) {
        enum class Turn { LEFT, RIGHT }

        override fun toString(): String = "${turn.toString().first()}$length"
    }

    private fun Map<Point, Boolean>.pathStrides(from: Point, to: Point? = null) = sequence<Stride> {
        val queue = PriorityQueue<List<Point>>(Comparator.comparing { size })
            .apply { add(listOf(from)) }

        var stride: Stride? = null

        while (queue.isNotEmpty()) {
            val path = queue.poll()
            if (path.last() == to) {
                stride?.let { yield(it) }
                break
            }

            val direction = path.takeLast(2).takeIf { list -> list.size == 2 }
                ?.let { list -> list.last() - list.first() }
                ?: Point.ORIGIN.up()

            stride = when {
                path.last() + direction in this@pathStrides.keys -> {
                    queue += path + (path.last() + direction)
                    stride?.copy(length = stride.length + 1)
                }
                path.last() + direction.rotateLeft() in this@pathStrides.keys -> {
                    queue += path + (path.last() + direction.rotateLeft())
                    stride?.let { yield(it) }
                    Stride(Stride.Turn.LEFT, 1)
                }
                path.last() + direction.rotateRight() in this@pathStrides.keys -> {
                    queue += path + (path.last() + direction.rotateRight())
                    stride?.let { yield(it) }
                    Stride(Stride.Turn.RIGHT, 1)
                }
                else -> null
            }
        }
    }

    private fun Point.rotateLeft() = Point(y, -x)
    private fun Point.rotateRight() = Point(-y, x)

    companion object {
        private const val SCAFFOLD = '#'
        private const val ROBOT = "<>^v"
    }
}
