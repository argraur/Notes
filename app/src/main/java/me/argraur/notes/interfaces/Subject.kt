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

package me.argraur.notes.interfaces

import me.argraur.notes.observers.NoteObserver

/**
 * Observer manager
 */
interface Subject {
    /**
     * Registers observer
     * @param observer NoteObserver type to-be-registered
     */
    fun registerObserver(observer: NoteObserver)

    /**
     * Removes observer
     * @param observer NoteObserver type to-be-removed
     */
    fun removeObserver(observer: NoteObserver)

    /**
     * Notifies all observers about update
     */
    fun notifyObserver()
}