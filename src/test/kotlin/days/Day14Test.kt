package days

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("Day 14")
class Day14Test {

    @Nested
    @DisplayName("Part 1")
    inner class Part1 {
        @Test
        fun `Example 1 Reactions require 31 ORE for 1 FUEL`() {
            val partOne = Day14(EXAMPLE_1_REQUIRES_31_ORE_FOR_1_FUEL).partOne()
            assertThat(partOne).isEqualTo(31)
        }

        @Test
        fun `Example 2 Reactions require 165 ORE for 1 FUEL`() {
            val partOne = Day14(EXAMPLE_2_REQUIRES_165_ORE_FOR_1_FUEL).partOne()
            assertThat(partOne).isEqualTo(165)
        }

        @Test
        fun `Example 3 Reactions require 13312 ORE for 1 FUEL`() {
            val ore = Day14(EXAMPLE_3_REQUIRES_13312_ORE_FOR_1_FUEL).partOne()
            assertThat(ore).isEqualTo(13312)
        }

        @Test
        fun `Example 4 Reactions require 180697 ORE for 1 FUEL`() {
            val ore = Day14(EXAMPLE_4_REQUIRES_180697_ORE_FOR_1_FUEL).partOne()
            assertThat(ore).isEqualTo(180697)
        }
        @Test
        fun `Example 5 Reactions require 2210736 ORE for 1 FUEL`() {
            val ore = Day14(EXAMPLE_5_REQUIRES_2210736_ORE_FOR_1_FUEL).partOne()
            assertThat(ore).isEqualTo(2210736)
        }
    }

    @Nested
    @DisplayName("Part 2")
    inner class Part2 {

        @Test
        fun `The 13312 ORE-per-FUEL example could produce 82892753 FUEL`() {
            val fuel = Day14(EXAMPLE_3_REQUIRES_13312_ORE_FOR_1_FUEL).partTwo()
            assertThat(fuel).isEqualTo(82892753)
        }

        @Test
        fun `The 180697 ORE-per-FUEL example could produce 5586022 FUEL`() {
            val fuel = Day14(EXAMPLE_4_REQUIRES_180697_ORE_FOR_1_FUEL).partTwo()
            assertThat(fuel).isEqualTo(5586022)
        }

        @Test
        fun `The 2210736 ORE-per-FUEL example could produce 460664 FUEL`() {
            val fuel = Day14(EXAMPLE_5_REQUIRES_2210736_ORE_FOR_1_FUEL).partTwo()
            assertThat(fuel).isEqualTo(460664)
        }
    }

    companion object {
        val EXAMPLE_1_REQUIRES_31_ORE_FOR_1_FUEL = """
            10 ORE => 10 A
            1 ORE => 1 B
            7 A, 1 B => 1 C
            7 A, 1 C => 1 D
            7 A, 1 D => 1 E
            7 A, 1 E => 1 FUEL"""
            .trimIndent().lines()

        val EXAMPLE_2_REQUIRES_165_ORE_FOR_1_FUEL = """
            9 ORE => 2 A
            8 ORE => 3 B
            7 ORE => 5 C
            3 A, 4 B => 1 AB
            5 B, 7 C => 1 BC
            4 C, 1 A => 1 CA
            2 AB, 3 BC, 4 CA => 1 FUEL"""
            .trimIndent().lines()

        val EXAMPLE_3_REQUIRES_13312_ORE_FOR_1_FUEL = """
            157 ORE => 5 NZVS
            165 ORE => 6 DCFZ
            44 XJWVT, 5 KHKGT, 1 QDVJ, 29 NZVS, 9 GPVTF, 48 HKGWZ => 1 FUEL
            12 HKGWZ, 1 GPVTF, 8 PSHF => 9 QDVJ
            179 ORE => 7 PSHF
            177 ORE => 5 HKGWZ
            7 DCFZ, 7 PSHF => 2 XJWVT
            165 ORE => 2 GPVTF
            3 DCFZ, 7 NZVS, 5 HKGWZ, 10 PSHF => 8 KHKGT"""
            .trimIndent().lines()

        val EXAMPLE_4_REQUIRES_180697_ORE_FOR_1_FUEL = """
            2 VPVL, 7 FWMGM, 2 CXFTF, 11 MNCFX => 1 STKFG
            17 NVRVD, 3 JNWZP => 8 VPVL
            53 STKFG, 6 MNCFX, 46 VJHF, 81 HVMC, 68 CXFTF, 25 GNMV => 1 FUEL
            22 VJHF, 37 MNCFX => 5 FWMGM
            139 ORE => 4 NVRVD
            144 ORE => 7 JNWZP
            5 MNCFX, 7 RFSQX, 2 FWMGM, 2 VPVL, 19 CXFTF => 3 HVMC
            5 VJHF, 7 MNCFX, 9 VPVL, 37 CXFTF => 6 GNMV
            145 ORE => 6 MNCFX
            1 NVRVD => 8 CXFTF
            1 VJHF, 6 MNCFX => 4 RFSQX
            176 ORE => 6 VJHF"""
            .trimIndent().lines()

        val EXAMPLE_5_REQUIRES_2210736_ORE_FOR_1_FUEL = """
            171 ORE => 8 CNZTR
            7 ZLQW, 3 BMBT, 9 XCVML, 26 XMNCP, 1 WPTQ, 2 MZWV, 1 RJRHP => 4 PLWSL
            114 ORE => 4 BHXH
            14 VRPVC => 6 BMBT
            6 BHXH, 18 KTJDG, 12 WPTQ, 7 PLWSL, 31 FHTLT, 37 ZDVW => 1 FUEL
            6 WPTQ, 2 BMBT, 8 ZLQW, 18 KTJDG, 1 XMNCP, 6 MZWV, 1 RJRHP => 6 FHTLT
            15 XDBXC, 2 LTCX, 1 VRPVC => 6 ZLQW
            13 WPTQ, 10 LTCX, 3 RJRHP, 14 XMNCP, 2 MZWV, 1 ZLQW => 1 ZDVW
            5 BMBT => 4 WPTQ
            189 ORE => 9 KTJDG
            1 MZWV, 17 XDBXC, 3 XCVML => 2 XMNCP
            12 VRPVC, 27 CNZTR => 2 XDBXC
            15 KTJDG, 12 BHXH => 5 XCVML
            3 BHXH, 2 VRPVC => 7 MZWV
            121 ORE => 7 VRPVC
            7 XCVML => 6 RJRHP
            5 BHXH, 4 VRPVC => 5 LTCX
            """.trimIndent().lines()
    }
}