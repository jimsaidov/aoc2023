import day03.Day03

fun main() {
    // test if implementation meets criteria from the description, like:
    val testInput = Day03.readInput("Test")
    check(Day03.solvePartOne(testInput) == 4361)
    check(Day03.solvePartTwo(testInput) == 467835)

    val input = Day03.readInput("Input")
    Day03.solvePartOne(input).println()
    Day03.solvePartTwo(input).println()
}
