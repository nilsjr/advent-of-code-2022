package de.nilsdruyen.aoc

fun main() {

    fun part1(input: List<String>): Int {
        val forest = input.map { it.map { tree -> tree.digitToInt() } }
        val forestWidth = forest.first().size - 1
        val forestHeight = forest.size - 1

        val outerVisible = forest.first().size * 2 + (forest.size * 2) - 4
        val innerVisible = forest
            .mapIndexed { indexR, row ->
                row.mapIndexed { indexT, t ->
                    if (indexT in 1 until forestWidth && indexR in 1 until forestHeight) {
                        val leftVisible = forest[indexR].slice(0 until indexT).max() < t
                        val rightVisible = forest[indexR].slice(indexT + 1..forestWidth).max() < t
                        val topVisible = forest.map { it[indexT] }.slice(0 until indexR).max() < t
                        val bottomVisible = forest.map { it[indexT] }.slice(indexR + 1..forestHeight).max() < t
                        listOf(leftVisible, rightVisible, topVisible, bottomVisible).any { it }
                    } else {
                        false
                    }
                }
            }
            .flatten()
            .count { it }

        return outerVisible + innerVisible
    }

    fun part2(input: List<String>): Int {
        val forest = input.map { it.map { tree -> tree.digitToInt() } }
        val forestWidth = forest.first().size - 1
        val forestHeight = forest.size - 1

        val scenicScore = forest
            .mapIndexed { indexR, row ->
                row.mapIndexed { indexT, t ->
                    if (indexT in 1 until forestWidth && indexR in 1 until forestHeight) {
                        val leftVisible = forest[indexR]
                            .slice(0 until indexT)
                            .reversed()
                        val rightVisible = forest[indexR].slice(indexT + 1..forestWidth)
                        val topVisible = forest
                            .map { it[indexT] }
                            .slice(0 until indexR)
                            .reversed()
                        val bottomVisible = forest.map { it[indexT] }.slice(indexR + 1..forestHeight)

                        println("check $indexR/$indexT - $t")

                        val list = listOf(leftVisible, rightVisible, topVisible, bottomVisible)
                        println(list)
                        list
                            .map { direction ->
                                val zipped = direction.zipWithNext().ifEmpty { listOf(Pair(direction.first(), 0)) }
                                if (t <= zipped.first().first) {
                                    1
                                } else {
                                    zipped.takeWhile { it.first <= it.second }.oneIfEmpty()
                                }
                            }
                            .fold(1) { result, count -> result * count }
                    } else {
                        0
                    }
                }
            }
            .flatten()
            .max()

        return scenicScore
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    check(part1(testInput) == 21)
    check(part2(testInput) == 8)
    check(part2(testInput) == 12)

    val input = readInput("Day08")
    println(part1(input))
    println(part2(input)) // 1023568 too high , 360 low
}

private fun <T> List<T>.oneIfEmpty(): Int {
    return if (this.isEmpty()) 1 else this.size + 1
}