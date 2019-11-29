package android.bignerdranch.audiorecord.implementation

import android.annotation.TargetApi
import android.bignerdranch.audiorecord.interfaces.ButtonsInterface
import android.content.Context
import android.graphics.PixelFormat
import android.media.MediaRecorder
import android.os.Build
import android.util.Log
import android.view.SurfaceView
import java.io.IOException

class Buttons(
    private var state: Boolean = false,
    private var mediaRecorder: MediaRecorder? = null,
    private var recordingStopped: Boolean = false,
    private var mAudioRecordState: Int = VisualizerManager.STATE_STOP,
    private var visualizerManager: VisualizerManager = VisualizerManager(),
    private var svWave: SurfaceView,
    private var context: Context
) : ButtonsInterface{

    init {
        mediaRecorder = MediaRecorderSettings().settings(context = context)
        svWave.setZOrderOnTop(true)
        svWave.holder.setFormat(PixelFormat.TRANSLUCENT)
    }

    override fun startRecording() {
        try {
            visualizerManager.mAudioRecord.apply {
                when(mAudioRecordState){
                    VisualizerManager.STATE_STOP -> {
                        startRecording()
                        println("Record started")
                        mediaRecorder?.prepare()
                        mediaRecorder?.start()
                        mAudioRecordState = VisualizerManager.STATE_PLAYING
                        if(visualizerManager.mStatus == VisualizerManager.STATUS_MEDIA_PLAYER || visualizerManager.mStatus == VisualizerManager.STATUS_UNKNOWN) {
                            visualizerManager.mStatus = VisualizerManager.STATUS_AUDIO_RECORD
                            Log.d("WHY", VisualizerManager.STATUS_AUDIO_RECORD.toString())
                            println(visualizerManager.mStatus.toString())
                            visualizerManager.createNewVisualizerManager()
                        }

                        visualizerManager.mVisualizerManager?.start(svWave, visualizerManager.mRenderers[4 % visualizerManager.mRenderers.size])
                    }
                }
            }

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
            mediaRecorder = MediaRecorderSettings().settings(context)
            state = false

        }
    }
}