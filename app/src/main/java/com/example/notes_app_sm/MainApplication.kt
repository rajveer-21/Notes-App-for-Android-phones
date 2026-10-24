package com.example.notes_app_sm
import android.app.Application
import com.example.notes_app_sm.data.*
class MainApplication : Application(){
    val database by lazy{NotesDatabase.getDatabase(this)}
    val repository by lazy{ NotesRepository(database.getDao())}
}