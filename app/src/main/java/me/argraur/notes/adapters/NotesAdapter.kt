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

package me.argraur.notes.adapters

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.get
import androidx.recyclerview.widget.RecyclerView

import com.google.android.material.card.MaterialCardView

import me.argraur.notes.R
import me.argraur.notes.entities.Note
import me.argraur.notes.enums.Action
import me.argraur.notes.helpers.NoteActionManager
import me.argraur.notes.screens.NoteActivity

/**
 * RecyclerView adapter for notes
 * @param notes Notes that should be added into view
 * @param activity Activity where RecyclerView exists
 */
class NotesAdapter(private val notes: Array<Note>, private val activity: Activity): RecyclerView.Adapter<NotesAdapter.NotesHolder>() {
    /**
     * RecyclerView holder for notes's cards
     * @param cardView Card to be added into view holder
     */
    class NotesHolder(val cardView: MaterialCardView): RecyclerView.ViewHolder(cardView)

    /**
     * @see RecyclerView.Adapter.onCreateViewHolder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesHolder {
        return NotesHolder(LayoutInflater.from(parent.context).inflate(R.layout.note_card, parent, false) as MaterialCardView)
    }

    /**
     * Adds data into cards based on given note's position
     * @see RecyclerView.Adapter.onBindViewHolder
     */
    override fun onBindViewHolder(holder: NotesHolder, position: Int) {
        val title = notes[position].getTitle()
        val value = notes[position].getValue()
        ((holder.cardView[0] as ConstraintLayout)[0] as TextView).text = title
        ((holder.cardView[0] as ConstraintLayout)[1] as TextView).text = value
        holder.cardView.setCardBackgroundColor(notes[position].getColor())
        holder.cardView.setOnClickListener {
            NoteActionManager.getInstance().call(Action.SHOW, notes[position])
            activity.startActivity(Intent(activity, NoteActivity::class.java), ActivityOptions.makeSceneTransitionAnimation(activity).toBundle())
        }
    }

    /**
     * @see RecyclerView.Adapter.getItemCount
     */
    override fun getItemCount() = notes.size
}
