package de.nilsdruyen.aoc

fun main() {

    fun part1(input: List<String>): Int {
        return input.uniqueIndex(4)
    }

    fun part2(input: List<String>): Int {
        return input.uniqueIndex(14)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part2(testInput) == 19)

    val input = readInput("Day06")
    println(part1(input))
    println(part2(input))
}

private fun List<String>.uniqueIndex(windowSize: Int): Int {
    return first()
        .map { it }
        .windowed(windowSize, 1)
        .indexOfFirst { it.toSet().size == it.size } + windowSize
}