import day06.Day06

fun main() {
    val testInput = Day06.readInput("Test")
    check(Day06.solvePartOne(testInput) == 288L)
    check(Day06.solvePartTwo(testInput) == 71503L)

    val input = Day06.readInput("Input")
    Day06.solvePartOne(input).println()
    Day06.solvePartTwo(input).println()
}
