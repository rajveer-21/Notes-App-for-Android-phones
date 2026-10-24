package com.example.notes_app_sm.data
import androidx.room.Dao
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao
{
    @Query("""
        SELECT * FROM notes_table ORDER BY isPinned DESC, updatedAt DESC
    """)
    fun getAllNotes() : Flow<List<NoteEntity>>

    @Query("""
        SELECT * FROM notes_table WHERE title LIKE '%' || :query || '%' OR 
        content LIKE '%' || :query ||'%' ORDER BY isPinned DESC, updatedAt DESC
    """)
    fun getByQuery(query : String) : Flow<List<NoteEntity>>

    @Query("""
        SELECT * FROM notes_table WHERE id = :id
    """)
    fun getByNoteId(id : Int) : NoteEntity?

    @Insert
    suspend fun insertNote(note : NoteEntity)

    @Update
    suspend fun updateNote(note : NoteEntity)

    @Delete
    suspend fun deleteNote(note : NoteEntity)

    @Query("""
        UPDATE notes_table SET updatedAt = :updatedAt, isPinned = :isPinned 
        WHERE id = :id
    """)
    suspend fun changePinStatus(updatedAt : Long, isPinned : Boolean, id : Int)
}