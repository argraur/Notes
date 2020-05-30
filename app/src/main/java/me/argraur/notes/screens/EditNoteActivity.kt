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
import com.google.android.material.textfield.TextInputLayout
import me.argraur.notes.R
import me.argraur.notes.entities.Note
import me.argraur.notes.enums.Action
import me.argraur.notes.helpers.NoteActionManager

class EditNoteActivity : AppCompatActivity() {
    private var color: Int? = null
    private var noteActionManager = NoteActionManager.getInstance()
    private lateinit var titleInput: TextInputEditText
    private lateinit var valueInput: TextInputEditText
    private lateinit var textInputLayout: TextInputLayout
    private lateinit var saveNoteFab: FloatingActionButton
    companion object {
        const val IS_EDIT = "me.argraur.notes.IS_EDIT"
    }

    /**
     * @see AppCompatActivity.onCreate
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        window.allowEnterTransitionOverlap = true
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_note)
        titleInput = findViewById(R.id.title_input)
        valueInput = findViewById(R.id.value_input)
        textInputLayout = findViewById(R.id.textInputLayout)
        saveNoteFab = findViewById(R.id.saveNote)
        color = getColor(R.color.colorNote0)
        if (!intent.getBooleanExtra(IS_EDIT, false)) {
            saveNoteFab.setOnClickListener {
                if (!checkTitle()) return@setOnClickListener
                save(titleInput.text.toString(), valueInput.text.toString(), color!!)
            }
        } else {
            val note = noteActionManager.current()
            color = note.getColor()
            findViewById<TextView>(R.id.new_note).setText(R.string.edit_note)
            titleInput.setText(note.getTitle())
            valueInput.setText(note.getValue())
            saveNoteFab.setOnClickListener {
                if (!checkTitle()) return@setOnClickListener
                delete(note)
                save(titleInput.text.toString(), valueInput.text.toString(), color!!)
            }
        }
    }

    /**
     * Creates note object based on params
     * Saves it into database
     * And finishes EditNoteActivity
     * @param title Note's title
     * @param value Note's content
     * @param color Note's color in int format
     */
    private fun save(title: String, value: String, color: Int) {
        noteActionManager.call(Action.ADD, Note(title, value, color))
        super.onBackPressed()
    }

    /**
     * Deletes note by given creation time
     * @param note A Note object ^_^
     */
    private fun delete(note: Note) = noteActionManager.call(Action.DELETE, note)

    /**
     * Checks if title is empty and
     * Adds error to textInputLayout if title is empty
     * @return Whether title is empty or not (false, true)
     */
    private fun checkTitle(): Boolean {
        return if (titleInput.text.toString().isEmpty()) {
            textInputLayout.error = "Title can't be empty!"
            false
        } else true
    }

    /**
     * Called by color FABs
     * Sets note color and sets check-mark drawable on button
     * @param view View where button lays
     */
    fun setColor(view: View) {
        resetImages()
        view as FloatingActionButton
        color = view.backgroundTintList!!.defaultColor
        view.setImageDrawable(getDrawable(R.drawable.ic_outline_done))
    }

    /**
     * Removes check-mark drawable from all color FABs
     */
    private fun resetImages() {
        findViewById<FloatingActionButton>(R.id.color1).setImageDrawable(null)
        findViewById<FloatingActionButton>(R.id.color2).setImageDrawable(null)
        findViewById<FloatingActionButton>(R.id.color3).setImageDrawable(null)
    }

    /**
     * Called by back floating action button
     * Finishes EditNoteActivity lifecycle
     * @param view View where button lays
     */
    fun back(@Suppress("UNUSED_PARAMETER") view: View) = super.onBackPressed()
}