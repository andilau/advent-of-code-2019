package util

import days.Puzzle
import org.reflections.Reflections
import kotlin.math.max
import kotlin.time.ExperimentalTime
import kotlin.time.TimedValue
import kotlin.time.measureTimedValue

@ExperimentalTime
object Runner {
    private val puzzleClasses by lazy {
        Reflections("days").getSubTypesOf(Puzzle::class.java)
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val solutions = puzzleClasses.map { dayNumber(it.simpleName) }.toSet()

        val days: List<Int>? = args
            .map { it.toIntOrNull() ?: error("Day argument must be an integer") }
            .filter { it in solutions || error("No solution for day $it found") }
            .takeIf { it.isNotEmpty() }

        puzzleClasses
            .sortedBy { dayNumber(it.simpleName) }
            .filter { days == null || dayNumber(it.simpleName) in days }
            .takeIf { it.isNotEmpty() }
            ?.forEach { printDay(it) }
            ?: printError("Days $days not found")
    }

    private fun printDay(puzzleClass: Class<out Puzzle>) {
        val dayNumber: Int = dayNumber(puzzleClass.simpleName)
        println("\n--- Day $dayNumber: (${puzzleClass.simpleName}) ---")

        var puzzle: Puzzle?
        try {
            puzzle = puzzleClass.constructors[0].newInstance() as Puzzle
        } catch (e: IllegalArgumentException) {
            val requiredTypeName = puzzleClass.constructors[0].genericParameterTypes[0].typeName
            val constructorWithParameter = puzzleClass.constructors.first { it.parameterCount == 1 } ?: return
            puzzle = when (requiredTypeName) {
                "java.lang.String" ->
                    constructorWithParameter.newInstance(InputReader.getInputAsString(dayNumber)) as Puzzle
                "java.util.List<java.lang.String>" ->
                    constructorWithParameter.newInstance(InputReader.getInputAsList(dayNumber)) as Puzzle
                "java.util.List<java.lang.Integer>" ->
                    constructorWithParameter.newInstance(InputReader.getInputAsListOfInt(dayNumber)) as Puzzle
                "java.util.List<java.lang.Long>" ->
                    constructorWithParameter.newInstance(InputReader.getInputAsListOfLong(dayNumber)) as Puzzle
                "int[]" ->
                    constructorWithParameter.newInstance(InputReader.getInputAsIntArray(dayNumber)) as Puzzle
                else ->
                    throw IllegalStateException("Unhandled Input: $requiredTypeName")
            }
        }
        if (puzzle is Puzzle) {
            val partOne = measureTimedValue { puzzle.partOne() }
            val partTwo = measureTimedValue { puzzle.partTwo() }
            printParts(partOne, partTwo)
        }
    }

    private fun printParts(partOne: TimedValue<Any>, partTwo: TimedValue<Any>) {
        val padding = max(
            partOne.value.toString().length,
            partTwo.value.toString().length
        ) + 14        // 14 is 8 (length of 'Part 1: ') + 6 more
        println("Part 1: ${partOne.value}".padEnd(padding, ' ') + "(${partOne.duration})")
        println("Part 2: ${partTwo.value}".padEnd(padding, ' ') + "(${partTwo.duration})")
    }

    private fun printError(message: String) =
        System.err.println("\n=== ERROR ===\n$message")

    private fun dayNumber(dayClassName: String): Int =
        NUMBER_PATTERN.find(dayClassName)?.value?.toIntOrNull() ?: 0

    private val NUMBER_PATTERN = Regex("""(\d+)""")
}