package days

import days.IntCodeComputer.ParameterMode.Companion.mapIntToMode
import days.IntCodeComputer.ParameterMode.IMMEDIATE
import days.IntCodeComputer.ParameterMode.POSITION

class IntCodeComputer(program: IntArray) {
    private val io = InputOutput(0, mutableListOf())
    internal var memory = program.clone()

    var input by io::input
    val output: List<Int>
        get() = io.output.toList()

    fun withNounAndVerb(noun: Int, verb: Int) =
        if (memory.size >= 3) {
            memory[1] = noun
            memory[2] = verb
        }
        else throw IllegalArgumentException("cannot apply noun & verb, memory size: ${memory.size}")

    fun run(): IntCodeComputer {
        var ip = 0
        do {
            val instruction = Instruction.from(memory[ip].opcode())
            ip += instruction.execute(memory, ip, io)

        } while (instruction !is Instruction.Halt)
        return this
    }

    sealed class Instruction(val offset: Int) {
        fun IntArray.getValue(at: Int, mode: ParameterMode) = when (mode) {
            POSITION -> this[this[at]]
            IMMEDIATE -> this[at]
        }

        abstract fun execute(program: IntArray, ip: Int, io: InputOutput): Int

        // Opcode 1 adds together numbers read from two positions and stores the result in a third position.
        // The three integers immediately after the opcode tell you these three positions
        object Add : Instruction(4) {
            override fun execute(program: IntArray, ip: Int, io: InputOutput): Int {
                program[program[ip + 3]] =
                    program.getValue(ip + 1, program[ip].parameter1Mode()) +
                            program.getValue(ip + 2, program[ip].parameter2Mode())
                return offset
            }
        }

        // Opcode 2 works exactly like opcode 1, except it multiplies the two inputs instead of adding them.
        object Multiply : Instruction(4) {
            override fun execute(program: IntArray, ip: Int, io: InputOutput): Int {
                program[program[ip + 3]] =
                    program.getValue(ip + 1, program[ip].parameter1Mode()) *
                            program.getValue(ip + 2, program[ip].parameter2Mode())
                return offset
            }
        }

        // Opcode 3 is input: it takes a single integer as input and
        // saves it to the position given by its only parameter.
        object Input : Instruction(2) {
            override fun execute(program: IntArray, ip: Int, io: InputOutput): Int {
                program[program[ip + 1]] = io.input
                return offset
            }
        }

        // Opcode 4 is output: it outputs the value of its only parameter.
        object Output : Instruction(2) {
            override fun execute(program: IntArray, ip: Int, io: InputOutput): Int {
                val value = program.getValue(ip + 1, program[ip].parameter1Mode())
                io.output.add(value)
                return offset
            }
        }

        // Opcode 5 is jump-if-true: if the first parameter is non-zero,
        // it sets the instruction pointer to the value from the second parameter.
        // Otherwise, it does nothing.
        object JumpTrue : IntCodeComputer.Instruction(3) {
            override fun execute(program: IntArray, ip: Int, io: InputOutput): Int {
                val p1 = program.getValue(ip + 1, program[ip].parameter1Mode())
                val p2 = program.getValue(ip + 2, program[ip].parameter2Mode())
                return if (p1 != 0) p2 - ip
                else offset
            }
        }

        // Opcode 6 is jump-if-false: if the first parameter is zero,
        // it sets the instruction pointer to the value from the second parameter.
        // Otherwise, it does nothing.
        object JumpFalse : IntCodeComputer.Instruction(3) {
            override fun execute(program: IntArray, ip: Int, io: InputOutput): Int {
                val p1 = program.getValue(ip + 1, program[ip].parameter1Mode())
                val p2 = program.getValue(ip + 2, program[ip].parameter2Mode())
                return if (p1 == 0) p2 - ip
                else offset
            }
        }

        // Opcode 7 is less than: if the first parameter is less than the second parameter,
        // it stores 1 in the position given by the third parameter.
        // Otherwise, it stores 0.
        object LessThan : IntCodeComputer.Instruction(4) {
            override fun execute(program: IntArray, ip: Int, io: InputOutput): Int {
                val p1 = program.getValue(ip + 1, program[ip].parameter1Mode())
                val p2 = program.getValue(ip + 2, program[ip].parameter2Mode())
                program[program[ip + 3]] = if (p1 < p2) 1 else 0
                return offset
            }
        }

        // Opcode 8 is equals: if the first parameter is equal to the second parameter,
        // it stores 1 in the position given by the third parameter.
        // Otherwise, it stores 0.
        object Equals : IntCodeComputer.Instruction(4) {
            override fun execute(program: IntArray, ip: Int, io: InputOutput): Int {
                val p1 = program.getValue(ip + 1, program[ip].parameter1Mode())
                val p2 = program.getValue(ip + 2, program[ip].parameter2Mode())
                program[program[ip + 3]] = if (p1 == p2) 1 else 0
                return offset
            }
        }

        // Opcode 99 is halt: the program is finished and should immediately halt
        object Halt : Instruction(1) {
            override fun execute(program: IntArray, ip: Int, io: InputOutput): Int {
                return 0
            }
        }

        companion object {
            fun from(opcode: Int): Instruction {
                return when (opcode) {
                    1 -> Add
                    2 -> Multiply
                    3 -> Input
                    4 -> Output
                    5 -> JumpTrue
                    6 -> JumpFalse
                    7 -> LessThan
                    8 -> Equals
                    99 -> Halt
                    else -> throw IllegalArgumentException("Unknown operation: $opcode")
                }
            }
        }
    }

    enum class ParameterMode(val code: Int) {
        POSITION(0),
        IMMEDIATE(1);

        companion object {
            fun fromInt(number: Int) = when (number) {
                POSITION.code -> POSITION
                IMMEDIATE.code -> IMMEDIATE
                else -> throw IllegalArgumentException("Unknown mode for parameter: $number")
            }

            inline fun mapIntToMode(number: Int, map: (Int) -> Int) =
                fromInt(map.invoke(number))
        }
    }

    data class InputOutput(var input: Int, val output: MutableList<Int>)

    val memoryAsString
        get() = memory.joinToString(",")
}

fun Int.opcode() = mod(100)
fun Int.parameter1Mode() = mapIntToMode(this) { (it % 1000 - it % 100) / 100 }
fun Int.parameter2Mode() = mapIntToMode(this) { (it % 10000 - it % 1000) / 1000 }
fun Int.parameter3Mode() = mapIntToMode(this) { (it % 100000 - it % 10000) / 10000 }