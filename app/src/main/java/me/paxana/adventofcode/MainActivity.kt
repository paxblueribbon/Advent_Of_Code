package me.paxana.adventofcode

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    Logger.addLogAdapter(AndroidLogAdapter())

    val spinner = findViewById<Spinner>(R.id.dayChoiceSpinner)

    val currentDays = arrayOf("Select Day", "Day 1", "Day 2", "Day 3", "Day 4", "Day 5", "Day 6")

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
          "Day 1" -> {
            startActivity(Intent(this@MainActivity, Day1Activity::class.java))
          }
          "Day 2" -> {
            startActivity(Intent(this@MainActivity, Day2Activity::class.java))
          }
          "Day 3" -> {
            startActivity(Intent(this@MainActivity, Day3Activity::class.java))
          }
          "Day 4" -> {
            startActivity(Intent(this@MainActivity, Day4Activity::class.java))
          }
          "Day 5" -> {
            startActivity(Intent(this@MainActivity, Day5Activity::class.java))
          }
          "Day 6" -> {
            startActivity(Intent(this@MainActivity, Day6Activity::class.java))
          }
        }
      }

      override fun onNothingSelected(parent: AdapterView<*>) {
        // write code to perform some action
      }
    }


  }
}