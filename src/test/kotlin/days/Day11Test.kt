package days

import days.Day11.PaintRobot
import days.Day11.PaintRobot.PanelColor.BLACK
import days.Point.Companion.ORIGIN
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("Day 11")
class Day11Test {

    @Test
    fun `With program the paint robot should touch 2373 panels`() {
        assertThat(Day11(program).partOne()).isEqualTo(2373)
    }

    @Test
    fun `Should print to console`() {
        assertThat(Day11(program).partTwo()).isEqualTo(Unit)
    }

    @Nested
    @DisplayName("Paint Robot")
    inner class Robot {
        @Test
        fun `Paint Robot should start at Origin facing North`() {
            val paintRobot = PaintRobot(CompleteIntCodeComputer(program.toLongNumbers()))
            assertThat(paintRobot).hasFieldOrPropertyWithValue("position", ORIGIN)
            assertThat(paintRobot).hasFieldOrPropertyWithValue("direction", Direction.NORTH)
        }

        @Test
        fun `Paint Robot should do a step and move left`() {
            val paintRobot = PaintRobot(CompleteIntCodeComputer(program.toLongNumbers()))
            val nextColor = paintRobot.step(BLACK)
            assertThat(nextColor).isEqualTo(PaintRobot.PanelColor.WHITE)
            assertThat(paintRobot).hasFieldOrPropertyWithValue("position", ORIGIN.left())
            assertThat(paintRobot).hasFieldOrPropertyWithValue("direction", Direction.WEST)
        }

        @Test
        fun `Paint Robot should do another step and move down`() {
            val paintRobot = PaintRobot(CompleteIntCodeComputer(program.toLongNumbers()))
            paintRobot.step(BLACK)
            val nextColor = paintRobot.step(BLACK)
            assertThat(nextColor).isEqualTo(PaintRobot.PanelColor.WHITE)
            assertThat(paintRobot).hasFieldOrPropertyWithValue("position", ORIGIN.left().down())
            assertThat(paintRobot).hasFieldOrPropertyWithValue("direction", Direction.SOUTH)
        }
    }

    private val program =
        """3,8,1005,8,332,1106,0,11,0,0,0,104,1,104,0,3,8,102,-1,8,10,101,1,10,10,4,10,108,1,8,10,4,10,101,0,8,28,3,8,102,-1,8,10,1001,10,1,10,4,10,1008,8,1,10,4,10,101,0,8,51,1,1103,5,10,1,1104,9,10,2,1003,0,10,1,5,16,10,3,8,102,-1,8,10,101,1,10,10,4,10,108,0,8,10,4,10,1001,8,0,88,1006,0,2,1006,0,62,2,8,2,10,3,8,1002,8,-1,10,101,1,10,10,4,10,1008,8,1,10,4,10,102,1,8,121,1006,0,91,1006,0,22,1006,0,23,1006,0,1,3,8,102,-1,8,10,1001,10,1,10,4,10,1008,8,1,10,4,10,101,0,8,155,1006,0,97,1,1004,2,10,2,1003,6,10,3,8,1002,8,-1,10,101,1,10,10,4,10,108,0,8,10,4,10,1002,8,1,187,1,104,15,10,2,107,9,10,1006,0,37,1006,0,39,3,8,1002,8,-1,10,1001,10,1,10,4,10,108,0,8,10,4,10,102,1,8,223,2,2,17,10,1,1102,5,10,3,8,1002,8,-1,10,101,1,10,10,4,10,108,0,8,10,4,10,1001,8,0,253,3,8,102,-1,8,10,1001,10,1,10,4,10,1008,8,1,10,4,10,1002,8,1,276,1006,0,84,3,8,102,-1,8,10,101,1,10,10,4,10,1008,8,0,10,4,10,1001,8,0,301,2,1009,9,10,1006,0,10,2,102,15,10,101,1,9,9,1007,9,997,10,1005,10,15,99,109,654,104,0,104,1,21102,1,936995738516,1,21101,0,349,0,1105,1,453,21102,1,825595015976,1,21102,1,360,0,1105,1,453,3,10,104,0,104,1,3,10,104,0,104,0,3,10,104,0,104,1,3,10,104,0,104,1,3,10,104,0,104,0,3,10,104,0,104,1,21102,46375541763,1,1,21101,0,407,0,1105,1,453,21102,1,179339005019,1,21101,0,418,0,1106,0,453,3,10,104,0,104,0,3,10,104,0,104,0,21102,825012036372,1,1,21102,441,1,0,1105,1,453,21101,988648461076,0,1,21101,452,0,0,1105,1,453,99,109,2,22102,1,-1,1,21102,40,1,2,21102,484,1,3,21101,0,474,0,1106,0,517,109,-2,2105,1,0,0,1,0,0,1,109,2,3,10,204,-1,1001,479,480,495,4,0,1001,479,1,479,108,4,479,10,1006,10,511,1102,1,0,479,109,-2,2105,1,0,0,109,4,2102,1,-1,516,1207,-3,0,10,1006,10,534,21101,0,0,-3,21202,-3,1,1,22101,0,-2,2,21102,1,1,3,21102,553,1,0,1106,0,558,109,-4,2106,0,0,109,5,1207,-3,1,10,1006,10,581,2207,-4,-2,10,1006,10,581,22102,1,-4,-4,1105,1,649,21202,-4,1,1,21201,-3,-1,2,21202,-2,2,3,21101,0,600,0,1105,1,558,21201,1,0,-4,21101,0,1,-1,2207,-4,-2,10,1006,10,619,21101,0,0,-1,22202,-2,-1,-2,2107,0,-3,10,1006,10,641,22102,1,-1,1,21102,1,641,0,106,0,516,21202,-2,-1,-2,22201,-4,-2,-4,109,-5,2105,1,0"""
}