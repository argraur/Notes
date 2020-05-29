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

import android.app.backup.BackupAgent
import android.app.backup.BackupDataInput
import android.app.backup.BackupDataOutput
import android.os.ParcelFileDescriptor
import android.util.Log

class BackupAgent: BackupAgent() {
    /**
     * Send log message when backup started
     * @see BackupAgent.onBackup
     */
    override fun onBackup(
        oldState: ParcelFileDescriptor?,
        data: BackupDataOutput?,
        newState: ParcelFileDescriptor?
    ) {
        Log.d(javaClass.simpleName, "Backing up!")
    }

    /**
     * Send log message when restore finished
     * @see BackupAgent.onRestore
     */
    override fun onRestore(
        data: BackupDataInput?,
        appVersionCode: Int,
        newState: ParcelFileDescriptor?
    ) {
        Log.d(javaClass.simpleName, "Restoring from backup!")
    }

    /**
     * Updates notes when restore finished.
     * @see BackupAgent.onRestoreFinished
     */
    override fun onRestoreFinished() {
        super.onRestoreFinished()
        NoteManager.getInstance(null).getNotes()
    }
}