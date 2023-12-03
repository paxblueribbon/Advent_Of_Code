package me.paxana.adventofcode

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger

class Day4Activity : AppCompatActivity() {

  lateinit var answerTV: TextView

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_day3)
    Logger.addLogAdapter(AndroidLogAdapter())

    answerTV = findViewById(R.id.answerDay3TV)
    val powerList = mutableListOf<Int>()

    assets.open("Day2Input.txt").bufferedReader().useLines { lines ->
      lines.forEach { lineStr ->
        val results = mutableMapOf(
          "red" to mutableListOf<Int>(),
          "green" to mutableListOf(),
          "blue" to mutableListOf()
        )

        val withoutTitle = lineStr.split(":")[1]
        val replaceSemicolonsSplitCommasTrimSpaces = withoutTitle.replace(";", ",").split(",").map{ it.trim() }

        replaceSemicolonsSplitCommasTrimSpaces.forEach { cubePull ->

          val split = cubePull.split(" ")
          when (split[1]) {
            "green" -> results["green"]!!.add(split[0].toInt())
            "red" -> results["red"]!!.add(split[0].toInt())
            "blue" -> results["blue"]!!.add(split[0].toInt())
          }
        }

        val justMaxes = maxes(results)
        val maxesPower = (justMaxes["red"]!! * justMaxes["green"]!! * justMaxes["blue"]!!)

        powerList.add(maxesPower)
      }
      Logger.d("Power List sum: ${powerList.sum()}")
      answerTV.text = powerList.sum().toString()
    }
  }
  private fun maxes(results: MutableMap<String, MutableList<Int>>): Map<String, Int> {
    val red = results["red"]!!
    val green = results["green"]!!
    val blue = results["blue"]!!

    val redMax = red.maxOrNull()!!
    val greenMax = green.maxOrNull()!!
    val blueMax = blue.maxOrNull()!!

    return mapOf(
      "red" to redMax,
      "green" to greenMax,
      "blue" to blueMax
    )
  }
}