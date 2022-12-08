package de.nilsdruyen.aoc

fun main() {

    fun part1(input: List<String>): Int {
        val tree: MutableList<Entry.Directory> = mutableListOf()
        val currentDir = mutableSetOf<String>()
        val dirTree = mutableListOf<String>()

        input.forEachIndexed { index, line ->
            if (line.startsWith('$')) {
                val (_, command) = line.split(" ")
                when (command) {
                    "cd" -> {
                        val (_, _, dir) = line.split(" ")
                        if (dir == "..") {
                            if (currentDir.size > 1) {
                                currentDir.remove(currentDir.last())
                            }
                        } else {
                            currentDir.add(dir)
                        }
                    }

                    "ls" -> {
                        val cDir = currentDir.format()
                        if (tree.none { it.name == cDir }) {
                            tree.add(Entry.Directory(cDir))
                        }
                    }
                }
            } else {
                println("$index - $currentDir")
                dirTree.add(currentDir.format())
                when {
                    line.startsWith("dir") -> {
                        val (_, name) = line.split(" ")
                        val cDir = currentDir.format()

                        if (cDir == "/") {
                            tree.add(Entry.Directory("$cDir$name"))
                        } else {
                            tree.add(Entry.Directory("$cDir/$name"))
                        }
                    }

                    else -> {
                        val (size, name) = line.split(" ")
                        val cDir = currentDir.format()
                        val entryIndex = tree.indexOfFirst { it.name == cDir }
                        val entry = tree[entryIndex]
                        tree.removeAt(entryIndex)
                        tree.add(entryIndex, entry.copy(entries = entry.entries + Entry.File(name, size.toInt())))
                    }
                }
            }
        }

//        tree.forEach { println(it) }

        val mostOf = tree.filter {
            it.size(tree) in 1..100_000
        }.sumOf {
            println("${it.name} - ${it.size()}")
            it.size(tree)
        }

        return mostOf
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 95437)

    val input = readInput("Day07")
    println(part1(input)) // 1374288 too high // 1347822 too low
    println(part2(input))
}

fun Set<String>.format(): String {
    return if (size == 1) {
        first()
    } else {
        joinToString("/").drop(1)
    }
}

sealed interface Entry {

    val name: String

    data class Directory(
        override val name: String,
        val entries: List<Entry> = emptyList(),
    ) : Entry {

        fun size(other: List<Directory> = emptyList()): Int {
            val plusSize = other.filter {
                it.name.contains(name) && it.name != name
            }.sumOf { it.size() }

            return entries.sumOf {
                when (it) {
                    is Directory -> 0
                    is File -> it.size
                }
            } + plusSize
        }
    }

    data class File(
        override val name: String,
        val size: Int,
    ) : Entry
}