package days

@AdventOfCodePuzzle(
    name = "The Tyranny of the Rocket Equation",
    url = "https://adventofcode.com/2019/day/1",
    date = Date(day = 1, year = 2019)
)
class Day1(private val masses: List<Int>) : Puzzle {
    override fun partOne() = masses.sumOf { it.requiredFuel() }

    override fun partTwo() = masses.sumOf { it.requiredFuelTotal() }

    companion object {
        fun Int.requiredFuel() = this / 3 - 2

        fun Int.requiredFuelTotal(): Int {
            if (this <= 8) return 0
            val fuel = this.requiredFuel()
            return fuel + fuel.requiredFuelTotal()
        }
    }
}