package me.paxana.adventofcode.day1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import me.paxana.adventofcode.R

class Day1PartAActivity : AppCompatActivity() {
  lateinit var answerTV: TextView

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_day1a)

    answerTV = findViewById(R.id.day1answerTV)

    Logger.addLogAdapter(AndroidLogAdapter())

    val list = mutableListOf<Int>()

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
      answerTV.text = list.sum().toString()
      }
    }
  }