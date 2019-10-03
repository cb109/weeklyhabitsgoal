package cbuelter.android.dev.weeklyhabitsgoal

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.*
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    val tableLayout by lazy { TableLayout(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val params = TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        tableLayout.apply {
            layoutParams = params
            isShrinkAllColumns = true
        }

        val numRows = 7
        val numColumns = 4
        createTable(numRows, numColumns)
    }

    fun createTable(rows: Int, cols: Int) {
        for (rowCounter in 0 until rows) {
            val row = TableRow(this)
            row.layoutParams = TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.MATCH_PARENT,
                1.0f
            )

            for (columnCounter in 0 until cols) {
                tableLayout.setColumnStretchable(columnCounter, true)
                if (columnCounter == 0) {
                    val textView = TextView(this)
                    var day = ""
                    when (rowCounter) {
                        0 -> day = "Monday"
                        1 -> day = "Tuesday"
                        2 -> day = "Wednesday"
                        3 -> day = "Thursday"
                        4 -> day = "Friday"
                        5 -> day = "Saturday"
                        6 -> day = "Sunday"
                    }
                    textView.setText(day)
                    row.addView(textView)
                } else {
                    val toggleButton = ToggleButton(this)
                    var buttonText = ""
                    when (columnCounter) {
                        1 -> buttonText = "Meditate"
                        2 -> buttonText = "Read"
                        3 -> buttonText = "Workout"
                    }
                    toggleButton.apply {
                        textOn = buttonText
                        textOff = ""
                        isChecked = false
                        layoutParams =
                            TableRow.LayoutParams(
                                TableRow.LayoutParams.WRAP_CONTENT,
                                TableRow.LayoutParams.WRAP_CONTENT
                            )
                    }
                    toggleButton.setOnCheckedChangeListener { _, isChecked ->
                        val msg = "Toggle Button is " + if (isChecked) "ON" else "OFF"
                        Toast.makeText(this@MainActivity, msg, Toast.LENGTH_SHORT).show()
                    }
                    row.addView(toggleButton)
                }


//                val button = Button(this)
//                button.apply {
//                    layoutParams = TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
//                        TableRow.LayoutParams.WRAP_CONTENT)
//                    text = "$i x $j"
//                }
//
//                row.addView(button)
            }
            tableLayout.addView(row)
        }
        mainLinearLayout.addView(tableLayout)
    }
}
