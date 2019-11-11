package cbuelter.android.dev.weeklyhabitsgoal

import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import kotlinx.android.synthetic.main.activity_main.*


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
        val fontawesomeRegular = ResourcesCompat.getFont(this, R.font.fa_regular_400)

        for (rowCounter in 0 until rows) {
            val row = TableRow(this)
            row.layoutParams = TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.MATCH_PARENT,
                1.0f
            )
            row.setGravity(Gravity.CENTER);

            for (columnCounter in 0 until cols) {
                tableLayout.setColumnStretchable(columnCounter, true)
                if (columnCounter == 0) {
                    val textView = TextView(this)
                    var day = ""
                    when (rowCounter) {
                        0 -> day = getString(R.string.monday)
                        1 -> day = getString(R.string.tuesday)
                        2 -> day = getString(R.string.wednesday)
                        3 -> day = getString(R.string.thursday)
                        4 -> day = getString(R.string.friday)
                        5 -> day = getString(R.string.saturday)
                        6 -> day = getString(R.string.sunday)
                    }
                    textView.text = day
                    textView.setGravity(Gravity.RIGHT);
                    row.addView(textView)
                } else {
                    val toggleButton = ToggleButton(this)
                    var buttonTextOn = ""
                    var buttonColorOn = Color.RED
                    when (columnCounter) {
                        1 -> {
                            buttonTextOn = getString(R.string.fa_user)
                            buttonColorOn = Color.RED
                        }
                        2 -> {
                            buttonTextOn = getString(R.string.fa_star)
                            buttonColorOn = Color.BLUE
                        }
                        3 -> {
                            buttonTextOn = getString(R.string.fa_heart)
                            buttonColorOn = Color.GREEN
                        }
                    }
                    toggleButton.apply {
                        textOn = buttonTextOn
                        textOff = ""
                        isChecked = false
                        layoutParams =
                            TableRow.LayoutParams(
                                TableRow.LayoutParams.WRAP_CONTENT,
                                TableRow.LayoutParams.WRAP_CONTENT
                            )
                        typeface = fontawesomeRegular
                    }
                    toggleButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22f)
                    toggleButton.setTextColor(buttonColorOn)
                    toggleButton.setBackgroundColor(Color.TRANSPARENT)
                    toggleButton.setOnCheckedChangeListener { self, isChecked ->
                        //if (!isChecked) {
                        //    return@setOnCheckedChangeListener
                        //}
                        // Do something here
                    }
                    row.addView(toggleButton)
                }
            }
            tableLayout.addView(row)
        }
        mainLinearLayout.addView(tableLayout)
    }
}
