package days

interface Puzzle {
    fun partOne(): Any
    fun partTwo(): Any
}

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class AdventOfCodePuzzle(val url: String, val name: String, val date: Date)

@Target(AnnotationTarget.ANNOTATION_CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Date(val day: Int, val month: Int = 12, val year: Int)