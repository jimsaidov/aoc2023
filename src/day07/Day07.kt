package day07

import Puzzle
import readFileInput

object Day07 : Puzzle<Long, Long, List<String>> {
    override fun solvePartOne(inputData: List<String>): Long =
        transformInput(inputData).sorted().mapIndexed { index, hand -> hand.bid * (index+1) }.sum().toLong()

    override fun solvePartTwo(inputData: List<String>): Long =
        transformInput(inputData, true)
            .sorted().mapIndexed { index, hand -> hand.bid * (index+1) }.sum().toLong()

    override fun readInput(inputFileName: String): List<String> = readFileInput("day07/input/$inputFileName")

    private fun transformInput(inputData: List<String>, withJocker: Boolean = false): List<CardHand> = inputData.map { line ->
        val (cards, bid) = line.split(" ")
        CardHand(cards, bid.toInt(), withJocker)
    }
}

private class CardHand(
    private val cards: String, val bid: Int, private val type: Int
) : Comparable<CardHand> {

    constructor(cards: String, bid: Int, withJocker: Boolean) : this(cards, bid, getHandType(cards, withJocker))

    override operator fun compareTo(other: CardHand): Int {
        if (type > other.type) return 1
        if (type < other.type) return -1

        repeat(cards.length) {
            val result =  cardWeights.getValue(cards[it]) - cardWeights.getValue(other.cards[it])
            if (result != 0) return result
        }
        return 0
    }

    private val cardWeights by lazy {
        setOf('J', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A')
            .withIndex()
            .associate { it.value to it.index }
    }
}


private fun getHandType(cards: String, withJocker: Boolean): Int {
    val optimalCards = if (withJocker) {
        val mostCommonCard = cards.groupBy { it }.filterKeys { it != 'J' }.maxByOrNull { it.value.size }?.key ?: 'J'
        cards.replace('J', mostCommonCard)
    }
    else cards

    val groupedCards = optimalCards.groupBy { it }
    with(groupedCards) {
        // a quick implementation without verbosity
        return when (size) {
            1 -> 7
            2 -> if (any { it.value.size == 4 }) 6 else 5
            3 -> if (any { it.value.size == 3 }) 4 else 3
            4 -> 2
            5 -> 1
            else -> 0
        }
    }
}