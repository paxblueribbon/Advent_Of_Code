package me.paxana.adventofcode.day4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import me.paxana.adventofcode.R
import kotlin.math.pow

class Day4PartAActivity : AppCompatActivity() {
  private lateinit var answer: TextView

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_day4a)
    Logger.addLogAdapter(AndroidLogAdapter())

    answer = findViewById(R.id.answerDay7)
    val points = mutableListOf<Int>()
    val bigPoints = mutableListOf<Int>()

    //TODO: Make this an extension function
    assets.open("Day4Input.txt").bufferedReader().useLines { lines ->
      lines.forEach { line ->
        val noTitle = line.split(":")[1]
        val twoStrings = noTitle.split("|").map { list ->
          list.replace("  ", " ").split(" ").map {
            it.trim() }.drop(1)
        }
        Logger.d("Two strings: $twoStrings")
        val originalSecondList = twoStrings[1]
        val ts = twoStrings[0]
        val ts2 = twoStrings[1].toMutableList()

        ts.forEach {
          var count = 0
          while (ts2.contains(it)) {
            ts2.remove(it)
            count++
          }
        }
        points.add(originalSecondList.size - ts2.size)
      }
      Logger.d("Points: $points")
      Logger.d("Points sum: ${points.sum()}")
      //TODO: Remove zeroes before forEaching
      points.forEach {
        if (it > 1)  { bigPoints.add(2.0.pow(it - 1).toInt())}
        else { bigPoints.add(it) }
      }
    }
    answer.text = bigPoints.sum().toString()

      }
}