package database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(note:Note)

    @Update
    fun update(note:Note)

    @Delete
    fun delete(note:Note)

    @Query("SELECT * FROM Note")
    fun getAllNotes(): List<Note>

    @Query("SELECT * FROM Note WHERE name = :name AND ticker = :ticker AND img = :img")
    fun getNoteByNameLevelAndImg(name: String, ticker: String, img: String): Note?
}