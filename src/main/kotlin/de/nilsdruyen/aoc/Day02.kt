package de.nilsdruyen.aoc

fun main() {

    fun part1(input: List<String>): Int {
        val gameScore = input.map { it.first() to it[2] }.sumOf {
            val result = it.first.fight(it.second)
            getScore(result, it.second)
        }
        return gameScore
    }

    fun part2(input: List<String>): Int {
        val gameScore = input.map { it.first() to it[2] }.sumOf {
            val predicted = it.second.predictedEnd(it.first)
            val result = it.first.fight(predicted)
            getScore(result, predicted)
        }
        return gameScore
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    println("$testInput = ${part2(testInput)}")
    check(part2(testInput) == 12)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}

fun Char.predictedEnd(opponent: Char): Char {
    return when {
        this == 'X' -> when (opponent) {
            'A' -> 'Z'
            'B' -> 'X'
            'C' -> 'Y'
            else -> error("unknown")
        }

        this == 'Y' -> when (opponent) {
            'A' -> 'X'
            'B' -> 'Y'
            'C' -> 'Z'
            else -> error("unknown")
        }

        this == 'Z' -> when (opponent) {
            'A' -> 'Y'
            'B' -> 'Z'
            'C' -> 'X'
            else -> error("unknown")
        }

        else -> error("unknown")
    }
}


fun Char.score(): Int = when (this) {
    'A' -> 1
    'B' -> 2
    'C' -> 3
    'X' -> 1
    'Y' -> 2
    'Z' -> 3
    else -> 0
}

// won 6, draw 3, loss 0
fun getScore(result: Int, choosed: Char): Int {
    val score = when (result) {
        1 -> 6
        0 -> 3
        -1 -> 0
        else -> 0
    }
    return score + choosed.score()
}

// A X rock, B Y paper, C Z scissors
fun Char.fight(other: Char): Int {
    return when {
        this == 'B' && other == 'Z' -> 1
        this == 'C' && other == 'X' -> 1
        this == 'A' && other == 'Y' -> 1

        this == 'A' && other == 'Z' -> -1
        this == 'B' && other == 'X' -> -1
        this == 'C' && other == 'Y' -> -1

        this == 'A' && other == 'X' -> 0
        this == 'B' && other == 'Y' -> 0
        this == 'C' && other == 'Z' -> 0
        else -> 0
    }
}