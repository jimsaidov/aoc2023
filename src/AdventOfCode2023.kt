import day07.Day07

fun main() {
    val testInput = Day07.readInput("Test")
    check(Day07.solvePartOne(testInput) == 288L)
    check(Day07.solvePartTwo(testInput) == 71503L)

    val input = Day07.readInput("Input")
    Day07.solvePartOne(input).println()
    Day07.solvePartTwo(input).println()
}
