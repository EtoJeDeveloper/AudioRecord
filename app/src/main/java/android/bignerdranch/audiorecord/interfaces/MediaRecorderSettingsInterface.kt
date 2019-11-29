package android.bignerdranch.audiorecord.interfaces

import android.content.Context
import android.media.MediaRecorder

interface MediaRecorderSettingsInterface {
    fun settings(context: Context): MediaRecorder?
}