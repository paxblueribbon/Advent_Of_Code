package me.paxana.adventofcode.day1

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import me.paxana.adventofcode.R
import me.paxana.adventofcode.toList

class Day1PartAActivity : AppCompatActivity() {
  private lateinit var answerTV: TextView

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_day1a)

    answerTV = findViewById(R.id.day1answerTV)

    val list = mutableListOf<Int>()

    assets.open("Day1Input.txt").toList().forEach { lineStr ->
      val justTheDigits = lineStr.filter { lineChar -> lineChar.isDigit() }
      val together = "${justTheDigits.first()}${justTheDigits.last()}".toInt()
      list.add(together)
    }

    answerTV.text = list.sum().toString()

  }
}