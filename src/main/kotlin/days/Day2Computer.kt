package days

@AdventOfCodePuzzle(
    name = "1202 Program Alarm",
    url = "https://adventofcode.com/2019/day/2",
    date = Date(day = 2, year = 2019)
)
class Day2Computer(private var program: IntArray) : Puzzle {
    override fun partOne() =
        IntCodeComputer(program)
            .apply { withNounAndVerb(12, 2) }
            .run()
            .memory
            .first()

    override fun partTwo() =
        pairsFrom(0..99, 0..99)
            .first { (noun, verb) ->
                IntCodeComputer(program)
                    .apply { withNounAndVerb(noun, verb) }
                    .run()
                    .memory
                    .first() == 19_690_720
            }
            .let { (noun, verb) ->
                100 * noun + verb
            }

    private fun pairsFrom(nounRange: IntRange, verbRange: IntRange) = sequence {
        nounRange.forEach { noun ->
            verbRange.forEach { verb ->
                yield(Pair(noun, verb))
            }
        }
    }
}