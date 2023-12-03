package me.paxana.adventofcode

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger

class Day3Activity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_day3)
    Logger.addLogAdapter(AndroidLogAdapter())
    val possibleResults = mutableListOf<MutableMap<String, MutableList<Int>>>()
    var numResult = 0
    val idList = mutableListOf<Int>()

    assets.open("Day2Input.txt").bufferedReader().useLines { lines ->
      lines.forEach { lineStr ->
        val results = mutableMapOf(
          "red" to mutableListOf<Int>(),
          "green" to mutableListOf(),
          "blue" to mutableListOf()
        )

        val removeTitle = lineStr.split(":")
        val title = removeTitle[0].split(" ")[1]
        val withoutTitle = removeTitle[1]
        val replaceSemicolonsSplitCommasTrimSpaces = withoutTitle.replace(";", ",").split(",").map{ it.trim() }

        replaceSemicolonsSplitCommasTrimSpaces.forEach { cubePull ->

          val split = cubePull.split(" ")
          when (split[1]) {
            // TODO: For efficiency sake, when a number gets above the limiter I should finish counting and skip the rest of the line

            "green" -> results["green"]!!.add(split[0].toInt())
            "red" -> results["red"]!!.add(split[0].toInt())
            "blue" -> results["blue"]!!.add(split[0].toInt())
          }
        }

        Logger.d("Line: $lineStr, Results: $results")
        if (isPossible(results)) {
          numResult++
          possibleResults.add(results)
          idList.add(title.toInt())
        }

      }
      Logger.d("ID List sum: ${idList.sum()}")
    }
  }
  private fun isPossible(results: MutableMap<String, MutableList<Int>>): Boolean {
    if (results["red"]!!.max() <= 12 && results["green"]!!.max() <= 13 && results["blue"]!!.max() <= 14) {
      return true
    }
    return false
  }
}