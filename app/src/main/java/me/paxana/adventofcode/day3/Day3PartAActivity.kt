package me.paxana.adventofcode.day3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.core.text.isDigitsOnly
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import me.paxana.adventofcode.R

class Day3PartAActivity : AppCompatActivity() {

  private val symbolCoordinates = mutableListOf<Pair<Int, Int>>()
  lateinit var answerTV: TextView

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_day3a)
    Logger.addLogAdapter(AndroidLogAdapter())
    //TODO: Standardize these answer IDs
    answerTV = findViewById(R.id.answerDay5TV)

    val matrix = turnTextFileIntoMatrix()
    val fullNumberCoordinatesList = mutableListOf<Pair<Int, Pair<Int, Int>>>()

    val acceptedNumbers = mutableListOf<Int>()

    symbolCoordinates.forEach { pair ->
      // TODO: Can this be done in shorter O notation time?
      for (i in pair.first - 1..pair.first + 1) {
        for (j in pair.second - 1..pair.second + 1) {
          if (i >= 0 && j >= 0 && i <= matrix.size && j <= matrix[i].size) {
            if (matrix[i][j].isDigitsOnly()) {
              fullNumberCoordinatesList.add(Pair(i, findStartAndEndOfNumber(matrix, i, j)))
            }
          }
        }
      }
    }
    Logger.d("Full number coordinates list: $fullNumberCoordinatesList")
      fullNumberCoordinatesList.distinct().forEach { pair ->
        val number = matrix[pair.first].subList(pair.second.first, pair.second.second + 1).joinToString("").toInt()
        acceptedNumbers.add(number)
      }
    Logger.d("Accepted numbers: $acceptedNumbers")
    Logger.d("Sum of accepted numbers: ${acceptedNumbers.sum()}")

    answerTV.text = acceptedNumbers.sum().toString()


  }

  private fun findStartAndEndOfNumber(matrix: MutableList<MutableList<String>>, i: Int, j: Int): Pair<Int, Int> {
    var start = j
    var end = j
    Logger.d("Line we're on: ${matrix[i]}")
      while (start > 0 && matrix[i][start - 1].isDigitsOnly()) {
        start--
      }

    while (end < matrix[i].size - 1 && matrix[i][end + 1].isDigitsOnly()) {
      if (end < matrix[i].size){
        end++
      }
    }
    return Pair(start, end)
  }

  private fun turnTextFileIntoMatrix(): MutableList<MutableList<String>>{
    val matrix = mutableListOf<MutableList<String>>()
    assets.open("Day3Input.txt").bufferedReader().useLines { lines ->
      lines.forEachIndexed { index, lineStr ->
        val split = lineStr.split("").drop(1)
        for (j in split.indices) {
          //TODO: Do this as regex
          if (split[j] == "*" || split[j] == "@" || split[j] == "$" || split[j] == "+" || split[j] == "#" || split[j] == "/" || split[j] == "=" || split[j] == "%" || split[j] == "&" || split[j] == "-")   {
            symbolCoordinates.add(Pair(index, j))
          }
        }
        val row = mutableListOf<String>()
        split.forEach { row.add(it) }
        matrix.add(row)
      }
      return matrix
    }
  }
}