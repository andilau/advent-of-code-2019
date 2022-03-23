# Advent of Code 2019

This project contains solutions to the [Advent of Code 2019](https://adventofcode.com/2019) challenge in [Kotlin](https://kotlinlang.org/). 
Advent of Code is an Advent calendar of small programming puzzles by [Eric Wastl](http://was.tl/).

### Solutions

- Day 1: [The Tyranny of the Rocket Equation](https://adventofcode.com/2019/day/1), [Day1.kt](https://github.com/andilau/advent-of-code-2019/blob/main/src/main/kotlin/days/Day1.kt)
- Day 2: [1202 Program Alarm](https://adventofcode.com/2019/day/2), [Day2.kt](https://github.com/andilau/advent-of-code-2019/blob/main/src/main/kotlin/days/Day2.kt), [Day2Computer.kt](https://github.com/andilau/advent-of-code-2019/blob/main/src/main/kotlin/days/Day2CodeComputer.kt), [IntCodeComputer.kt](https://github.com/andilau/advent-of-code-2019/blob/main/src/main/kotlin/days/IntCodeComputer.kt)
- Day 3: [Crossed Wires](https://adventofcode.com/2019/day/3), [Day3.kt](https://github.com/andilau/advent-of-code-2019/blob/main/src/main/kotlin/days/Day3.kt)
- Day 4: [Secure Container](https://adventofcode.com/2019/day/4), [Day4.kt](https://github.com/andilau/advent-of-code-2019/blob/main/src/main/kotlin/days/Day4.kt), [Day4Faster.kt](https://github.com/andilau/advent-of-code-2019/blob/main/src/main/kotlin/days/Day4Faster.kt)
- Day 5: [Sunny with a Chance of Asteroids](https://adventofcode.com/2019/day/5), [Day5.kt](https://github.com/andilau/advent-of-code-2019/blob/main/src/main/kotlin/days/Day5.kt), [IntCodeComputer.kt](https://github.com/andilau/advent-of-code-2019/blob/main/src/main/kotlin/days/IntCodeComputer.kt)
- Day 6: [Universal Orbit Map](https://adventofcode.com/2019/day/6), [Day6.kt](https://github.com/andilau/advent-of-code-2019/blob/main/src/main/kotlin/days/Day6.kt)
- Day 7: [Amplification Circuit](https://adventofcode.com/2019/day/7), [Day7.kt](https://github.com/andilau/advent-of-code-2019/blob/main/src/main/kotlin/days/Day7.kt), [IntCodeComputer.kt](https://github.com/andilau/advent-of-code-2019/blob/main/src/main/kotlin/days/IntCodeComputer.kt)
- Day 8: [Space Image Format](https://adventofcode.com/2019/day/8), [Day8.kt](https://github.com/andilau/advent-of-code-2019/blob/main/src/main/kotlin/days/Day8.kt)
- Day 9: [Sensor Boost](https://adventofcode.com/2019/day/9), [Day9.kt](https://github.com/andilau/advent-of-code-2019/blob/main/src/main/kotlin/days/Day9.kt)
- Day 10: [Monitoring Station](https://adventofcode.com/2019/day/10), [Day10.kt](https://github.com/andilau/advent-of-code-2019/blob/main/src/main/kotlin/days/Day10.kt)
- Day 11: [Space Police](https://adventofcode.com/2019/day/11), [Day11.kt](https://github.com/andilau/advent-of-code-2019/blob/main/src/main/kotlin/days/Day11.kt)
- Day 12: [The N-Body Problem](https://adventofcode.com/2019/day/12), [Day12.kt](https://github.com/andilau/advent-of-code-2019/blob/main/src/main/kotlin/days/Day12.kt)
- Day 13: [Care Package](https://adventofcode.com/2019/day/13), [Day13.kt](https://github.com/andilau/advent-of-code-2019/blob/main/src/main/kotlin/days/Day13.kt)
- Day 14: [Space Stoichiometry](https://adventofcode.com/2019/day/14), [Day14.kt](https://github.com/andilau/advent-of-code-2019/blob/main/src/main/kotlin/days/Day14.kt)

### Features

* Gradle setup so you can run a specific day or all days on the command line (see **Running**)
* Timings for each part of each day
* Input for each day automatically exposed in String and List form
* [Junit 5](https://junit.org/junit5/) and [AssertJ](https://assertj.github.io/doc/) test libraries included (see **Testing**)
* Starter .gitignore

### Running

Project is already setup with gradle. To run the app:

* Navigate to top-level directory on the command line
* Run `./gradlew run` to run all days
* Run `./gradlew run --args $DAY` where `$DAY` is an integer to run a specific day
* Run `./gradlew run --args "$DAY1 $DAY2 $ANOTHERDAY"` to run a subset of days

### Testing

Project includes JUnit and AssertJ and a stub unit test to get you going. To run all tests:

* Navigate to top-level directory on the command line
* Run `./gradlew test`
* Add `--info`, `--debug` or `--stacktrace` flags for more output

By default, instantiations of `Day` classes in tests will use the input files in `src/test/resources`, _not_ those
in `src/main/resources`. This hopefully gives you flexibility - you could either just copy the real input
into `src/test/resources` if you want to test the actual answers, or you could add a file of test data based on the
examples given on the Advent of Code description for the day. The stub `Day1Test` class shows a test of the
functionality of `Day1` where the test input differs from the actual input.

### Architecture

* Inputs go into `src/main/resources` and follow the naming convention `input_day_X.txt`
* Solutions go into `src/main/kotlin/days` and implement the `Puzzle` interface
* Solutions follow the naming convention `DayX`
* It is assumed all solutions will have two parts
* It is assumed that the puzzle input is provided through the primary constructor
* You can use the `InputReader` methods for reading input
* To get started simply replace `src/main/resources/input_day_1.txt` with the real input and the solutions in `Day1` with your own