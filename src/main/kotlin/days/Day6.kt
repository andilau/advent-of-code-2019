package days

@AdventOfCodePuzzle(
    name = "Universal Orbit Map",
    url = "https://adventofcode.com/2019/day/6",
    date = Date(day = 6, year = 2019)
)
class Day6(input: List<String>) : Puzzle {
    private val orbitsAround = input
        .associate { line ->
            line.split(")").let {
                it.last() to it.first()
            }
        }

    override fun partOne() =
        orbitsAround.keys.sumOf { it.pathToCenter().count() }

    override fun partTwo(): Int {
        val yourPath = "YOU".pathToCenter().toList()
        val santaPath = "SAN".pathToCenter().toList()
        val crossing = (yourPath intersect santaPath.toSet()).first()
        return yourPath.indexOf(crossing) + santaPath.indexOf(crossing)
    }

    private fun String.pathToCenter() =
        generateSequence(this) { planet -> orbitsAround[planet] }.drop(1)
}