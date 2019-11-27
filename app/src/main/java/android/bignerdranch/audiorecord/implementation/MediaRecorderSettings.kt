package android.bignerdranch.audiorecord.implementation

import android.bignerdranch.audiorecord.interfaces.MediaRecorderSettingsInterface
import android.media.MediaRecorder
import android.os.Build
import android.util.Log
import java.io.File

var count = 0

class MediaRecorderSettings(private var mediaRecorder: MediaRecorder? = null,
                            private var output: File? = null
) : MediaRecorderSettingsInterface{
    override fun settings(): MediaRecorder? {
        count++
        output = File("/storage/emulated/0/MyRecords/record$count.mp3")

        mediaRecorder = MediaRecorder()
        mediaRecorder?.setAudioSource(MediaRecorder.AudioSource.MIC)
        mediaRecorder?.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
        mediaRecorder?.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
        Log.d("RECV", output.toString())

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mediaRecorder?.setOutputFile(output)
        }

        return mediaRecorder
    }
}