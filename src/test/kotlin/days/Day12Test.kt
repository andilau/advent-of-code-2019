package days

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("Day 12")
class Day12Test {

    @Nested
    @DisplayName("Part 1")
    inner class Part1 {
        @Test
        fun `Simulating the motion of these moons would produce the following after 0 steps`() {
            val actual = Day12(EXAMPLE1).stepAndMoonsAsString(0)
            val expected = EXAMPLE1_AFTER_0_STEPS
            assertThat(actual).isEqualTo(expected)
        }
        @Test
        fun `Simulating the motion of these moons would produce the following after 1 step`() {
            val actual = Day12(EXAMPLE1).stepAndMoonsAsString(1)
            val expected = EXAMPLE1_AFTER_1_STEP
            assertThat(actual).isEqualTo(expected)
        }
        @Test
        fun `Simulating the motion of these moons would produce the following after 2 step`() {
            val actual = Day12(EXAMPLE1).stepAndMoonsAsString(2)
            val expected = EXAMPLE1_AFTER_2_STEPS
            assertThat(actual).isEqualTo(expected)
        }
    }

    @Nested
    @DisplayName("Part 2")
    inner class Part2 {
        @Test
        fun `EXAMPLE1 of initial positions takes 2772 steps before it repeats a previous state`() {
            val partTwo = Day12(EXAMPLE1).partTwo()
            assertThat(partTwo).isEqualTo(2772)
        }

        @Test
        fun `EXAMPLE2 of initial positions takes 4686774924 steps before it repeats a previous state`() {
            val partTwo = Day12(EXAMPLE2).partTwo()
            assertThat(partTwo).isEqualTo(4686774924)
        }
    }

    companion object {
        val EXAMPLE1 = """
            <x=-1, y=0, z=2>
            <x=2, y=-10, z=-7>
            <x=4, y=-8, z=8>
            <x=3, y=5, z=-1>""".trimIndent().lines()

        val EXAMPLE1_AFTER_0_STEPS = """
            pos=<x=-1, y=  0, z= 2>, vel=<x= 0, y= 0, z= 0>
            pos=<x= 2, y=-10, z=-7>, vel=<x= 0, y= 0, z= 0>
            pos=<x= 4, y= -8, z= 8>, vel=<x= 0, y= 0, z= 0>
            pos=<x= 3, y=  5, z=-1>, vel=<x= 0, y= 0, z= 0>
            """.trimIndent().filterNot { it == ' ' }
        val EXAMPLE1_AFTER_1_STEP = """
            pos=<x= 2, y=-1, z= 1>, vel=<x= 3, y=-1, z=-1>
            pos=<x= 3, y=-7, z=-4>, vel=<x= 1, y= 3, z= 3>
            pos=<x= 1, y=-7, z= 5>, vel=<x=-3, y= 1, z=-3>
            pos=<x= 2, y= 2, z= 0>, vel=<x=-1, y=-3, z= 1>
            """.trimIndent().filterNot { it == ' ' }
        val EXAMPLE1_AFTER_2_STEPS = """
            pos=<x= 5, y=-3, z=-1>, vel=<x= 3, y=-2, z=-2>
            pos=<x= 1, y=-2, z= 2>, vel=<x=-2, y= 5, z= 6>
            pos=<x= 1, y=-4, z=-1>, vel=<x= 0, y= 3, z=-6>
            pos=<x= 1, y=-4, z= 2>, vel=<x=-1, y=-6, z= 2>
            """.trimIndent().filterNot { it == ' ' }

        val EXAMPLE2 = """
            <x=-8, y=-10, z=0>
            <x=5, y=5, z=10>
            <x=2, y=-7, z=3>
            <x=9, y=-8, z=-3>""".trimIndent().lines()
    }
}