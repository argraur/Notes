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

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import me.argraur.notes.entities.Note
import me.argraur.notes.helpers.TestNoteManager
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class NoteTest {
    private val testName = "This is a test note!"
    private val testValue = "Hey there"
    private val testColor = 0
    private val testTime = System.currentTimeMillis()

    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java)

    @Test fun getNotes() {
        val testNoteManager = TestNoteManager(activityRule.activity)
        testNoteManager.deleteAllNotes()
        testNoteManager.putNote(Note(testName, testValue, testColor, testTime))
        val note = testNoteManager.getNotes()[0]
        assertNotNull(note)
        Log.d(javaClass.simpleName,"Title: ${note.getTitle()}")
        Log.d(javaClass.simpleName,"Value: ${note.getValue()}")
        Log.d(javaClass.simpleName,"Color: ${note.getColor()}")
        Log.d(javaClass.simpleName,"Time: ${note.getTime()}")
        assertTrue(testNoteManager.deleteNote(testTime))
        Log.d(javaClass.simpleName,"Test finished.")
    }
}