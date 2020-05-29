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

package me.argraur.notes.screens

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.card.MaterialCardView
import me.argraur.notes.R
import me.argraur.notes.adapters.NOTE_COLOR
import me.argraur.notes.adapters.NOTE_TIME
import me.argraur.notes.adapters.NOTE_TITLE
import me.argraur.notes.adapters.NOTE_VALUE
import me.argraur.notes.helpers.NoteManager
import me.argraur.notes.screens.EditNoteActivity.Companion.IS_EDIT

class NoteActivity : AppCompatActivity() {
    /**
     * Gets Note contents from intent extras
     * And updates views according to those contents
     * @see AppCompatActivity.onCreate
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        window.allowEnterTransitionOverlap = true
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)
        findViewById<TextView>(R.id.title_view).text = intent.getStringExtra(NOTE_TITLE)
        findViewById<TextView>(R.id.value_view).text = intent.getStringExtra(NOTE_VALUE)
        findViewById<MaterialCardView>(R.id.note_cardview).setCardBackgroundColor(intent.getIntExtra(NOTE_COLOR, 0))
    }

    /**
     * Called by edit button.
     * Starts EditNoteActivity with IS_EDIT set to true
     * And passes all contents of the Note object
     * @param view Button's view
     */
    fun edit(@Suppress("UNUSED_PARAMETER") view: View) {
        startActivity(Intent(this, EditNoteActivity::class.java).apply {
            putExtra(NOTE_TITLE, intent.getStringExtra(NOTE_TITLE))
            putExtra(NOTE_VALUE, intent.getStringExtra(NOTE_VALUE))
            putExtra(NOTE_TIME, intent.getLongExtra(NOTE_TIME, 0L))
            putExtra(NOTE_COLOR, intent.getIntExtra(NOTE_COLOR, 0))
            putExtra(IS_EDIT, true)
        })
        finish()
    }

    /**
     * Called by delete button.
     * Deletes note using NOTE_TIME intent extra
     * @param view Button's view
     */
    fun delete(@Suppress("UNUSED_PARAMETER") view: View) {
        NoteManager.getInstance(null).deleteNote(intent.getLongExtra(NOTE_TIME, 0))
        super.onBackPressed()
    }

    override fun onRestart() {
        super.onRestart()
        finish()
    }

    /**
     * Called by back floating action button
     * Finishes NoteActivity lifecycle
     * @param view View where button lays
     */
    fun back(@Suppress("UNUSED_PARAMETER") view: View) = super.onBackPressed()
}