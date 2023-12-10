package day08

import Puzzle
import println
import readFileInput

object Day08 : Puzzle<Long, Long, List<String>> {
    override fun solvePartOne(inputData: List<String>): Long = transformInput(inputData).let { (instructions, nodes) ->
        traverse(instructions, nodes, nodes.getValue("AAA"))
    }

    override fun solvePartTwo(inputData: List<String>): Long = transformInput(inputData).let { (instructions, nodes) ->
        nodes.values.filter { it.isStartNode() }.toSet().map { traverse(instructions, nodes, it) }.lcm()
    }

    override fun readInput(inputFileName: String): List<String> = readFileInput("day08/input/$inputFileName")

    private fun transformInput(inputData: List<String>) =
        Graph(inputData.first(), inputData.drop(2).associate {
            val (name, directions) = it.split(" = ")
            val (left, right) = directions.removePrefix("(").removeSuffix(")").split(", ")
            name to Node(name, left, right)
        })

    private fun traverse(instructions: String, nodes: Map<String, Node>, start: Node): Long {
        var i = 0L
        var current = start

        while (!current.isEndNode()) {
            val instruction = instructions[(i % instructions.length).toInt()].also { i++ }
            current = when (instruction) {
                'L' -> nodes.getValue(current.left)
                'R' -> nodes.getValue(current.right)
                else -> throw Exception("Only 'L' and 'R' are allowed")
            }
        }

        return i
    }

    data class Node(val label: String, val left: String, val right: String)

    private data class Graph(val instructions: String, val nodes: Map<String, Node>)

    private fun Node.isEndNode() = label.endsWith("Z")
    private fun Node.isStartNode() = label.endsWith("A")

    private fun List<Long>.lcm() = reduce { a, b -> lcm(a, b) }

    // source: https://www.programiz.com/kotlin-programming/examples/lcm
    private fun lcm(a: Long, b: Long): Long {
        var gcd = 1
        var i = 1

        while (i <= a && i <= b) {
            if (a % i == 0L && b % i == 0L) gcd = i
            ++i
        }

        return a * b / gcd
    }
}