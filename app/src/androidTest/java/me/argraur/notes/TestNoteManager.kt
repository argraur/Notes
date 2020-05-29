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

package me.argraur.notes.helpers

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteException
import android.provider.BaseColumns
import me.argraur.notes.entities.Note

/**
 * Manages Notes and NoteObservers
 * @constructor Creates empty NoteManager object, updates notes and notifies observers
 */
class TestNoteManager(context: Context) {
    private val dbHelper = DatabaseHelper(context)

    /**
     * Adds note into database, updates notes and notifies observers
     * @param note Note that should be registered
     * @return If action is successful, return true otherwise false
     */
    fun putNote(note: Note): Boolean {
        return try {
            val db = dbHelper.writableDatabase
            val values = ContentValues().apply {
                put(Note.Companion.Entry.COLUMN_NAME_TITLE, note.getTitle())
                put(Note.Companion.Entry.COLUMN_NAME_VALUE, note.getValue())
                put(Note.Companion.Entry.COLUMN_NAME_COLOR, note.getColor())
                put(Note.Companion.Entry.COLUMN_NAME_TIME, note.getTime())
            }
            db?.insert(Note.Companion.Entry.TABLE_NAME, null, values)
            db.close()
            true
        } catch (e: SQLiteException) {
            false
        }
    }

    /**
     * Retrieves all notes from database
     * Creates array of Note types based on title, value, color and creation time
     * If current notes array doesn't match new one, update current and notify observers
     */
     fun getNotes(): Array<Note> {
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            Note.Companion.Entry.TABLE_NAME,
            arrayOf(
                BaseColumns._ID,
                Note.Companion.Entry.COLUMN_NAME_TITLE,
                Note.Companion.Entry.COLUMN_NAME_VALUE,
                Note.Companion.Entry.COLUMN_NAME_COLOR,
                Note.Companion.Entry.COLUMN_NAME_TIME
            ),
            null,
            null,
            null,
            null,
            "${Note.Companion.Entry.COLUMN_NAME_TIME} DESC"
        )
        val notes = mutableListOf<Note>()
        with(cursor) {
            while (moveToNext()) {
                notes.add(
                    Note(
                        getString(getColumnIndexOrThrow(Note.Companion.Entry.COLUMN_NAME_TITLE)),
                        getString(getColumnIndexOrThrow(Note.Companion.Entry.COLUMN_NAME_VALUE)),
                        getString(getColumnIndexOrThrow(Note.Companion.Entry.COLUMN_NAME_COLOR)).toInt(),
                        getString(getColumnIndexOrThrow(Note.Companion.Entry.COLUMN_NAME_TIME)).toLong()
                    )
                )
            }
        }
        cursor.close()
        db.close()
        return notes.toTypedArray()
    }

    /**
     * Deletes note by given time
     * @param time One and only unique identifier of note
     * @return If action is successful, return true otherwise false
     */
    fun deleteNote(time: Long): Boolean {
        return try {
            val db = dbHelper.writableDatabase
            db.delete(Note.Companion.Entry.TABLE_NAME, "${Note.Companion.Entry.COLUMN_NAME_TIME} LIKE ?", arrayOf(time.toString()))
            db.close()
            getNotes()
            true
        } catch (e: SQLiteException) {
            false
        }
    }

    fun deleteAllNotes() {
        val db = dbHelper.writableDatabase
        db.delete(Note.Companion.Entry.TABLE_NAME, null, null)
        db.close()
    }
}