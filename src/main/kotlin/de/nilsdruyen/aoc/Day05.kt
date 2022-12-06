package de.nilsdruyen.aoc

fun main() {

    fun part1(input: List<String>): String {
        return with(input.crates()) {
            input.instructions(size).fold(stacks()) { s, i ->
                val movedCrates = s[i.from - 1].takeLast(i.move).reversed()
                s[i.from - 1] = s[i.from - 1].dropLast(i.move)
                s[i.to - 1] += movedCrates
                s
            }.topChars()
        }
    }

    fun part2(input: List<String>): String {
        return with(input.crates()) {
            input.instructions(size).fold(stacks()) { s, i ->
                val movedCrates = s[i.from - 1].takeLast(i.move)
                s[i.from - 1] = s[i.from - 1].dropLast(i.move)
                s[i.to - 1] += movedCrates
                s
            }.topChars()
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == "CMZ")
    check(part2(testInput) == "MCD")

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}

data class Instruction(val move: Int, val from: Int, val to: Int)

private fun List<List<Char>>.topChars() = map { it.last() }.joinToString("")

private fun List<String>.crates() = takeWhile { it.isNotBlank() }

private fun List<String>.instructions(dropLines: Int): List<Instruction> = drop(dropLines + 1).map {
    val parts = it.split(" ")
    val move = parts[1].toInt()
    val from = parts[3].toInt()
    val to = parts[5].toInt()
    Instruction(move, from, to)
}

private fun List<String>.stacks(): MutableList<List<Char>> {
    return asSequence()
        .map { it.windowed(3, 4) }
        .flatMap { row -> row.mapIndexed { index, s -> index to s[1] } }
        .filter { it.second.isLetter() }
        .groupBy { it.first }
        .toSortedMap()
        .map { entry -> entry.value.map { it.second }.reversed() }
        .toMutableList()
}