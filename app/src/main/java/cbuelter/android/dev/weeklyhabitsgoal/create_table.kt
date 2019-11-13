package cbuelter.android.dev.weeklyhabitsgoal

import android.app.Activity
import android.graphics.Color
import android.view.Gravity
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.ToggleButton
import androidx.core.content.res.ResourcesCompat


fun createTable(mainActivity: Activity, tableLayout: TableLayout, habits: List<String>) {
    val fontawesomeRegular = ResourcesCompat.getFont(mainActivity, R.font.fa_regular_400)
    val numWeekDays = 7

    val tableLayoutParams =
        TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    tableLayout.apply {
        layoutParams = tableLayoutParams
        isShrinkAllColumns = true
    }

    for (rowCounter in 0 until numWeekDays) {
        val row = TableRow(mainActivity)
        row.layoutParams = TableLayout.LayoutParams(
            TableLayout.LayoutParams.MATCH_PARENT,
            TableLayout.LayoutParams.MATCH_PARENT,
            1.0f
        )
        row.setGravity(Gravity.CENTER);

        for (columnCounter in 0 until habits.count() + 1) {
            tableLayout.setColumnStretchable(columnCounter, true)
            if (columnCounter == 0) {
                val textView = TextView(mainActivity)
                var day = ""
                when (rowCounter) {
                    0 -> day = mainActivity.getString(R.string.monday)
                    1 -> day = mainActivity.getString(R.string.tuesday)
                    2 -> day = mainActivity.getString(R.string.wednesday)
                    3 -> day = mainActivity.getString(R.string.thursday)
                    4 -> day = mainActivity.getString(R.string.friday)
                    5 -> day = mainActivity.getString(R.string.saturday)
                    6 -> day = mainActivity.getString(R.string.sunday)
                }
                textView.text = day
                textView.setGravity(Gravity.RIGHT);
                row.addView(textView)
            } else {
                val toggleButton = ToggleButton(mainActivity)
//                var buttonTextOn = ""
                val buttonTextOn = habits[columnCounter - 1]
//                val buttonColorOn = Color.RED

//                when (columnCounter) {
//                    1 -> {
//                        buttonTextOn = mainActivity.getString(R.string.fa_user)
//                        buttonColorOn = Color.RED
//                    }
//                    2 -> {
//                        buttonTextOn = mainActivity.getString(R.string.fa_star)
//                        buttonColorOn = Color.BLUE
//                    }
//                    3 -> {
//                        buttonTextOn = mainActivity.getString(R.string.fa_heart)
//                        buttonColorOn = Color.GREEN
//                    }
//                }
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
//                toggleButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22f)
//                toggleButton.setTextColor(buttonColorOn)
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
}