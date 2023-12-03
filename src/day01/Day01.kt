package day01

import Puzzle
import readFileInput

object Day01 : Puzzle<Int, Int, List<String>> {
    override fun solvePartOne(inputData: List<String>): Int = inputData.sumOf { line ->
        val firstDigit = line.first(Char::isDigit)
        val lastDigit = line.reversed().first(Char::isDigit)
        "$firstDigit$lastDigit".toInt()
    }

    override fun solvePartTwo(inputData: List<String>): Int {
        val numbersMap = buildMap {
            listOf(
                    1 to "one",
                    2 to "two",
                    3 to "three",
                    4 to "four",
                    5 to "five",
                    6 to "six",
                    7 to "seven",
                    8 to "eight",
                    9 to "nine"
            ).forEach { (digit, label) ->
                put(digit.toString(), digit)
                put(label, digit)
            }
        }

        return inputData.sumOf { line ->
            val firstDigit = numbersMap.getValue(line.findAnyOfOrException(numbersMap.keys).second)
            val lastDigit = numbersMap.getValue(line.findLastAnyOfOrException(numbersMap.keys).second)
            "$firstDigit$lastDigit".toInt()
        }
    }

    override fun readInput(inputFileName: String): List<String> =
            readFileInput("day01/input/$inputFileName")

    private fun String.findAnyOfOrException(items: Set<String>) =
            findAnyOf(items) ?: throw Exception("No item found")

    private fun String.findLastAnyOfOrException(items: Set<String>) =
            findLastAnyOf(items) ?: throw Exception("No item found")

}