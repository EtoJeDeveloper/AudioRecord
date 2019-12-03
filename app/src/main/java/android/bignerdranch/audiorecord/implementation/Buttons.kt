package android.bignerdranch.audiorecord.implementation

import android.annotation.TargetApi
import android.bignerdranch.audiorecord.interfaces.ButtonsInterface
import android.content.Context
import android.graphics.PixelFormat
import android.media.MediaRecorder
import android.os.Build
import android.view.SurfaceView
import java.io.IOException

class Buttons(
    private var mediaRecorder: MediaRecorder? = null,
    private var mAudioRecordState: Int = VisualizerManager.STATE_STOP,
    private var visualizerManager: VisualizerManager = VisualizerManager(),
    private var svWave: SurfaceView,
    private var context: Context
) : ButtonsInterface{

    init {
        mediaRecorder = MediaRecorderSettings().settings(context = context)
        svWave.setZOrderOnTop(true)
        svWave.holder.setFormat(PixelFormat.TRANSLUCENT)
        visualizerManager.mStatus = VisualizerManager.STATUS_AUDIO_RECORD
    }

    override fun startRecording() {
        try {
            visualizerManager.mAudioRecord.apply {
                when(mAudioRecordState){
                    VisualizerManager.STATE_STOP -> {
                        startRecording()
                        println("Record started")

                        visualizerManager.createNewVisualizerManager()
                        visualizerManager.mVisualizerManager?.start(svWave, visualizerManager.mRenderers[8 % visualizerManager.mRenderers.size])

                        mediaRecorder?.prepare()
                        mediaRecorder?.start()

                        mAudioRecordState = VisualizerManager.STATE_RECORDING
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
        visualizerManager.mAudioRecord.apply {
            when(mAudioRecordState) {
                VisualizerManager.STATE_RECORDING -> {
                    stop()
                    println("Record stopped")

                    visualizerManager.mVisualizerManager?.stop()
                    mediaRecorder?.pause()

                    mAudioRecordState = VisualizerManager.STATE_PAUSE
                }
            }
        }
    }

    override fun resumeRecording() {
        visualizerManager.mAudioRecord.apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                mediaRecorder?.resume()
            }
            startRecording()

            visualizerManager.mVisualizerManager?.start(svWave, visualizerManager.mRenderers[8 % visualizerManager.mRenderers.size])

            mAudioRecordState = VisualizerManager.STATE_RECORDING
        }
    }

    override fun stopRecording() {
        visualizerManager.mAudioRecord.apply {
            stop()
            println("Record stopped")

            //обход особенностей фреймвора. см. fun pauseRecording
            startRecording()
            visualizerManager.mVisualizerManager?.start(svWave, visualizerManager.mRenderers[8 % visualizerManager.mRenderers.size])
            stop()
            visualizerManager.mVisualizerManager?.stop()

            mediaRecorder?.stop()
            mediaRecorder?.release()
            mediaRecorder = MediaRecorderSettings().settings(context)

            mAudioRecordState = VisualizerManager.STATE_STOP
        }
    }
}