package cbuelter.android.dev.weeklyhabitsgoal

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TableLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val tableLayout by lazy { TableLayout(this) }
    private lateinit var habitViewModel: HabitViewModel
    private val MANAGE_HABITS_ACTIVITY_REQUEST_CODE = 1

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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.getItemId()

        if (id == R.id.manage_habits) {
            val intent = Intent(this@MainActivity, ManageHabitsActivity::class.java)
            startActivityForResult(intent, MANAGE_HABITS_ACTIVITY_REQUEST_CODE)
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
