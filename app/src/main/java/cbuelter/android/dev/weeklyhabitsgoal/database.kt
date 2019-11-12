package cbuelter.android.dev.weeklyhabitsgoal

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Entity(tableName = "habit_table")
class Habit(
    @PrimaryKey @ColumnInfo(name = "habit") val habit: String
)

@Dao
interface HabitDao {
    @Query("SELECT * from habit_table ORDER BY habit ASC")
    fun getAlphabetizedHabits(): LiveData<List<Habit>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(habit: Habit)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(vararg habit: Habit)

    @Query("DELETE FROM habit_table")
    suspend fun deleteAll()
}

@Database(entities = arrayOf(Habit::class), version = 1)
public abstract class HabitRoomDatabase : RoomDatabase() {

    abstract fun habitDao(): HabitDao

    private class HabitDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch {
                    val habitDao = database.habitDao()

                    habitDao.deleteAll()

//                    val habitNames: Array<String> = arrayOf("Meditation", "Reading", "Workout")
//                    val habits = habitNames.map { Habit(it) }
//                    habitDao.insertAll(*habits.toTypedArray())
                }
            }
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: HabitRoomDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): HabitRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HabitRoomDatabase::class.java,
                    "habit_database"
                )
                    .addCallback(HabitDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}

class HabitRepository(private val habitDao: HabitDao) {
    val allHabits: LiveData<List<Habit>> = habitDao.getAlphabetizedHabits()

    suspend fun insert(habit: Habit) {
        habitDao.insert(habit)
    }
}

class HabitViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: HabitRepository
    val allHabits: LiveData<List<Habit>>

    init {
        val habitDao = HabitRoomDatabase.getDatabase(application, viewModelScope).habitDao()
        repository = HabitRepository(habitDao)
        allHabits = repository.allHabits
    }

    fun insert(habit: Habit) = viewModelScope.launch {
        repository.insert(habit)
    }
}