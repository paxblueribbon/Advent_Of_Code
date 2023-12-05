package me.paxana.adventofcode.day1

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import me.paxana.adventofcode.R

class Day1PartBActivity : AppCompatActivity() {

  private lateinit var answerTV: TextView

  private val mapOfDigits = mutableMapOf(
    1 to "one",
    2 to "two",
    3 to "three",
    4 to "four",
    5 to "five",
    6 to "six",
    7 to "seven",
    8 to "eight",
    9 to "nine",
    0 to "zero"
  )

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_day1b)

    answerTV = findViewById(R.id.day2answerTV)

    Logger.addLogAdapter(AndroidLogAdapter())

    val list = mutableListOf<Int>()


    assets.open("Day1Input.txt").bufferedReader().useLines { lines ->
      lines.forEach { lineStr ->
        var editableLineStr = lineStr

        // Issue where some text numbers contain characters from other text numbers
        // Rather than replace text numbers, I'm inserting the digit one character into the text

        while (doesContainTextDigit(editableLineStr)) {
          for (digit in mapOfDigits.keys) {
            editableLineStr = interruptTextDigits(editableLineStr, digit)
          }
        }

        val justTheDigits = editableLineStr.filter { lineChar -> lineChar.isDigit() }
        val together = "${justTheDigits.first()}${justTheDigits.last()}".toInt()

        list.add(together)
      }
      answerTV.text = list.sum().toString()
    }
  }

  private fun interruptTextDigits(editableText: String, searchedForDigit: Int): String {
    var editableLocalText = editableText

    val indexOfDigit = editableText.indexOf(mapOfDigits[searchedForDigit] ?: "")
    if (indexOfDigit != -1) {
      val sb = StringBuilder(editableText)
      sb.insert(indexOfDigit + 1, searchedForDigit)
      editableLocalText = sb.toString()
    }
    return editableLocalText
  }

  private fun doesContainTextDigit(line: String): Boolean {
    return mapOfDigits.values.any { line.contains(it) }
  }
}