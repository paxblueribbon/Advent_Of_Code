package me.paxana.adventofcode.day1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import me.paxana.adventofcode.R

class Day1PartBActivity : AppCompatActivity() {

  lateinit var answerTV: TextView

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_day1b)

    answerTV = findViewById(R.id.day2answerTV)

    Logger.addLogAdapter(AndroidLogAdapter())

    val list = mutableListOf<Int>()
    val listOfDigits = mutableListOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)

    assets.open("Day1Input.txt").bufferedReader().useLines { lines ->
      lines.forEach { lineStr ->
        var editableLineStr = lineStr

        // Issue where some text numbers contain characters from other text numbers
        // Rather than replace text numbers, I'm inserting the digit one character into the text

        while (doesContainTextDigit(editableLineStr)) {
          for (digit in listOfDigits) {
            editableLineStr = interruptTextDigits(editableLineStr, digit)
          }
        }

        val replaced = editableLineStr
        val justTheDigits = editableLineStr.filter { lineChar -> lineChar.isDigit() }
        val first = justTheDigits.first()
        val last = justTheDigits.last()
        val together = "$first$last"
        val togetherInt = together.toInt()

        Logger.d("Lines: $lineStr, Replaced line str: $replaced Digits: $justTheDigits, First: $first, Last: $last, Together: $together, Together Int: $togetherInt")

        list.add(togetherInt)
      }

      Logger.d("Sum = ${list.sum()}")
      answerTV.text = list.sum().toString()
    }
  }

  private fun interruptTextDigits(editableText: String, searchedForDigit: Int): String {
    var editableLocalText = editableText

    val textVersion = (when (searchedForDigit) {
      1 -> "one"
      2 -> "two"
      3 -> "three"
      4 -> "four"
      5 -> "five"
      6 -> "six"
      7 -> "seven"
      8 -> "eight"
      9 -> "nine"
      0 -> "zero"
      else -> "zero"
    })

    val indexOfDigit = editableText.indexOf(textVersion)
    if (indexOfDigit != -1) {
      val sb = StringBuilder(editableText)
      sb.insert(indexOfDigit + 1, searchedForDigit)
      editableLocalText = sb.toString()
    }
    return editableLocalText
  }

  private fun doesContainTextDigit(line: String): Boolean {
    return line.contains("one") || line.contains("two") || line.contains("three") || line.contains("four") || line.contains("five") || line.contains("six") || line.contains("seven") || line.contains("eight") || line.contains("nine") || line.contains("zero")
  }
}