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

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import me.argraur.notes.entities.Note

class DatabaseHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        const val SQL_CREATE_ENTRIES = "CREATE TABLE ${Note.Companion.Entry.TABLE_NAME} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY, " +
                "${Note.Companion.Entry.COLUMN_NAME_TITLE} TEXT, " +
                "${Note.Companion.Entry.COLUMN_NAME_VALUE} TEXT, " +
                "${Note.Companion.Entry.COLUMN_NAME_COLOR} TEXT, " +
                "${Note.Companion.Entry.COLUMN_NAME_TIME} TEXT)"
        const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${Note.Companion.Entry.TABLE_NAME}"
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "notes.db"
    }

    /**
     * @see SQLiteOpenHelper.onCreate
     */
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    /**
     * @see SQLiteOpenHelper.onUpgrade
     */
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_ENTRIES)
    }

    /**
     * @see SQLiteOpenHelper.onDowngrade
     */
    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }
}