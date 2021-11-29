package days

import days.CompleteIntCodeComputer.Instruction.*
import days.CompleteIntCodeComputer.Instruction.Output.opcode
import days.CompleteIntCodeComputer.Instruction.ParameterMode.Companion.mapLongToMode
import java.util.*

class CompleteIntCodeComputer(memory: LongArray) {
    private val state = State(0L, 0L, LinkedList(), LinkedList())

    private val memory: MutableMap<Long, Long> = memory
        .withIndex()
        .associate { it.index.toLong() to it.value }
        .toMutableMap()

    val halted: Boolean
        get() = Instruction.from(memory.opcode(state.ip)) is Halt

    val memoryAsString: String
        get() = (0..memory.keys.maxOf { it })
            .map { addr -> memory.getOrDefault(addr, 0L) }
            .joinToString(",")

    var input: Long
        set(input) {
            state.input.offer(input)
        }
        get() = state.input.peek()

    val output: Long
        get() = state.output.poll() ?: error("No output.")

    fun run(blocking: Boolean = false): CompleteIntCodeComputer {
        do {
            val instruction = Instruction.from(memory.getValue(state.ip).opcode())

            if (instruction is Input && state.input.isEmpty()) break
            instruction.execute(memory, state)
            if (blocking && instruction is Output) break
        } while (instruction !is Halt)
        return this
    }

    sealed class Instruction(val offset: Int) {
        companion object {
            fun from(opcode: Int): Instruction = when (opcode) {
                1 -> Add
                2 -> Multiply
                3 -> Input
                4 -> Output
                5 -> JumpTrue
                6 -> JumpFalse
                7 -> LessThan
                8 -> Equals
                9 -> SetBase
                99 -> Halt
                else -> throw IllegalArgumentException("Unknown operation: $opcode")
            }
        }

        abstract fun execute(memory: MutableMap<Long, Long>, io: State)

        // Opcode 1 adds together numbers read from two positions and stores the result in a third position.
        // The three integers immediately after the opcode tell you these three positions
        object Add : Instruction(4) {
            override fun execute(memory: MutableMap<Long, Long>, io: State) {
                memory[memory.setValue(io.ip, 3, io.base)] =
                    memory.getValue(io.ip + 1, memory.parameterMode(io.ip, 1), io.base) +
                            memory.getValue(io.ip + 2, memory.parameterMode(io.ip, 2), io.base)
                io.ip += offset
            }
        }

        // Opcode 2 works exactly like opcode 1, except it multiplies the two inputs instead of adding them.
        object Multiply : Instruction(4) {
            override fun execute(memory: MutableMap<Long, Long>, io: State) {
                memory[memory.setValue(io.ip, 3, io.base)] =
                    memory.getValue(io.ip + 1, memory.parameterMode(io.ip, 1), io.base) *
                            memory.getValue(io.ip + 2, memory.parameterMode(io.ip, 2), io.base)
                io.ip += offset
            }
        }

        // Opcode 3 is input: it takes a single integer as input and
        // saves it to the position given by its only parameter.
        object Input : Instruction(2) {
            override fun execute(memory: MutableMap<Long, Long>, io: State) {
                memory[memory.setValue(io.ip, 1, io.base)] = io.input.remove()
                io.ip += offset
            }
        }

        // Opcode 4 is output: it outputs the value of its only parameter.
        object Output : Instruction(2) {
            override fun execute(memory: MutableMap<Long, Long>, io: State) {
                val value = memory.getValue(io.ip + 1, memory.parameterMode(io.ip, 1), io.base)
                io.output.offer(value)
                io.ip += offset
            }
        }

        // Opcode 5 is jump-if-true: if the first parameter is non-zero,
        // it sets the instruction pointer to the value from the second parameter.
        // Otherwise, it does nothing.
        object JumpTrue : Instruction(3) {
            override fun execute(memory: MutableMap<Long, Long>, io: State) {
                val p1 = memory.getValue(io.ip + 1, memory.parameterMode(io.ip, 1), io.base)
                val p2 = memory.getValue(io.ip + 2, memory.parameterMode(io.ip, 2), io.base)
                if (p1 != 0L)
                    io.ip = p2
                else io.ip += offset
            }
        }

