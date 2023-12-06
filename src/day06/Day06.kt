package day06

import Puzzle
import readFileInput
import toLongList

object Day06 : Puzzle<Long, Long, List<String>> {
    override fun solvePartOne(inputData: List<String>): Long {
        val times = inputData.first().split(":").last().toLongList()
        val distances = inputData.last().split(":").last().toLongList()

        return times.zip(distances).map { (time, distance) ->
            getNumberOfWinWays(time, distance)
        }.fold(1, Long::times)
    }

    override fun solvePartTwo(inputData: List<String>): Long {
        val time = inputData.first().split(":").last().filter(Char::isDigit).toLong()
        val distance = inputData.last().split(":").last().filter(Char::isDigit).toLong()

        return getNumberOfWinWays(time, distance)
    }

    override fun readInput(inputFileName: String): List<String> =
        readFileInput("day06/input/$inputFileName")

    private fun getNumberOfWinWays(time: Long, distance: Long): Long =
        (1..time).count { hold ->
            hold * (time - hold) > distance
        }.toLong()
}