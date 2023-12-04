import day04.Day04

fun main() {
    // test if implementation meets criteria from the description, like:
    val testInput = Day04.readInput("Test")
    check(Day04.solvePartOne(testInput) == 13)
    check(Day04.solvePartTwo(testInput) == 30)

    val input = Day04.readInput("Input")
    Day04.solvePartOne(input).println()
    Day04.solvePartTwo(input).println()
}
