package days

import days.Day10.Asteroid
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*

@DisplayName("Day 10")
class Day10Test {

    @Nested
    @DisplayName("Part 1")
    inner class Part1 {

        @TestFactory
        internal fun `Base Should Detect Exact Number Of Asteroids`() =
            listOf(
                BASE_AT_3_4_DETECTS_8_ASTEROIDS to 8,
                BASE_AT_5_8_DETECTS_33_ASTEROIDS to 33,
                BASE_AT_1_2_DETECTS_35_ASTEROIDS to 35,
                BASE_AT_6_3_DETECTS_41_ASTEROIDS to 41,
                BASE_AT_11_13_DETECTS_210_ASTEROIDS to 210
            )
                .mapIndexed() { i, (input, expected) ->
                    DynamicTest.dynamicTest("Example $i should detect $expected asteroids") {
                        val actual = Day10(input.lines()).partOne()
                        assertThat(actual).isEqualTo(expected)
                    }
                }
    }

    @Nested
    @DisplayName("Part 2")
    inner class Part2 {

        @Test
        fun `Example 1`() {
            val base = Day10(BASE_AT_3_4_DETECTS_8_ASTEROIDS.lines()).findOptimalBase()
            assertThat(base).isEqualTo(Asteroid(3, 4))
        }

        @Test
        fun `Example 3`() {
            val actual = Day10(BASE_AT_11_13_DETECTS_210_ASTEROIDS.lines()).partTwo()
            assertThat(actual).isEqualTo(802)
            val base = Day10(BASE_AT_11_13_DETECTS_210_ASTEROIDS.lines()).findOptimalBase()
            assertThat(base).isEqualTo(Asteroid(11, 13))
        }

        @Test
        fun `Test for order of asteroid targets`() {
            val day10 = Day10(BASE_AT_11_13_DETECTS_210_ASTEROIDS.lines())
            val base = day10.findOptimalBase()
            assertThat(base).isEqualTo(Asteroid(11, 13))
            val actual = day10.targetsAsteroids(base).toList()
            assertThat(actual).isNotEmpty
            assertThat(actual).element(0).isEqualTo(Asteroid(11, 12))
            assertThat(actual).element(1).isEqualTo(Asteroid(12, 1))
            assertThat(actual).element(2).isEqualTo(Asteroid(12, 2))
            assertThat(actual).element(9).isEqualTo(Asteroid(12, 8))
            assertThat(actual).element(19).isEqualTo(Asteroid(16, 0))
            assertThat(actual).element(49).isEqualTo(Asteroid(16, 9))
            assertThat(actual).element(99).isEqualTo(Asteroid(10, 16))
            assertThat(actual).element(198).isEqualTo(Asteroid(9, 6))
            assertThat(actual).element(199).isEqualTo(Asteroid(8, 2))
            assertThat(actual).element(200).isEqualTo(Asteroid(10, 9))
            assertThat(actual).element(298).isEqualTo(Asteroid(11, 1))
        }
    }

    companion object {
        val BASE_AT_3_4_DETECTS_8_ASTEROIDS = """
            .#..#
            .....
            #####
            ....#
            ...##""".trimIndent()

        val BASE_AT_5_8_DETECTS_33_ASTEROIDS = """
            ......#.#.
            #..#.#....
            ..#######.
            .#.#.###..
            .#..#.....
            ..#....#.#
            #..#....#.
            .##.#..###
            ##...#..#.
            .#....####""".trimIndent()

        val BASE_AT_1_2_DETECTS_35_ASTEROIDS = """
            #.#...#.#.
            .###....#.
            .#....#...
            ##.#.#.#.#
            ....#.#.#.
            .##..###.#
            ..#...##..
            ..##....##
            ......#...
            .####.###.""".trimIndent()

        val BASE_AT_6_3_DETECTS_41_ASTEROIDS = """
            .#..#..###
            ####.###.#
            ....###.#.
            ..###.##.#
            ##.##.#.#.
            ....###..#
            ..#.#..#.#
            #..#.#.###
            .##...##.#
            .....#.#..""".trimIndent()

        val BASE_AT_11_13_DETECTS_210_ASTEROIDS = """
            .#..##.###...#######
            ##.############..##.
            .#.######.########.#
            .###.#######.####.#.
            #####.##.#.##.###.##
            ..#####..#.#########
            ####################
            #.####....###.#.#.##
            ##.#################
            #####.##.###..####..
            ..######..##.#######
            ####.##.####...##..#
            .#####..#.######.###
            ##...#.##########...
            #.##########.#######
            .####.#.###.###.#.##
            ....##.##.###..#####
            .#.#.###########.###
            #.#.#.#####.####.###
            ###.##.####.##.#..##
            """.trimIndent()
    }
}