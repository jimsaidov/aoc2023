package day05

import Puzzle
import println
import toRegexLongList
import kotlin.io.path.Path
import kotlin.io.path.readText

object Day05 : Puzzle<Long, Long, String> {
    override fun solvePartOne(inputData: String): Long = transformInput(inputData).run {
        seeds.minOf { seed ->
            findLocation(seed, maps)
        }
    }

    override fun solvePartTwo(inputData: String): Long = transformInput(inputData).run {
        var lowestSeed: Long = Long.MAX_VALUE
        val seedRanges = seeds.chunked(2).map {
            val progression: LongProgression = it[0]..<it[0] + it[1]
            progression
        }

        // Not optimal implemenation at all as traverses too much, but it works
        seedRanges.forEach { seed ->
            for (i in seed.first..seed.last) {
                lowestSeed = minOf(lowestSeed, findLocation(i, maps))
            }
        }
        lowestSeed
    }

    override fun readInput(inputFileName: String): String = Path("src/day05/input/$inputFileName.txt").readText()

    private fun transformInput(inputData: String): Almanac = inputData.split("\r\n\r\n").let { splitInput ->
        Almanac(
            splitInput[0].toRegexLongList(),
            splitInput.drop(1).map { rawMap ->
            rawMap.split("\r\n").drop(1).map {
                val (dest, start, length) = it.toRegexLongList()
                val sourceProgression: LongProgression = start..<start + length
                val targetProgression: LongProgression = dest..<dest + length
                Pair(sourceProgression, targetProgression)
            }
        })
    }
}

data class Almanac(val seeds: List<Long>, val maps: List<List<Pair<LongProgression, LongProgression>>>)

fun findDestination(mapRanges: List<Pair<LongProgression, LongProgression>>, source: Long): Long {
    for ((sourceRange, destRange) in mapRanges) {
        if (sourceRange.first <= source && source <= sourceRange.last) {
            return destRange.first + source - sourceRange.first
        }
    }
    return source
}

fun findLocation(seedIndex: Long, maps: List<List<Pair<LongProgression, LongProgression>>>): Long {
    var location = seedIndex
    for (i in maps.indices) {
        location = findDestination(maps[i], location)
    }
    return location
}