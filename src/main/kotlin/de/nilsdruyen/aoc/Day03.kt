package de.nilsdruyen.aoc

fun main() {

  fun Char.prio(): Int = if (code > 96) code - 96 else code - 38

  fun List<String>.sumUpPrio(): Int = map {
    val center = it.length / 2
    it.slice(0 until center) to it.slice(center until it.length)
  }
    .map { parts ->
      parts.first.first { parts.second.contains(it) }
    }
    .sumOf(Char::prio)

  fun part1(input: List<String>): Int = input.sumUpPrio()

  fun part2(input: List<String>): Int {
    return input.windowed(3, 3)
      .map { group ->
        group.first().first { group[1].contains(it) && group[2].contains(it) }
      }
      .sumOf(Char::prio)
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("Day03_test")
  check(part2(testInput) == 70)

  val input = readInput("Day03")
  println(part1(input))
  println(part2(input))
}

