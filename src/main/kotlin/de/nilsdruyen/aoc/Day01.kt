package de.nilsdruyen.aoc

fun main() {

    fun List<String>.groupByCalories(): List<Int> {
        var elveIndex = 0
        return mapNotNull {
            if (it.isBlank()) {
                elveIndex++
                null
            } else {
                elveIndex to it
            }
        }.groupBy { it.first }.map {
            it.value.sumOf { foodCalories -> foodCalories.second.toInt() }
        }
    }

    fun part1(input: List<String>): Int = input.groupByCalories().max()

    fun part2(input: List<String>): Int = input.groupByCalories().sorted().takeLast(3).sum()

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 24000)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}