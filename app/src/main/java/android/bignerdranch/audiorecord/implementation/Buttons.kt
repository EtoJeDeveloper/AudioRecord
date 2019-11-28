package android.bignerdranch.audiorecord.implementation

import android.annotation.TargetApi
import android.bignerdranch.audiorecord.interfaces.ButtonsInterface
import android.media.MediaRecorder
import android.os.Build
import java.io.IOException

class Buttons(
    private var state: Boolean = false,
    private var mediaRecorder: MediaRecorder? = null,
    private var recordingStopped: Boolean = false
) : ButtonsInterface{

    init {
        mediaRecorder = MediaRecorderSettings().settings()
    }

    override fun startRecording() {
        try {
            println("Record started")
            mediaRecorder?.prepare()
            mediaRecorder?.start()
            state = true
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

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

    @TargetApi(Build.VERSION_CODES.N)
    override fun resumeRecording() {
        mediaRecorder?.resume()
        recordingStopped = false
    }

    override fun stopRecording() {
        if (state) {
            println("Record did stop")
            mediaRecorder?.stop()
            mediaRecorder?.reset()
            mediaRecorder?.release()
            mediaRecorder = MediaRecorderSettings().settings()
            state = false

        }
    }
}