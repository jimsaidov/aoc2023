package day03

import Puzzle
import readFileInput

object Day03 : Puzzle<Int, Int, List<String>> {
    override fun solvePartOne(inputData: List<String>): Int =
            transformInput(inputData).filterNot { it.isSurroundedByDot(inputData) }.sumOf { it.value }

    override fun solvePartTwo(inputData: List<String>): Int {
        val numberInfos = transformInput(inputData)

        return inputData.flatMapIndexed { lineIndex, line ->
            line.mapIndexedNotNull { positionIndex, char ->
                if (char == '*') {
                    numberInfos.filter {
                        it.isValidGearCandidate(lineIndex, positionIndex)
                    }.takeIf { it.size == 2 }
                            ?.let { it.first().value * it.last().value }
                } else null
            }
        }.sum()
    }

    override fun readInput(inputFileName: String): List<String> =
            readFileInput("day03/input/$inputFileName")

    private fun transformInput(inputData: List<String>) =
            buildList {
                inputData.mapIndexed { lineIndex, line ->
                    val numbers = "\\d+".toRegex().findAll(line)
                    numbers.forEach { match ->
                        add(
                                NumberInfo(
                                        match.value.toInt(),
                                        lineIndex,
                                        match.range.first,
                                        match.range.last
                                )
                        )
                    }
                }
            }
}
private data class NumberInfo(
        val value: Int,
        val lineIndex: Int,
        val startIndex: Int,
        val endIndex: Int
)

private fun NumberInfo.isValidGearCandidate(checkLineIndex: Int, checkPosition: Int): Boolean {
    if (checkLineIndex == lineIndex - 1 || checkLineIndex == lineIndex + 1) {
        if (checkPosition in (startIndex - 1)..(endIndex + 1)) {
            return true
        }
    }

    if (checkLineIndex == lineIndex) {
        if (checkPosition == startIndex - 1 || checkPosition == endIndex + 1) {
            return true
        }
    }

    return false
}

private fun NumberInfo.isSurroundedByDot(input: List<String>): Boolean {
    if ((lineIndex > 0 && !isDottedLine(input[lineIndex - 1], startIndex, endIndex)) ||
            (lineIndex < input.size - 1 && !isDottedLine(input[lineIndex + 1], startIndex, endIndex))) {
        return false
    }

    if ((startIndex > 0 && input[lineIndex][startIndex - 1] != '.') ||
            (endIndex < input[lineIndex].length - 1 && input[lineIndex][endIndex + 1] != '.')) {
        return false
    }

    if ((lineIndex > 0 && startIndex > 0 && input[lineIndex - 1][startIndex - 1] != '.') ||
            (lineIndex > 0 && endIndex < input[lineIndex].length - 1 && input[lineIndex - 1][endIndex + 1] != '.') ||
            (lineIndex < input.size - 1 && startIndex > 0 && input[lineIndex + 1][startIndex - 1] != '.') ||
            (lineIndex < input.size - 1 && endIndex < input[lineIndex].length - 1 && input[lineIndex + 1][endIndex + 1] != '.')) {
        return false
    }

    return true
}

private fun isDottedLine(line: String, startIndex: Int, endIndex: Int): Boolean {
    for (i in startIndex..endIndex) {
        if (i < line.length && line[i] != '.') {
            return false
        }
    }
    return true
}