package de.nilsdruyen.aoc

fun main() {
    fun part1(input: List<String>): Int {
        var elveIndex = 0
        return input
            .mapNotNull {
                if (it.isBlank()) {
                    elveIndex++
                    null
                } else {
                    elveIndex to it
                }
            }
            .groupBy { it.first }
            .map { it.value.sumOf { it.second.toInt() } }
            .max()
    }

    fun part2(input: List<String>): Int {
        var elveIndex = 0
        return input
            .mapNotNull {
                if (it.isBlank()) {
                    elveIndex++
                    null
                } else {
                    elveIndex to it
                }
            }
            .groupBy { it.first }
            .map { it.value.sumOf { it.second.toInt() } }
            .sorted()
            .takeLast(3)
            .sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 24000)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}