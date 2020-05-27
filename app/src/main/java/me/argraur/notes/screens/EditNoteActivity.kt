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

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import me.argraur.notes.R
import me.argraur.notes.adapters.NOTE_TIME
import me.argraur.notes.adapters.NOTE_TITLE
import me.argraur.notes.adapters.NOTE_VALUE
import me.argraur.notes.entities.Note
import me.argraur.notes.helpers.NoteHelper

class EditNoteActivity : AppCompatActivity() {
    private var color: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_note)
        color = getColor(R.color.colorNote0)
        if (!intent.getBooleanExtra("me.argraur.notes.IS_EDIT", false)) {
            findViewById<FloatingActionButton>(R.id.saveNote).setOnClickListener {
                val title = findViewById<TextInputEditText>(R.id.title_input).text.toString()
                val value = findViewById<TextInputEditText>(R.id.value_input).text.toString()
                if (title == "" && value == "") {
                    finish()
                    return@setOnClickListener
                }
                NoteHelper(this).putNote(Note(title, value, color!!))
                finish()
            }
        } else {
            findViewById<TextView>(R.id.new_note).setText(R.string.edit_note)
            val titleInput = findViewById<TextInputEditText>(R.id.title_input)
            val valueInput = findViewById<TextInputEditText>(R.id.value_input)
            titleInput.setText(intent.getStringExtra(NOTE_TITLE))
            valueInput.setText(intent.getStringExtra(NOTE_VALUE))
            val time = intent.getLongExtra(NOTE_TIME, 0L)
            val noteHelper = NoteHelper(this)
            findViewById<FloatingActionButton>(R.id.saveNote).setOnClickListener {
                val title = titleInput.text.toString()
                val value = valueInput.text.toString()
                if (title == "" && value == "") {
                    finish()
                    return@setOnClickListener
                }
                noteHelper.deleteNote(time)
                NoteHelper(this).putNote(Note(title, value, color!!))
                finish()
            }
        }
    }

    fun setColor(view: View) {
        resetImages()
        view as FloatingActionButton
        color = view.backgroundTintList!!.defaultColor
        view.setImageDrawable(getDrawable(R.drawable.ic_outline_done))
    }

    private fun resetImages() {
        findViewById<FloatingActionButton>(R.id.color1).setImageDrawable(null)
        findViewById<FloatingActionButton>(R.id.color2).setImageDrawable(null)
        findViewById<FloatingActionButton>(R.id.color3).setImageDrawable(null)
    }

    fun back(view: View) {
        finish()
    }
}