        // Opcode 6 is jump-if-false: if the first parameter is zero,
        // it sets the instruction pointer to the value from the second parameter.
        // Otherwise, it does nothing.
        object JumpFalse : Instruction(3) {
            override fun execute(memory: MutableMap<Long, Long>, io: State) {
                val p1 = memory.getValue(io.ip + 1, memory.parameterMode(io.ip, 1), io.base)
                val p2 = memory.getValue(io.ip + 2, memory.parameterMode(io.ip, 2), io.base)
                if (p1 == 0L) io.ip = p2
                else io.ip += offset
            }
        }

        // Opcode 7 is less than: if the first parameter is less than the second parameter,
        // it stores 1 in the position given by the third parameter.
        // Otherwise, it stores 0.
        object LessThan : Instruction(4) {
            override fun execute(memory: MutableMap<Long, Long>, io: State) {
                val p1 = memory.getValue(io.ip + 1, memory.parameterMode(io.ip, 1), io.base)
                val p2 = memory.getValue(io.ip + 2, memory.parameterMode(io.ip, 2), io.base)
                memory[memory.setValue(io.ip, 3, io.base)] = if (p1 < p2) 1 else 0
                io.ip += offset
            }
        }

        // Opcode 8 is equals: if the first parameter is equal to the second parameter,
        // it stores 1 in the position given by the third parameter.
        // Otherwise, it stores 0.
        object Equals : Instruction(4) {
            override fun execute(memory: MutableMap<Long, Long>, io: State) {
                val p1 = memory.getValue(io.ip + 1, memory.parameterMode(io.ip, 1), io.base)
                val p2 = memory.getValue(io.ip + 2, memory.parameterMode(io.ip, 2), io.base)
                memory[memory.setValue(io.ip, 3, io.base)] = if (p1 == p2) 1 else 0
                io.ip += offset
            }
        }

        object SetBase : Instruction(2) {
            override fun execute(memory: MutableMap<Long, Long>, io: State) {
                io.base += memory.getValue(io.ip + 1, memory.parameterMode(io.ip, 1), io.base)
                io.ip += offset
            }
        }

        // Opcode 99 is halt: the memory is finished and should immediately halt
        object Halt : Instruction(1) {
            override fun execute(memory: MutableMap<Long, Long>, io: State) {
            }
        }

        enum class ParameterMode(val code: Int) {
            POSITION(0),
            IMMEDIATE(1),
            RELATIVE(2);

            companion object {
                fun fromInt(number: Int) = when (number) {
                    POSITION.code -> POSITION
                    IMMEDIATE.code -> IMMEDIATE
                    RELATIVE.code -> RELATIVE
                    else -> throw IllegalArgumentException("Unknown mode for parameter: $number")
                }

                inline fun mapLongToMode(number: Long, map: (Long) -> Int) =
                    fromInt(map(number))
            }
        }

        fun MutableMap<Long, Long>.getValue(ip: Long, mode: ParameterMode, base: Long): Long {
            return when (mode) {
                ParameterMode.POSITION -> getOrDefault(getOrDefault(ip, 0L), 0L)
                ParameterMode.IMMEDIATE -> getOrDefault(ip, 0L)
                ParameterMode.RELATIVE -> getOrDefault(getOrDefault(ip, 0L) + base, 0L)
            }
        }

        fun MutableMap<Long, Long>.setValue(ip: Long, parameter: Int = 3, base: Long): Long {
            return when (parameterMode(ip, parameter)) {
                ParameterMode.POSITION -> getOrDefault(ip + parameter, 0L)
                ParameterMode.RELATIVE -> getOrDefault(ip + parameter, 0L) + base
                ParameterMode.IMMEDIATE -> throw IllegalArgumentException("Can't set value in immediate mode ")
            }
        }

        fun Map<Long, Long>.parameterMode(ip: Long, parameter: Int) = when (parameter) {
            1 -> mapLongToMode(getOrDefault(ip, 0L)) { ((it % 1000 - it % 100) / 100).toInt() }
            2 -> mapLongToMode(getOrDefault(ip, 0L)) { ((it % 10000 - it % 1000) / 1000).toInt() }
            3 -> mapLongToMode(getOrDefault(ip, 0L)) { ((it % 100000 - it % 10000) / 10000).toInt() }
            else -> throw IllegalStateException("Unable to determine parameter mode: $parameter")
        }

        fun Map<Long, Long>.opcode(ip: Long) = getOrDefault(ip, 0L).opcode()
        fun Long.opcode() = mod(100)

        data class State(
            var ip: Long = 0,
            var base: Long = 0,
            val input: Queue<Long>,
            val output: Queue<Long>
        )
    }
}