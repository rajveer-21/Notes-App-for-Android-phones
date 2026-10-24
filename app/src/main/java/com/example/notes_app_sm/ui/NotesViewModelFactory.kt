package com.example.notes_app_sm.ui
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModel
import com.example.notes_app_sm.data.*

class NotesViewModelFactory(private val notesRepository: NotesRepository) : ViewModelProvider.Factory
{
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T
    {
        if(modelClass.isAssignableFrom(NotesViewModel::class.java))
        {
            return NotesViewModel(notesRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
