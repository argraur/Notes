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

class NoteHelper(context: Context) {
    private val dbHelper = DatabaseHelper(context)

    fun deleteAllNotes() {
        val db = dbHelper.writableDatabase
        db.delete(Note.Companion.Entry.TABLE_NAME, null, null)
        db.close()
    }

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

    fun getNotes(): Array<Note>? {
        val db = dbHelper.readableDatabase
        val projection = arrayOf(BaseColumns._ID, Note.Companion.Entry.COLUMN_NAME_TITLE, Note.Companion.Entry.COLUMN_NAME_VALUE, Note.Companion.Entry.COLUMN_NAME_COLOR, Note.Companion.Entry.COLUMN_NAME_TIME)
        val sortOrder = "${Note.Companion.Entry.COLUMN_NAME_TIME} DESC"
        val cursor = db.query(
            Note.Companion.Entry.TABLE_NAME,
            projection,
            null,
            null,
            null,
            null,
            sortOrder
        )
        val notes = mutableListOf<Note>()
        with(cursor) {
            while (moveToNext()) {
                val title = getString(getColumnIndexOrThrow(Note.Companion.Entry.COLUMN_NAME_TITLE))
                val value = getString(getColumnIndexOrThrow(Note.Companion.Entry.COLUMN_NAME_VALUE))
                val color = getString(getColumnIndexOrThrow(Note.Companion.Entry.COLUMN_NAME_COLOR)).toInt()
                val time = getString(getColumnIndexOrThrow(Note.Companion.Entry.COLUMN_NAME_TIME)).toLong()
                notes.add(Note(title, value, color, time))
            }
        }
        cursor.close()
        db.close()
        return if (notes.size != 0) {
            notes.toTypedArray()
        } else {
            null
        }
    }

    fun deleteNote(time: Long): Boolean {
        return try {
            val db = dbHelper.writableDatabase
            val selection = "${Note.Companion.Entry.COLUMN_NAME_TIME} LIKE ?"
            val selectionArgs = arrayOf(time.toString())
            db.delete(Note.Companion.Entry.TABLE_NAME, selection, selectionArgs)
            true
        } catch (e: SQLiteException) {
            false
        }
    }
}