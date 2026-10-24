package com.example.notes_app_sm.ui
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes_app_sm.data.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class NotesViewModel(val notesRepository: NotesRepository) : ViewModel()
{
    private val searchQuery = MutableStateFlow("")
    val query: StateFlow<String> = searchQuery.asStateFlow()
    val notes: StateFlow<List<NoteEntity>> = searchQuery.flatMapLatest { query->
        if(query.isBlank())
            notesRepository.getAllNotes()
        else
            notesRepository.getByQuery(query)
    }.stateIn(scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
        )

    fun updateSearchQuery(query : String)
    {
        searchQuery.value = query
    }

    fun addNote(title : String, content : String)
    {
        if(title.isBlank() && content.isBlank())
            return;
        viewModelScope.launch { notesRepository.insertNote(NoteEntity(title = title.trim(), content = content.trim()))}
    }

    fun updateNote(id : Int, title : String, content : String)
    {
        viewModelScope.launch{
            val oldNote = notesRepository.getByNoteId(id) ?: return@launch
            notesRepository.updateNote(oldNote.copy(title = title.trim(), content = content.trim(), updatedAt = System.currentTimeMillis()))
        }
    }

    fun deleteNote(note : NoteEntity)
    {
        viewModelScope.launch{notesRepository.deleteNote(note)}
    }

    fun togglePin(note : NoteEntity)
    {
        viewModelScope.launch{notesRepository.changePinStatus(id = note.id, isPinned = !note.isPinned)}
    }

    suspend fun getNote(id : Int): NoteEntity?
    {
        return notesRepository.getByNoteId(id)
    }
}