package android.bignerdranch.audiorecord.implementation

import android.bignerdranch.audiorecord.interfaces.MediaRecorderSettingsInterface
import android.content.Context
import android.media.AudioRecord
import android.media.MediaRecorder
import android.os.Build
import android.os.Environment
import android.util.Log
import java.io.File

var count = 0


class MediaRecorderSettings(private var mediaRecorder: MediaRecorder? = null,
                            private var output: File? = null
) : MediaRecorderSettingsInterface{
    override fun settings(context: Context): MediaRecorder? {
        count++
        output = File(context.getExternalFilesDir(Environment.DIRECTORY_MUSIC),"record$count.mp3")

        mediaRecorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                setOutputFile(output)
            }
        }
        Log.d("RECV", output.toString())
        return mediaRecorder
    }
}