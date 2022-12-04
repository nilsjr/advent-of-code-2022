package de.nilsdruyen.aoc

fun main() {

    fun part1(input: List<String>): Int = input.toPairRanges().count { ranges ->
        val firstInSecond = ranges.first.all { it in ranges.second }
        val secondInFirst = ranges.second.all { it in ranges.first }
        firstInSecond || secondInFirst
    }

    fun part2(input: List<String>): Int = input.toPairRanges().count { ranges ->
        ranges.first.any { it in ranges.second }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part2(testInput) == 4)

    val input = readInput("Day04")
    println(part1(input)) // 444
    println(part2(input)) // 801
}

private fun List<String>.toPairRanges(): List<Pair<IntRange, IntRange>> {
    return map {
        val (firstPair, secondPair) = it.split(",")
        val (from1, to1) = firstPair.split("-").map(String::toInt)
        val (from2, to2) = secondPair.split("-").map(String::toInt)
        val rangeOne = from1..to1
        val rangeTwo = from2..to2
        rangeOne to rangeTwo
    }
}