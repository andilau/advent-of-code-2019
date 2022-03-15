package days

@AdventOfCodePuzzle(
    name = "Space Image Format",
    url = "https://adventofcode.com/2019/day/8",
    date = Date(day = 8, year = 2019)
)
class Day8(input: String) : Puzzle {
    private val layers = input.chunked(WIDE * TALL)

    override fun partOne() =
        layers
            .minByOrNull { layer -> layer.count { it == BLACK } }
            ?.let { layer -> layer.count { it == WHITE } * layer.count { it == TRANSPARENT } }
            ?: 0

    override fun partTwo() =
        layers.first().indices
            .map { at ->
                layers
                    .map { layer -> layer[at] }
                    .firstOrNull { pixel -> pixel != TRANSPARENT }
            }
            .chunked(WIDE)
            .forEach {
                println(it.joinToString("") { if (it == '1') "#" else " " })
            }


    companion object {
        var WIDE = 25
        var TALL = 6
        const val BLACK = '0'
        const val WHITE = '1'
        const val TRANSPARENT = '2'
    }

}