package com.example.noteapproom.Room

import androidx.room.*


@Dao
//this interface have all operations you want to do on database
//like insert, delete , update and retrieve
interface NoteDao {

    //TO ADD MORE NOTE INTO RV
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addNote(note: Note)

    //RETRIEVE NOTE FROM ROOM DATABASE
    @Query("SELECT * FROM NoteTable ORDER BY id ASC")
    fun retrieveNote(): List<Note>

    //UPDATE THE NOTE
    @Update
    suspend fun updateNote(note: Note)

    //DELETE THE NOTE
    @Delete
    suspend fun deleteNote(note: Note)

}