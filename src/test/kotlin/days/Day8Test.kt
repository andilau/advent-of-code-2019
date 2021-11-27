package days

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested

@DisplayName("Day 7")
class Day8Test {
    @Nested
    @DisplayName("Part 1")
    inner class Part1 {

        var f = Day8("0222112222120000").partOne()
    }

    @Nested
    @DisplayName("Part 2")
    inner class Part2 {

    }
    companion object {

    }
}