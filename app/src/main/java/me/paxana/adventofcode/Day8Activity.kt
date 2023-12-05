package me.paxana.adventofcode

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger

class Day8Activity : AppCompatActivity() {

  private lateinit var answer: TextView

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_day8)
    Logger.addLogAdapter(AndroidLogAdapter())
    answer = findViewById(R.id.day8Answer)
    val scratchCardList = mutableListOf<ScratchCard>()

    assets.open("Day4Input.txt").bufferedReader().useLines { lines ->
      lines.forEachIndexed { index, line ->
        val card = line.split(":")[1].split("|").map { list ->
          list.replace("  ", " ").split(" ").map {
            it.trim() }.drop(1)
        }

        val scratchCard = ScratchCard(index, 1, card[0].dropLast(1), card[1], 0)
        scratchCardList.add(fillInScratchCardData(scratchCard))
      }
    }
      scratchCardList.forEach {
        if (it.points > 0) {
          for (i in it.id + 1 .. it.id + it.points ) {
            scratchCardList[i].copies = scratchCardList[i].copies?.plus(it.copies ?: 0)
          }
        }
      }
    val totalCopies = scratchCardList.sumOf { it.copies ?: 0 }
    answer.text = totalCopies.toString()
    }

  private fun fillInScratchCardData(scratchCard: ScratchCard): ScratchCard {
    val modifiable = scratchCard.searchNumbers.toMutableList()
    var count = 0
    scratchCard.keyNumbers.forEach {
      while (modifiable.contains(it)) {
        modifiable.remove(it)
        count++
      }
    }

    return ScratchCard(scratchCard.id, scratchCard.copies, scratchCard.keyNumbers, scratchCard.searchNumbers, count )
  }
  data class ScratchCard(val id: Int, var copies: Int? = 0, val keyNumbers: List<String>, val searchNumbers: List<String>, val points: Int)
}