interface Puzzle<Part1, Part2, Input> {
    fun solvePartOne(inputData: Input): Part1

    fun solvePartTwo(inputData: Input): Part2

    fun readInput(inputFileName: String) : Input
}