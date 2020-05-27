/*
 * Copyright (C) 2020 Arseniy Graur
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.argraur.notes

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import me.argraur.notes.adapters.NotesAdapter
import me.argraur.notes.helpers.NoteHelper
import me.argraur.notes.screens.EditNoteActivity

class MainActivity : AppCompatActivity() {
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val newNoteFab = findViewById<FloatingActionButton>(R.id.addNote)
        newNoteFab.setOnClickListener {
            val newNoteIntent = Intent(this, EditNoteActivity::class.java)
            startActivity(newNoteIntent)
        }
    }

    private fun updateNotes() {
        val nothingTextView = findViewById<TextView>(R.id.nothingTextView)
        val notesView = findViewById<RecyclerView>(R.id.notesView)
        nothingTextView.visibility = View.GONE
        notesView.visibility = View.GONE
        val noteHelper = NoteHelper(this)
        viewManager = LinearLayoutManager(this)
        val notes = noteHelper.getNotes()
        if (notes == null) {
            nothingTextView.visibility = View.VISIBLE
        } else {
            notesView.visibility = View.VISIBLE
            findViewById<RecyclerView>(R.id.notesView).apply {
                setHasFixedSize(true)
                layoutManager = viewManager
                adapter = NotesAdapter(notes, this@MainActivity)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        updateNotes()
    }

    fun back(view: View) {
        finish()
    }
}