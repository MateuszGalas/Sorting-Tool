package sorting

import java.lang.RuntimeException

class SortingTool(private val sortingType: String = "natural", val dataType: String = "word") {
    private val list = mutableListOf<String>()

    init {
        while (true) {
            try {
                list.add(readln())
            } catch (e: RuntimeException) {
                break
            }
        }
    }
    //Sorting values by themselves and by amount of them
    private fun sortByCount(list: List<Any>): Map<Any, Int> {
        return list.groupingBy { it }.eachCount().toList().sortedBy { (key, _) -> key as Comparable<Any> }
            .sortedBy { (_, value) -> value }.toMap()
    }
    //Sorting values in list (in this project Int and String)
    private fun sortByNatural(list: List<Any>): List<Any> {
        return list.sortedBy { value -> value as Comparable<Any> }
    }
    //Sorting depending on arguments provided, default as natural and word
    fun run() {
        val data = when (dataType) {
            "word" -> {
                val words = mutableListOf<String>()
                list.forEach { words.addAll(it.split("""\s+""".toRegex())) }
                println("Total words: ${words.size}.")
                words
            }

            "long" -> {
                val numbers = mutableListOf<Int>()
                list.forEach { numbers.addAll(it.split(" ").filter { it != "" }.map { it.toInt() }) }
                println("Total numbers: ${numbers.size}.")
                numbers
            }

            else -> {
                println("Total lines: ${list.size}.")
                list
            }
        }

        when (sortingType) {
            "byCount" -> {
                val sorted = sortByCount(data)
                val numbersCount = sorted.values.sum()
                sorted.forEach { println("${it.key}: ${it.value} time(s), ${it.value * 100 / numbersCount}%") }
            }

            "natural" -> {
                val sorted = sortByNatural(data)
                println(
                    if (dataType == "line") {
                        sorted.joinToString("\n")
                    } else {
                        sorted.joinToString(" ")
                    }
                )
            }
        }
    }
}

fun main(args: Array<String>) {
    val sort = when {
        args.isEmpty() -> SortingTool()
        args.size == 2 && args.first() == "-sortingType" -> SortingTool(args[1])
        args.size == 2 && args.first() == "-dataType" -> SortingTool(dataType = args[1])
        else -> SortingTool(args[args.indexOf("-sortingType") + 1], args[args.indexOf("-dataType") + 1])
    }

    sort.apply { run() }
}
