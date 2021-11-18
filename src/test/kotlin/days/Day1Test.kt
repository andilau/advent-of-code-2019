package days

import days.Day1.Companion.requiredFuel
import days.Day1.Companion.requiredFuelTotal
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*

@DisplayName("Day 1")
class Day1Test {
    @Nested
    @DisplayName("Part 1")
    inner class Part1 {

        @TestFactory
        fun testFuelRequiredForMass() = listOf(
            12 to 2,
            14 to 2,
            1969 to 654,
            100756 to 33583
        ).map { (mass, fuel) ->
            DynamicTest.dynamicTest("Fuel required for $mass should be $fuel") {
                assertThat(mass.requiredFuel()).isEqualTo(fuel)
            }
        }
    }

    @Nested
    @DisplayName("Part 2")
    inner class Part2 {
        private val massToFuel = listOf(
            12 to 2,
            14 to 2,
            1969 to 966,
            100756 to 50346
        )

        @TestFactory
        @DisplayName("Fuel Fuel Rec")
        fun testFuelRequiredForMassAndFuel(): List<DynamicTest> {
            return massToFuel.map { (mass, fuel) ->
                DynamicTest.dynamicTest("Fuel required for $mass should be $fuel") {
                    assertThat(mass.requiredFuelTotal()).isEqualTo(fuel)
                }
            }
        }
    }
}