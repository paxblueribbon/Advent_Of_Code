package me.paxana.adventofcode.day6

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.orhanobut.logger.Logger
import me.paxana.adventofcode.R

class Day6PartA : AppCompatActivity() {

  var times = mutableListOf<Int>()
  var distances = mutableListOf<Int>()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_day6_parta)
    Logger.addLogAdapter(com.orhanobut.logger.AndroidLogAdapter())

    assets.open("Day6Input.txt").bufferedReader().useLines { lines ->
      lines.forEach { lineStr ->
        if (lineStr.isNotEmpty()) {
          if (lineStr.contains("Time:")) {
            times = stringToListOfInts(lineStr)
            Logger.d("times: $times")
          } else if (lineStr.contains("Distance:")) {

            distances = stringToListOfInts(lineStr)
            Logger.d("distances: $distances")
          }
        }
      }
    }

    val timeDistanceMap = times.zip(distances).toMap()
    timeDistanceMap.forEach {
      val range = findRange(it.key, it.value)
      Logger.d("range: $range")
    }
    var rangeList: List<Int> = timeDistanceMap.map { findRange(it.key, it.value).toList().size }
    Logger.d("rangeList: $rangeList")

    Logger.d(rangeList.reduce { acc, i -> acc * i  })
  }

  private fun stringToListOfInts(line: String): MutableList<Int> {
    return line.split(":")[1].split(" ").filter { it.isNotEmpty()  }.map { it.toInt()  }.toMutableList()
  }

  private fun findRange(time: Int, distance: Int): IntRange {
    val timeList = mutableListOf<Int>()
    for (i in 0..time) {
      if (i * (time - i) > distance) {
        timeList.add(i)
      }
    }
    return IntRange(timeList.min(), timeList.max())
  }
}