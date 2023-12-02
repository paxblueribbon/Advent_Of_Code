package me.paxana.adventofcode

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger

class Day1Activity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_day_1)

    Logger.addLogAdapter(AndroidLogAdapter())

    var list = mutableListOf<Int>()

    assets.open("Day1Input.txt").bufferedReader().useLines { lines ->
      lines.forEach { lineStr ->

        val justTheDigits = lineStr.filter { lineChar -> lineChar.isDigit() }
        val first = justTheDigits.first()
        val last = justTheDigits.last()
        val together = "$first$last"
        val togetherInt = together.toInt()

        Logger.d("Lines: $lineStr, Digits: $justTheDigits, First: $first, Last: $last, Together: $together, Together Int: $togetherInt")

        list.add(togetherInt)
        }
      }
    }
  }