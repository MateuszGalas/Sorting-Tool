package sorting

import java.lang.RuntimeException

fun main(args: Array<String>) {
    val list = mutableListOf<String>()
    while (true) {
        try {
            list.add(readln())
        } catch (e: RuntimeException) {
            break
        }
    }

    var type = args.firstOrNull()

    type = when {
        type == null -> "word"
        type == "-sortIntegers" -> "-sortIntegers"
        args.size > 2 && args[2] == "-sortIntegers" -> "-sortIntegers"
        else -> args[1]
    }

    when (type) {
        "long" -> {
            val numbers = mutableListOf<Int>()
            list.forEach { numbers.addAll(it.split(" ".toRegex()).filter { it != "" }.map { it.toInt() }) }
            val maxElement = numbers.maxOf { it }
            println("Total numbers: ${numbers.size}.")
            val maxElementCount = numbers.count { it == maxElement }
            println(
                "The greatest number: $maxElement ($maxElementCount time(s), ${maxElementCount * 100 / numbers.size}%)."
            )
        }

        "line" -> {
            val maxElement = list.maxByOrNull { it.length }
            val maxElementCount = list.count { it == maxElement }
            println("Total lines: ${list.size}")
            println(
                "The longest line: \n${maxElement}\n($maxElementCount time(s), ${maxElementCount * 100 / list.size}%)."
            )
        }

        "word" -> {
            val words = mutableListOf<String>()
            list.forEach { words.addAll(it.split("""\s+""".toRegex())) }
            val maxElement = words.maxByOrNull { it.length }
            val maxElementCount = words.count { it == maxElement }
            println("Total words: ${words.size}")
            println(
                "The longest word: $maxElement ($maxElementCount time(s), ${maxElementCount * 100 / words.size}%)."
            )
        }

        "-sortIntegers" -> {
            val numbers = mutableListOf<Int>()
            list.forEach { numbers.addAll(it.split(" ".toRegex()).filter { it != "" }.map { it.toInt() }) }
            println("Total numbers: ${numbers.size}.")
            println("Sorted data: ${numbers.sorted().joinToString(" ")}")
        }
    }

}
