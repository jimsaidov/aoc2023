package day02

import Puzzle
import readFileInput

object Day02 : Puzzle<Int, Int, List<String>> {
    private data class Game(val gameId: Int, val rounds: List<String>)

    private val availableCubes = mapOf(
            "red" to 12,
            "green" to 13,
            "blue" to 14
    )

    override fun solvePartOne(inputData: List<String>): Int =
            inputData.map {
                val (gameInfoPart, roundPart) = it.split(": ")
                Game(gameInfoPart.split(" ")[1].toInt(),
                        roundPart.split("; "))
            }.filter { game ->
                game.rounds.all { round ->
                    round.split(", ").all { cube ->
                        val (number, cubeName) = cube.split(" ")
                        number.toInt() <= availableCubes.getValue(cubeName)
                    }
                }
            }.sumOf { it.gameId }

    override fun solvePartTwo(inputData: List<String>): Int =
            inputData.map {
                val (gameInfoPart, roundPart) = it.split(": ")
                Game(gameInfoPart.split(" ")[1].toInt(),
                        roundPart.split("; "))
            }.run {
                sumOf { game ->
                    buildMap {
                        game.rounds.forEach { round ->
                            round.split(", ").forEach { cube ->
                                val (number, cubeName) = cube.split(" ")
                                put(cubeName,
                                        maxOf(getOrDefault(cubeName, number.toInt()), number.toInt())
                                )
                            }
                        }
                    }.values.fold(1, Int::times)
                }
            }

    override fun readInput(inputFileName: String): List<String> =
            readFileInput("day02/input/$inputFileName")
}