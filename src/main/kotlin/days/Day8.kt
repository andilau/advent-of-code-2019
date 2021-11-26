package days

@AdventOfCodePuzzle(
    name = "Space Image Format",
    url = "https://adventofcode.com/2019/day/8",
    date = Date(day = 8, year = 2019)
)
class Day8(private val input: String) : Puzzle {
    private val layers = input.chunked(WIDE * TALL)

    override fun partOne() =
        layers
            .minByOrNull { layer -> layer.count { it == '0' } }
            ?.let { layer -> layer.count { it == '1' } * layer.count { it == '2' } }
            ?: 0

    override fun partTwo() =
        (0 until WIDE * TALL)
            .map { at ->
                if (layers
                    .map { it[at] }
                    .firstOrNull { it != '2' } == '1') '@' else ' '
            }
            .chunked(WIDE)
            .forEach {
                println(it.joinToString(""))
            }

    companion object {
        const val WIDE = 25
        const val TALL = 6
    }

}