package de.nilsdruyen.aoc

data class Instruction(val move: Int, val from: Int, val to: Int)

fun main() {

    fun part1(input: List<String>): String {
        val crates = input.crates()
        val stacks = crates.stacks()
        val instructions = input.instructions(crates.size + 1)

        instructions.forEach { i ->
            val movedCrates = stacks[i.from - 1].takeLast(i.move).reversed()
            val changedStack = stacks[i.from - 1].dropLast(i.move)
            stacks[i.from - 1] = changedStack
            stacks[i.to - 1] += movedCrates
        }

        return stacks.topChars()
    }

    fun part2(input: List<String>): String {
        val crates = input.crates()
        val stacks = crates.stacks()
        val instructions = input.instructions(crates.size + 1)

        instructions.forEach { i ->
            val movedCrates = stacks[i.from - 1].takeLast(i.move)
            val changedStack = stacks[i.from - 1].dropLast(i.move)
            stacks[i.from - 1] = changedStack
            stacks[i.to - 1] += movedCrates
        }

        return stacks.topChars()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part2(testInput) == "MCD")

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}

private fun List<List<Char>>.topChars() = map { it.last() }.joinToString("")

private fun List<String>.crates() = takeWhile { it.isNotBlank() }

private fun List<String>.instructions(dropLines: Int): List<Instruction> = drop(dropLines).map {
    val parts = it.split(" ")
    val move = parts[1].toInt()
    val from = parts[3].toInt()
    val to = parts[5].toInt()
    Instruction(move, from, to)
}

private fun List<String>.stacks(): MutableList<List<Char>> {
    return asSequence()
        .map { it.windowed(3, 4) }
        .map { row ->
            row.mapIndexed { index, s -> index to s[1] }
        }
        .flatten()
        .filter { it.second.isLetter() }
        .groupBy { it.first }
        .toSortedMap()
        .map { entry -> entry.value.map { it.second }.reversed() }
        .toMutableList()
}