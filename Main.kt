package sorting

import java.io.File
import java.lang.RuntimeException

class SortingTool(private val sortingType: String, private val dataType: String, inputFile: String, outputFile: String) {
    private val list = mutableListOf<String>()
    private val input = File(inputFile)
    private val output = File(outputFile)

    // Taking data from file if provided, otherwise from input.
    init {
        if (inputFile != "") {
            list.addAll(input.readLines())
        } else {
            while (true) {
                try {
                    list.add(readln())
                } catch (e: RuntimeException) {
                    break
                }
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

    //Sorting depending on arguments provided, default as natural and word. Saving data to file if provided.
    fun run() {
        val data = when (dataType) {
            "word" -> {
                val words = mutableListOf<String>()
                list.forEach { words.addAll(it.split("""\s+""".toRegex())) }
                println("Total words: ${words.size}.")
                words
            }

            "long" -> {
                val numbers = mutableListOf<String>()
                val elements = mutableListOf<String>()
                list.forEach { elements.addAll(it.split(" ").filter { it != "" }) }
                elements.forEach {
                    if (!it.matches("""-?\d+""".toRegex())) println("\"${it}\" is not a long. It will be skipped.")
                    else numbers.add(it)
                }
                println("Total numbers: ${numbers.size}.")
                numbers.map { it.toInt() }
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
                if (output.name == "") {
                    sorted.forEach { println("${it.key}: ${it.value} time(s), ${it.value * 100 / numbersCount}%") }
                } else if (output.exists()) {
                    output.writeText("")
                    sorted.forEach {
                        output.appendText(
                            "${it.key}: ${it.value} time(s), ${it.value * 100 / numbersCount}%"
                        )
                    }
                } else {
                    output.createNewFile()
                    sorted.forEach {
                        output.appendText(
                            "${it.key}: ${it.value} time(s), ${it.value * 100 / numbersCount}%"
                        )
                    }
                }
            }

            "natural" -> {
                val sorted = sortByNatural(data)

                if (dataType == "line") {
                    if (output.name == "") {
                        println(sorted.joinToString("\n"))
                    } else if (output.exists()) {
                        output.writeText("")
                        output.appendText(sorted.joinToString("\n"))
                    } else {
                        output.createNewFile()
                        output.appendText(sorted.joinToString("\n"))
                    }
                } else {
                    if (output.name == "") {
                        println(sorted.joinToString(" "))
                    } else if (output.exists()) {
                        output.writeText("")
                        output.appendText(sorted.joinToString(" "))
                    } else {
                        output.createNewFile()
                        output.appendText(sorted.joinToString(" "))
                    }
                }

            }
        }
    }
}

fun main(args: Array<String>) {
    val sortingType = mutableListOf("natural", "byCount")
    val dataType = mutableListOf("long", "line", "word")
    val types = mutableListOf("-sortingType", "-dataType")

    args.forEach {
        if (it !in sortingType && it !in dataType && it !in types) {
            println("\"$it\" is not a valid parameter. It will be skipped")
        }
    }
    val inputFileIndex = args.indexOf("-inputFile")
    val outputFileIndex = args.indexOf("-outputFile")
    val sortingTypeIndex = args.indexOf("-sortingType")
    val dataTypeIndex = args.indexOf("-dataType")

    val input = if (inputFileIndex != -1) args[inputFileIndex + 1] else ""
    val output = if (outputFileIndex != -1) args[outputFileIndex + 1] else ""
    val sorting = try {
        if (sortingTypeIndex != -1) args[sortingTypeIndex + 1] else "natural"
    } catch (e: Exception) {
        "natural"
    }

    val data = try {
        if (dataTypeIndex != -1) args[dataTypeIndex + 1] else "word"
    } catch (e: Exception) {
        "word"
    }

    val sort = SortingTool(sorting, data, input, output)

    sort.apply { run() }
}
