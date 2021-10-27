package com.example.noteapproom

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapproom.Room.Note
import com.example.noteapproom.Room.NoteDatabase
import com.example.noteapproom.Room.NoteRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var editText: EditText
    lateinit var recyclerView: RecyclerView
    lateinit var submitBtn: Button

    private lateinit var notes: List<Note>

    private val noteDao by lazy {
        NoteDatabase.getDatabase(this).noteDao()
    }

    private val repository by lazy {
        NoteRepository(noteDao)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        notes = listOf()

        editText = findViewById(R.id.tvNewNote)
        recyclerView = findViewById(R.id.rvNotes)
        submitBtn = findViewById(R.id.btSubmit)
        submitBtn.setOnClickListener {
            val userEnter = editText.text.toString()
            addNote(userEnter)
            editText.text.clear()
            editText.clearFocus()
            updateRV()
        }

        getNote()
        updateRV()

    }

    //update the recycler view
    private fun updateRV(){
        recyclerView.adapter = RVadapter(this, notes)
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun addNote(noteText: String){
        CoroutineScope(IO).launch {
            repository.addNote(Note(0, noteText))
        }
    }
    private fun getNote(){
        CoroutineScope(IO).launch {
            val data = async {
                repository.getNote
            }.await()
            if(data.isNotEmpty()){
                notes = data
                updateRV()
            }else{
                Log.e("MainActivity", "Unable to get data")
            }
        }
    }
    private fun editNote(noteText: Note){
        CoroutineScope(IO).launch {
            repository.updateNote(noteText)
        }
    }

    fun deleteNote(noteId: Int){
        CoroutineScope(IO).launch {
            repository.deleteNote(Note(noteId, ""))
        }
    }

    fun raiseDialog(id: Int){
        val dialogBuilder = AlertDialog.Builder(this)
        val updatedNote = EditText(this)
        updatedNote.hint = "Enter new text"
        dialogBuilder
            .setCancelable(false)
            .setPositiveButton("Save", DialogInterface.OnClickListener {
                    _, _ ->
                run {
                    editNote(Note(id,updatedNote.text.toString()))
                    updateRV()
                }
            })
            .setNegativeButton("Cancel", DialogInterface.OnClickListener {
                    dialog, _ -> dialog.cancel()
            })
        val alert = dialogBuilder.create()
        alert.setTitle("Update Note")
        alert.setView(updatedNote)
        alert.show()
    }
}

