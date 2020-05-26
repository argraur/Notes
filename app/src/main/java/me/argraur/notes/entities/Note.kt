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

class Note {
    private var title: String
    private var value: String
    private var color: Int
    private var time: Long

    companion object {
        object Entry: BaseColumns {
            const val TABLE_NAME = "note"
            const val COLUMN_NAME_TITLE = "title"
            const val COLUMN_NAME_VALUE = "value"
            const val COLUMN_NAME_COLOR = "color"
            const val COLUMN_NAME_TIME = "time"
        }
    }

    constructor(title: String, value: String) {
        this.title = title
        this.value = value
        this.color = Color.parseColor("#5b2b2a")
        time = System.currentTimeMillis()
    }

    constructor(title: String, value: String, color: Int) {
        this.title = title
        this.value = value
        this.color = color
        time = System.currentTimeMillis()
    }

    constructor(title: String, value: String, color: Int, time: Long) {
        this.title = title
        this.value = value
        this.color = color
        this.time = time
    }

    fun setTitle(title: String): Note {
        this.title = title
        return this
    }

    fun setValue(value: String): Note {
        this.value = value
        return this
    }

    fun setColor(color: Int): Note {
        this.color = color
        return this
    }

    fun setTime(time: Long): Note {
        this.time = time
        return this
    }

    fun getTitle(): String {
        return title
    }

    fun getValue(): String {
        return value
    }

    fun getColor(): Int {
        return color
    }

    fun getTime(): Long {
        return time
    }
}