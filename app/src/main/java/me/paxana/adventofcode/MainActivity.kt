package me.paxana.adventofcode

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.airbnb.lottie.LottieAnimationView
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import me.paxana.adventofcode.day1.Day1PartAActivity
import me.paxana.adventofcode.day1.Day1PartBActivity
import me.paxana.adventofcode.day2.Day2PartAActivity
import me.paxana.adventofcode.day2.Day2PartBActivity
import me.paxana.adventofcode.day3.Day3PartAActivity
import me.paxana.adventofcode.day3.Day3PartBActivity
import me.paxana.adventofcode.day4.Day4PartAActivity

class MainActivity : AppCompatActivity() {

  private lateinit var snowView: LottieAnimationView

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    Logger.addLogAdapter(AndroidLogAdapter())

    snowView = findViewById(R.id.snowView)

    snowView.playAnimation()

    val spinner = findViewById<Spinner>(R.id.dayChoiceSpinner)

    val currentDays = arrayOf("Select Day", "Day 1A", "Day 1B", "Day 2A", "Day 2B", "Day 3A", "Day 3B", "Day 4A")

    ArrayAdapter(this, android.R.layout.simple_spinner_item, currentDays).also {
      adapter ->
      adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
      spinner.adapter = adapter
    }

    spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
      override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        when (parent.getItemAtPosition(position).toString()) {
          "Select Day" -> {
            Logger.d("Select Day selected")
          }
          "Day 1A" -> {
            startActivity(Intent(this@MainActivity, Day1PartAActivity::class.java))
          }
          "Day 1B" -> {
            startActivity(Intent(this@MainActivity, Day1PartBActivity::class.java))
          }
          "Day 2A" -> {
            startActivity(Intent(this@MainActivity, Day2PartAActivity::class.java))
          }
          "Day 2B" -> {
            startActivity(Intent(this@MainActivity, Day2PartBActivity::class.java))
          }
          "Day 3A" -> {
            startActivity(Intent(this@MainActivity, Day3PartAActivity::class.java))
          }
          "Day 3B" -> {
            startActivity(Intent(this@MainActivity, Day3PartBActivity::class.java))
          }
          "Day 4A" -> {
            startActivity(Intent(this@MainActivity, Day4PartAActivity::class.java))
          }
        }
      }

      override fun onNothingSelected(parent: AdapterView<*>) {
        // write code to perform some action
      }
    }


  }
}