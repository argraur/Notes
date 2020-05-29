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

package me.argraur.notes.entities

import android.graphics.Color
import android.provider.BaseColumns

/**
 * Note entity
 */
class Note {
    // Main variables
    private var title: String
    private var value: String
    private var color: Int
    private var time: Long

    companion object {
        /**
         * Used by DatabaseHelper and NoteManager
         */
        object Entry: BaseColumns {
            const val TABLE_NAME = "note"
            const val COLUMN_NAME_TITLE = "title"
            const val COLUMN_NAME_VALUE = "value"
            const val COLUMN_NAME_COLOR = "color"
            const val COLUMN_NAME_TIME = "time"
        }
    }

    /**
     * @param title Note title
     * @param value Note content
     */
    constructor(title: String, value: String) {
        this.title = title
        this.value = value
        this.color = Color.parseColor("#5b2b2a")
        time = System.currentTimeMillis()
    }

    /**
     * @param title Note title
     * @param value Note content
     * @param color Color in int format
     */
    constructor(title: String, value: String, color: Int) {
        this.title = title
        this.value = value
        this.color = color
        time = System.currentTimeMillis()
    }

    /**
     * @param title Note title
     * @param value Note content
     * @param color Color in int format
     * @param time Time in Long format
     */
    constructor(title: String, value: String, color: Int, time: Long) {
        this.title = title
        this.value = value
        this.color = color
        this.time = time
    }

    /**
     * Sets title and returns Note object
     * @param title
     * @return Object of Note type
     */
    fun setTitle(title: String): Note {
        this.title = title
        return this
    }

    /**
     * Sets value and returns Note object
     * @param value
     * @return Object of Note type
     */
    fun setValue(value: String): Note {
        this.value = value
        return this
    }

    /**
     * Sets color and returns Note object
     * @param color
     * @return Object of Note type
     */
    fun setColor(color: Int): Note {
        this.color = color
        return this
    }

    /**
     * Sets time and returns Note object
     * @param time
     * @return Object of Note type
     */
    fun setTime(time: Long): Note {
        this.time = time
        return this
    }

    /**
     * @return Note title
     */
    fun getTitle(): String {
        return title
    }

    /**
     * @return Note content
     */
    fun getValue(): String {
        return value
    }

    /**
     * @return Note color in int format
     */
    fun getColor(): Int {
        return color
    }

    /**
     * @return Note creation time in Long format
     */
    fun getTime(): Long {
        return time
    }
}