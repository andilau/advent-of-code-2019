package days

@AdventOfCodePuzzle(
    name = "Space Image Format",
    url = "https://adventofcode.com/2019/day/8",
    date = Date(day = 8, year = 2019)
)
class Day8(val input: String) : Puzzle {
    internal var wide = 25
    internal var tall = 6

    override fun partOne() =
        input.chunked(wide * tall)
            .minByOrNull { layer -> layer.count { it == BLACK } }
            ?.let { layer -> layer.count { it == WHITE } * layer.count { it == TRANSPARENT } }
            ?: 0

    override fun partTwo() =
        with(input.chunked(wide * tall)) {
            first().indices
                .map { at ->
                    this
                        .map { layer -> layer[at] }
                        .firstOrNull { pixel -> pixel != TRANSPARENT }
                }
                .chunked(wide)
                .forEach {
                    println(it.joinToString("") { if (it == '1') "#" else " " })
                }
        }

    companion object {
        const val BLACK = '0'
        const val WHITE = '1'
        const val TRANSPARENT = '2'
    }
}