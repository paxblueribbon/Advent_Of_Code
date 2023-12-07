package me.paxana.adventofcode.day6

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.orhanobut.logger.Logger
import me.paxana.adventofcode.R

class Day6Activity : AppCompatActivity() {

  private var times = listOf<Long>()
  private var distances = listOf<Long>()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_day6_parta)
    Logger.addLogAdapter(com.orhanobut.logger.AndroidLogAdapter())

    assets.open("Day6Input.txt").bufferedReader().useLines { lines ->
      lines.forEach { lineStr ->
        if (lineStr.isNotEmpty()) {
          if (lineStr.contains("Time:")) {
            times = stringToListOfLongs(lineStr)
          } else if (lineStr.contains("Distance:")) {

            distances = stringToListOfLongs(lineStr)
          }
        }
      }
    }

    Logger.d("partAAnswer: ${partAAnswer(times.toList(), distances.toList())}")

    val longTime = times.joinTo(StringBuilder(), separator = "").toString().toLong()
    val longDistance = distances.joinTo(StringBuilder(), separator = "").toString().toLong()

    Logger.d("partBAnswer: ${partBAnswer(longTime, longDistance)}")
  }

  private fun stringToListOfLongs(line: String): List<Long> {
    return line.split(":")[1].split(" ").filter { it.isNotEmpty()  }.map { it.toLong()  }.toMutableList()
  }

  private fun findRangeImproved(time: Long, distance: Long): LongRange {
    var min: Long? = null
    var max: Long? = null
    //TODO: Do this with coroutines instead of while loops

    while (min == null && max == null) {
        for (i in 0..time) {
          if (i * (time - i) > distance && min == null) {
            min = i
          }
        }

        for (i in time downTo 0) {
          if (i * (time - i) > distance && max == null) {
            max = i
          }

      }
    }
    return LongRange(min ?: 0, max ?: 0)
  }

  private fun partAAnswer(timeList: List<Long>, distanceList: List<Long>): Long {
    val timeDistanceMap = timeList.zip(distanceList).toMap()
    val possibleWins = mutableListOf<Long>()
    timeDistanceMap.forEach {
      val range = findRangeImproved(it.key, it.value)
      possibleWins.add((range.max() - range.min()) + 1)
    }
    return possibleWins.reduce { acc, l -> acc* l }
  }

  private fun partBAnswer(time: Long, distance: Long): Long {
    val range = findRangeImproved(time, distance)

    return (range.max() - range.min()) + 1
  }
}