package me.paxana.adventofcode

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.core.text.isDigitsOnly
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger

class Day6Activity : AppCompatActivity() {
  private val symbolCoordinates = mutableListOf<Pair<Int, Int>>()
  lateinit var answerTV: TextView

  override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_day6)
        Logger.addLogAdapter(AndroidLogAdapter())
        answerTV = findViewById(R.id.day6AnswerTV)

        val matrix = turnTextFileIntoMatrix()

        val fullNumberCoordinateList = mutableListOf<Pair<Int, Pair<Int, Int>>>()

    symbolCoordinates.forEach {
      val neighborCoordinates = mutableListOf<Pair<Int, Pair<Int, Int>>>()
      for (i in it.first - 1..it.first + 1) {
        for (j in it.second - 1..it.second + 1) {
          if (matrix[i][j].isDigitsOnly()) {
            neighborCoordinates.add(Pair(i, findStartAndEndOfNumber(matrix, i, j)))
          }
        }
      }
      if (neighborCoordinates.distinct().size == 2) {
        fullNumberCoordinateList.addAll(neighborCoordinates.distinct())
      }

    }
    val theActualNumbers = fullNumberCoordinateList.chunked(2).map{ coordinates ->
      Pair(getSubstring(matrix, coordinates[0]), getSubstring(matrix, coordinates[1]))
    }

    val multipliedNumbers = theActualNumbers.map { it.first * it.second }
    answerTV.text = multipliedNumbers.sum().toString()
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

  private fun getSubstring(matrix: MutableList<MutableList<String>>, coordinate: Pair<Int, Pair<Int, Int>>): Int {
    val number = matrix[coordinate.first].subList(coordinate.second.first, coordinate.second.second + 1).joinToString("").toInt()
    return number
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