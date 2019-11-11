package cbuelter.android.dev.weeklyhabitsgoal

import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.TableLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    val tableLayout by lazy { TableLayout(this) }
    private lateinit var habitViewModel: HabitViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Read habits from database.
        var tableCreated = false
        habitViewModel = ViewModelProvider(this).get(HabitViewModel::class.java)
        habitViewModel.allHabits.observe(this, Observer { habits ->
            // Create the table.
            val habitNames = habits.map { it.habit }

            val params = TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            tableLayout.apply {
                layoutParams = params
                isShrinkAllColumns = true
            }
            createTable(this, tableLayout, habitNames)
            mainLinearLayout.addView(tableLayout)
        })
    }
}
