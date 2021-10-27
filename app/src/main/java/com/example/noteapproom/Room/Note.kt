package com.example.noteapproom.Room

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "NoteTable")
data class Note (
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val noteText: String
    )