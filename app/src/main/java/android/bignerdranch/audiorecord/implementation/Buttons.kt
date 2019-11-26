package android.bignerdranch.audiorecord.implementation

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.bignerdranch.audiorecord.interfaces.ButtonsInterface
import android.media.MediaRecorder
import android.os.Build
import android.util.Log
import java.io.IOException

class Buttons(output: String?,
              private var mediaRecorder: MediaRecorder? = null,
              private var state: Boolean = false,
              private var recordingStopped: Boolean = false
) : ButtonsInterface {

    init {
        mediaRecorder = MediaRecorder()
        Log.d("RECV", output.toString())

        mediaRecorder?.setAudioSource(MediaRecorder.AudioSource.MIC)
        mediaRecorder?.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
        mediaRecorder?.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
        mediaRecorder?.setOutputFile(output)
    }

    override fun startRecording() {
        try {
            mediaRecorder?.prepare()
            mediaRecorder?.start()
            state = true
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    @SuppressLint("RestrictedApi", "SetTextI18n")
    @TargetApi(Build.VERSION_CODES.N)
    override fun pauseRecording() {
        if (state) {
            if (!recordingStopped) {
                mediaRecorder?.pause()
                recordingStopped = true
            } else {
                resumeRecording()
            }
        }
    }

    @SuppressLint("RestrictedApi", "SetTextI18n")
    @TargetApi(Build.VERSION_CODES.N)
    override fun resumeRecording() {
        mediaRecorder?.resume()
        recordingStopped = false
    }

    override fun stopRecording() {
        if (state) {
            mediaRecorder?.stop()
            mediaRecorder?.release()
            state = false
        }
    }
}