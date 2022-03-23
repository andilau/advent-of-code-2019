package days

import kotlin.math.max

@AdventOfCodePuzzle(
    name = "Space Stoichiometry",
    url = "https://adventofcode.com/2019/day/14",
    date = Date(day = 14, year = 2019)
)
class Day14(input: List<String>) : Puzzle {
    private val reactions: Map<String, Pair<Long, List<Quantity>>> = input.parseReactions()

    override fun partOne(): Long =
        calculateOreFor("FUEL", 1L)

    override fun partTwo(): Long =
        calculateMaximumFuelWith(one_trillion) { fuel -> calculateOreFor(amount = fuel) }

    private fun calculateOreFor(
        chemical: String = "FUEL",
        amount: Long = 1,
        inventory: MutableMap<String, Long> = mutableMapOf()
    ): Long {
        if (chemical == "ORE") return amount // base case

        val inventoryAmount = inventory.getOrDefault(chemical, 0)
        if (inventoryAmount > 0) {  // got it or some
            inventory[chemical] = max(inventoryAmount - amount, 0)
        }

        val neededAmount = amount - inventoryAmount
        if (neededAmount > 0) {
            val formula = reactions[chemical] ?: error("Cant produce chemical: $chemical")
            val producesAmount = formula.first
            val recipe = formula.second

            val factor = ceil(neededAmount, producesAmount)
            val producesWithExcess = producesAmount * factor
            val excess = producesWithExcess - neededAmount
            if (excess > 0) {
                inventory.merge(chemical, excess) { a, b -> a + b }
            }

            return recipe.sumOf { inputQuantity ->
                calculateOreFor(
                    inputQuantity.chemical,
                    inputQuantity.amount * factor,
                    inventory
                )
            }
        }
        return 0
    }

    private fun calculateMaximumFuelWith(maxQuantityOre: Long, function: (Long) -> Long): Long {
        var low = 1L
        var high = maxQuantityOre
        while (low + 1 < high) {
            val guess = (low + high) / 2
            val ore = function(guess)
            if (ore > maxQuantityOre)
                high = guess
            else
                low = guess
        }
        return low
    }

    private fun ceil(a: Long, b: Long) = (a + b - 1) / b

    private fun List<String>.parseReactions() =
        associate { line ->
            val from = line.substringAfter("=>").let { Quantity.from(it) }
            val receipt = line
                .substringBefore("=>")
                .split(',')
                .map { Quantity.from(it) }
            from.chemical to Pair(from.amount, receipt)
        }


    data class Quantity(val chemical: String, val amount: Long) {

        companion object {
            private val REGEX_QUANTITY_SYMBOL = Regex("""\s*(\d+)\s+(\w+)\s*""")

            fun from(string: String): Quantity {
                return REGEX_QUANTITY_SYMBOL.matchEntire(string)?.let {
                    val (quantity, symbol) = it.destructured
                    Quantity(symbol, quantity.toLong())
                }
                    ?: throw IllegalArgumentException("Unable to parse $string into Quantity")
            }
        }
    }

    companion object {
        private const val one_trillion = 1_000_000_000_000
    }
}