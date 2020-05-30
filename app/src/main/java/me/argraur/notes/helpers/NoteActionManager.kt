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

import me.argraur.notes.entities.Note
import me.argraur.notes.enums.Action
import me.argraur.notes.interfaces.NoteActionObserverManager
import me.argraur.notes.observers.NoteActionObserver

/**
 * This class parses some of actions that can be done with Notes
 * Such as: ADD, REMOVE, SHOW
 */
class NoteActionManager: NoteActionObserverManager {
    companion object {
        // Static instance of NoteActionManager
        private var INSTANCE: NoteActionManager? = null

        /**
         * Creates one and only instance of NoteActionManager
         */
        fun getInstance(): NoteActionManager {
            if (INSTANCE == null) {
                INSTANCE = NoteActionManager()
            }
            return INSTANCE!!
        }
    }

    private val mObservers = ArrayList<NoteActionObserver>()
    private var action = Action.NOTHING
    private var actionOn: Note? = null

    /**
     * Calls observers to process given action
     * @param action actual note Action
     * @param actionOn Note we are performing action on
     */
    fun call(action: Action, actionOn: Note) {
        this.action = action
        this.actionOn = actionOn
        if (action != Action.SHOW) {
            notifyObserver()
        }
    }

    /**
     * Calls observers to process given action on current note (actionOn)
     * @param action actual note Action
     */
    fun callOnCurrent(action: Action) {
        this.action = action
        if (actionOn != null) {
            notifyObserver()
        }
    }

    /**
     * Returns most recent called action
     * @return Recent action (action var)
     */
    fun currentAction(): Action {
        return action
    }

    /**
     * Returns most recent note we worked with
     * @return Note we performed action on (actionOn var)
     */
    fun current(): Note {
        return actionOn!!
    }

    /**
     * @see NoteActionObserverManager.registerObserver
     */
    override fun registerObserver(observer: NoteActionObserver) {
        if (!mObservers.contains(observer)) {
            mObservers.add(observer)
        }
    }

    /**
     * @see NoteActionObserverManager.removeObserver
     */
    override fun removeObserver(observer: NoteActionObserver) {
        if (mObservers.contains(observer)) {
            mObservers.remove(observer)
        }
    }

    /**
     * @see NoteActionObserverManager.notifyObserver
     */
    override fun notifyObserver() {
        for (observer in mObservers) {
            observer.onAction(actionOn!!, action)
        }
    }
}