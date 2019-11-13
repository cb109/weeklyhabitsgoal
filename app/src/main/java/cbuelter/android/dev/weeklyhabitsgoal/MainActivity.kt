package cbuelter.android.dev.weeklyhabitsgoal

import android.os.Bundle
import android.util.Log
import android.widget.TableLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    val tableLayout by lazy { TableLayout(this) }
    private lateinit var habitViewModel: HabitViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        mainLinearLayout.addView(tableLayout)

        // Read habits from database.
        habitViewModel = ViewModelProvider(this).get(HabitViewModel::class.java)
        habitViewModel.allHabits.observe(this, Observer { habits ->
            if (habits.isEmpty()) {
                // Avoid creating the table before we fetch some data.
                return@Observer
            }

            // Create the table.
            val habitNames = habits.map { it.habit }
            createTable(this, tableLayout, habitNames)
        })
    }
}
