package me.paxana.adventofcode.day5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Range
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import me.paxana.adventofcode.R
import me.paxana.adventofcode.toList

class Day5PartAActivity : AppCompatActivity() {
  lateinit var seeds: Pair<String, List<Long>>
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_day5_part_aactivity)
    Logger.addLogAdapter(AndroidLogAdapter())
    val blocks = mutableMapOf<String, String>()
    val keys = mutableListOf<String>()
    val values = mutableListOf<MutableList<String>>()
    val mappy = mutableMapOf<String, MutableList<String>>()

    val seedsToLocations = mutableMapOf<Long, Long>()

    var lineCount = 0
    var latestKey = ""

    assets.open("Day5Input.txt").bufferedReader().useLines { lines ->
      lines.forEach { lineStr ->
        if (lineStr.isNotEmpty()) {
          if (lineStr.contains("seeds:")) {
            val split = lineStr.split(":")
            seeds = Pair(split[0], split[1].split(" ").drop(1).map { it.toLong() })
          } else if (lineStr.contains("map:")) {
            latestKey = lineStr.split(":")[0]
            mappy[latestKey] = mutableListOf()
          } else {
            mappy[latestKey]!!.add(lineStr)
          }
        }

      }
      val result = mutableListOf<Pair<LongRange, LongRange>>()
//TODO better variable names

      val nextMappy = mappy.map {
        Pair(
          it.key,
          it.value.map {
            convertToRanges(
              splitToNumerals(it)[2],
              Pair(splitToNumerals(it)[0], splitToNumerals(it)[1])
            )
          })
      }.toMap()

      Logger.d("nextMappy: $nextMappy")

      mappy["seed-to-soil map"]?.forEach {
        var splitToNumerals = it.split(" ").map { it.toLong() }
        result.add(convertToRanges(splitToNumerals[2], Pair(splitToNumerals[0], splitToNumerals[1])))
      }

      nextMappy.forEach {
        it.value.forEach { range ->
          result.add(range)
        }
      }

      //TODO make this recursive
      val resultsList = mutableListOf<Pair<Long, Pair<String, Long>>>()

      val lastMap = mutableMapOf<Long, Pair<String, Long>>()

      var priorResults = seeds.second

      nextMappy.forEach { mapOfKeyToRangesList ->
        priorResults = priorResults.map { findSeedLocation(mapOfKeyToRangesList.key, it, mapOfKeyToRangesList.value).second.second }
      }

      Logger.d("priorResults: $priorResults")

      seeds.second.forEach { seed ->
        nextMappy.forEach{
          findSeedLocation(it.key, seed, it.value).let { pair ->
            resultsList.add(pair)
            val key = pair.second.first
            val value = pair.second.second
            lastMap[seed] = Pair(key, value)
          }
        }
      }

      Logger.d("seedsToLocations: $seedsToLocations")
      Logger.d("nextMappy: $nextMappy")
      Logger.d("resultsList: $resultsList")


      Logger.d("result: $result")

      Logger.d("lastMap: $lastMap")

      Logger.d("Correct answer hopefully: ${priorResults.min()}")

    }

    Logger.d(seeds)
    Logger.d(mappy)
  }

  fun splitToNumerals(it: String): List<Long> {
    return it.split(" ").map { it.toLong() }
  }

  fun convertToRanges(rangeModifier: Long, rangeStarts: Pair<Long, Long>): Pair<LongRange, LongRange> {
    val ir1 = LongRange(rangeStarts.first, rangeStarts.first + rangeModifier - 1)
    val ir2 = LongRange(rangeStarts.second, rangeStarts.second + rangeModifier - 1)
    return Pair(ir1, ir2)
  }

  fun findSeedLocation(
    key: String,
    seed: Long,
    ranges: List<Pair<LongRange, LongRange>>
  ): Pair<Long, Pair<String, Long>> {
    var nextLocation = seed

    ranges.forEach {
      if (seed in it.second) {
        val difference = seed - it.second.first
        Logger.d("difference: $difference, seed: $seed, destination range start: ${it.first.first}")
        nextLocation = it.first.first + difference
      }
    }
    return Pair(seed, Pair(key, nextLocation))


  }

}