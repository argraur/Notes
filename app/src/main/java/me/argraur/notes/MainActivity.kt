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

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import me.argraur.notes.adapters.NotesAdapter
import me.argraur.notes.entities.Note
import me.argraur.notes.enums.Action
import me.argraur.notes.helpers.NoteActionManager
import me.argraur.notes.helpers.NoteManager
import me.argraur.notes.observers.NoteObserver
import me.argraur.notes.screens.EditNoteActivity

/**
 * MainActivity. Shows previews for already saved notes
 */
class MainActivity : AppCompatActivity(), NoteObserver {
    private lateinit var nothingTextView: TextView
    private lateinit var notesView: RecyclerView
    private lateinit var fab: FloatingActionButton
    private val noteManager = NoteManager.getInstance(null)
    private val noteActionManager = NoteActionManager.getInstance()

    /**
     * Creates Notes view and defines addNote fab action
     * @param savedInstanceState
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        window.allowEnterTransitionOverlap = true
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        nothingTextView = findViewById(R.id.nothingTextView)
        notesView = findViewById(R.id.notesView)
        fab = findViewById(R.id.addNote)
        noteManager.registerObserver(this)
        fab.setOnClickListener {
            startActivity(Intent(this, EditNoteActivity::class.java), ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        }
    }

    /**
     * Called when mNotes of NoteManager was changed
     * Creates and updates RecyclerView
     * @see NoteObserver
     */
    override fun onNotesChanged(mNotes: Array<Note>?) {
        @Suppress("NON_EXHAUSTIVE_WHEN")
        when (NoteActionManager.getInstance().currentAction()) {
            Action.ADD -> Snackbar.make(findViewById(R.id.main_layout), getString(R.string.snackbar_add), Snackbar.LENGTH_SHORT).setBackgroundTint(getColor(R.color.snackBarBackground)).setTextColor(getColor(R.color.foreground)).setAnchorView(fab).show()
            Action.UNDO_DELETE -> Snackbar.make(findViewById(R.id.main_layout), getString(R.string.snackbar_restore), Snackbar.LENGTH_SHORT).setBackgroundTint(getColor(R.color.snackBarBackground)).setTextColor(getColor(R.color.foreground)).setAnchorView(fab).show()
            Action.DELETE -> Snackbar.make(findViewById(R.id.main_layout), getString(R.string.snackbar_delete), Snackbar.LENGTH_SHORT).setBackgroundTint(getColor(R.color.snackBarBackground)).setTextColor(getColor(R.color.foreground)).setAnchorView(fab)
                .setAction(getString(R.string.snackbar_undo)) {
                    noteActionManager.callOnCurrent(Action.UNDO_DELETE)
                }.show()
        }
        nothingTextView.visibility = View.GONE
        notesView.visibility = View.GONE
        if (mNotes!!.isEmpty()) {
            nothingTextView.visibility = View.VISIBLE
        } else {
            notesView.visibility = View.VISIBLE
            findViewById<RecyclerView>(R.id.notesView).apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(this@MainActivity)
                adapter = NotesAdapter(mNotes, this@MainActivity)
            }
        }
    }
}