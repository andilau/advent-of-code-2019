package days

@AdventOfCodePuzzle(
    name = "1202 Program Alarm",
    url = "https://adventofcode.com/2019/day/2",
    date = Date(day = 2, year = 2019)
)
class Day2(private var program: IntArray) : Puzzle {
    override fun partOne() =
        program.clone().run(12, 2).first()

    override fun partTwo() =
        pairsFrom(0..99, 0..99)
            .first { (noun, verb) ->
                program.clone().run(noun, verb).first() == 19690720
            }
            .let { (noun, verb) ->
                100 * noun + verb
            }

    fun testProgram() = program.run()

    private fun add(a: Int, b: Int) = a + b
    private fun multiply(a: Int, b: Int) = a * b
    private fun IntArray.run(noun: Int = this[1], verb: Int = this[2]): IntArray {
        apply {
            this[1] = noun
            this[2] = verb
        }
            .indices.filter { it % 4 == 0 }.forEach { ip ->
                 val value = when (this[ip]) {
                    1 -> add(getByReference(ip + 1), getByReference(ip + 2))
                    2 -> multiply(getByReference(ip + 1), getByReference(ip + 2))
                    99 -> return this
                    else -> throw IllegalArgumentException("Unhandled opcode: $this[ip]")
                }
                this[this[ip + 3]] = value
            }
        throw IllegalStateException("Somethings wrong: $this[ip]")
    }

    private fun IntArray.getByReference(ip: Int) = this[this[ip]]

    private fun pairsFrom(nounRange: IntRange, verbRange: IntRange) = sequence {
        nounRange.forEach { noun ->
            verbRange.forEach { verb ->
                yield(Pair(noun, verb))
            }
        }
    }
}