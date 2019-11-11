package cbuelter.android.dev.weeklyhabitsgoal

import android.os.Bundle
import android.view.ViewGroup
import android.widget.TableLayout
import androidx.appcompat.app.AppCompatActivity
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

        createTable(this, tableLayout, numRows, numColumns)
        mainLinearLayout.addView(tableLayout)
    }
}
