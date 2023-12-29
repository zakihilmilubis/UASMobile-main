package repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import database.Note
import database.NoteDao
import database.NoteRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class NoteRepository(application: Application) {
    private var mNoteDao:NoteDao

    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = NoteRoomDatabase.getDatabase(application)
        mNoteDao = db.noteDao()
    }

    private val insertionStatus = MutableLiveData<Boolean>()

    fun insertIfNotExists(name: String, ticker: String, img: String) {
        executorService.execute {
            val existingNote = mNoteDao.getNoteByNameLevelAndImg(name, ticker, img)
            if (existingNote == null) {
                val newNote = Note(name = name, ticker = ticker, img = img)
                mNoteDao.insert(newNote)
                insertionStatus.postValue(true)
            }
            else {
                insertionStatus.postValue(false)
            }
        }
    }

    fun deleteExistingNote(name: String, ticker: String, img: String) {
        executorService.execute {
            val existingNote = mNoteDao.getNoteByNameLevelAndImg(name, ticker, img)
            existingNote?.let {
                mNoteDao.delete(it)
            }
        }
    }

    fun update(note: Note) {
        executorService.execute {
            mNoteDao.update(note)
        }
    }
}