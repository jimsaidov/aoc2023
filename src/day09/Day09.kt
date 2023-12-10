package day09

import Puzzle
import println
import readFileInput
import toLongList

object Day09 : Puzzle<Long, Long, List<String>> {
    override fun solvePartOne(inputData: List<String>): Long =
            inputData.sumOf { predictNext(it.toLongList()) }

    override fun solvePartTwo(inputData: List<String>): Long =
            inputData.sumOf { predictNext(it.toLongList().asReversed()) }

    override fun readInput(inputFileName: String): List<String> =
            readFileInput("day09/input/$inputFileName")

    private fun predictNext(history: List<Long>): Long =
            if (history.all { it == 0L }) 0L
            else history.last() + predictNext(history.windowed(2) { it[1] - it[0] })
}