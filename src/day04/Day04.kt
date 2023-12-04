package day04

import Puzzle
import kotlin.math.pow
import readFileInput

object Day04 : Puzzle<Int, Int, List<String>> {
    override fun solvePartOne(inputData: List<String>): Int =
        transformInput(inputData)
            .filter { it.matches.isNotEmpty() }
            .sumOf { 2.0.pow((it.matches.size - 1)).toInt()}

    override fun solvePartTwo(inputData: List<String>): Int =
        buildMap {
            transformInput(inputData).forEachIndexed { index, card ->
                val numOfCopies : Int = getOrDefault(index + 1, 0)
                put(index + 1, numOfCopies + 1)

                (index + 2 .. index + 1 + card.matches.size).forEach { next ->
                    val numOfNextCopies : Int = getOrDefault(next, 0)
                    put(next, numOfNextCopies + numOfCopies + 1)
                }
            }
        }.values.sum()

    override fun readInput(inputFileName: String): List<String> =
            readFileInput("day04/input/$inputFileName")

    private fun transformInput(inputData: List<String>) =
            buildList {
                inputData.map { line ->
                    val (cardIdPart, numbersPart) = line.split(": ")
                    val cardId = cardIdPart.toIntList().first()
                    val (winningNumbersPart, playerNumbersPart) = numbersPart.split(" | ")
                    add(
                        Card(
                            cardId,
                            winningNumbersPart.toIntList(),
                            playerNumbersPart.toIntList()
                        )
                    )
                }
            }
}
private data class Card (
    val cardId: Int,
    val winningNumbers: List<Int>,
    val playerNumbers: List<Int>
) {
    val matches: List<Int>
        get() = winningNumbers.filter { playerNumbers.contains(it) }
}

private fun String.toIntList() = "\\d+".toRegex().findAll(this).map { it.value.toInt() }.toList()