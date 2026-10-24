package com.example.notes_app_sm.data
import kotlinx.coroutines.flow.Flow
class NotesRepository(private val noteDao : NoteDao)
{
    fun getAllNotes() : Flow<List<NoteEntity>>
    {
        return noteDao.getAllNotes()
    }
    fun getByQuery(query : String) : Flow<List<NoteEntity>>
    {
        return noteDao.getByQuery(query)
    }
    fun getByNoteId(id : Int) : NoteEntity?
    {
        return noteDao.getByNoteId(id)
    }
    suspend fun insertNote(note : NoteEntity)
    {
        noteDao.insertNote(note)
    }
    suspend fun updateNote(note : NoteEntity)
    {
        noteDao.updateNote(note)
    }
    suspend fun deleteNote(note : NoteEntity)
    {
        noteDao.deleteNote(note)
    }
    suspend fun changePinStatus(id : Int, isPinned : Boolean)
    {
        noteDao.changePinStatus(id = id, isPinned = isPinned, updatedAt = System.currentTimeMillis())
    }
}