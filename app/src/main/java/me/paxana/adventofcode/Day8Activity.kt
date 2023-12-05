package me.paxana.adventofcode

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger

class Day8Activity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_day8)
    Logger.addLogAdapter(AndroidLogAdapter())
    val scratchCardList = mutableListOf<ScratchCard>()

    assets.open("Day4Input.txt").bufferedReader().useLines { lines ->
      lines.forEachIndexed { index, line ->

        val noTitle = line.split(":")[1]
        val twoStrings = noTitle.split("|").map {
          it.replace("  ", " ").split(" ").map {
            it.trim() }.drop(1)
        }
        Logger.d("Two strings: $twoStrings")
        val scratchCard = ScratchCard(index, 1, twoStrings[0].dropLast(1), twoStrings[1], 0)
        Logger.d("Scratch Card: $scratchCard")
        scratchCardList.add(fillInScratchCardData(scratchCard))
        Logger.d("Two strings: $twoStrings")
        val originalSecondList = twoStrings[1]
        val ts2 = twoStrings[1].toMutableList()

        Logger.d("Original list: $originalSecondList, ts2: $ts2")
      }

//      Logger.d("Scratch Card List: $scratchCardList")
    }
      scratchCardList.forEach {
        if (it.points > 0) {
          for (i in it.id + 1 .. it.id + it.points ) {
            scratchCardList[i].copies = scratchCardList[i].copies?.plus(it.copies ?: 0)
            Logger.d("Scratch card: ${it.id + 1} has ${it.points} matches, so ${scratchCardList[i].id + 1} gets ${it.copies} copies")
          }
        }
      }
    Logger.d("Scratch Card List: $scratchCardList")
    val totalCopies = scratchCardList.sumOf { it.copies ?: 0 }
    Logger.d("Total Copies: $totalCopies")
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