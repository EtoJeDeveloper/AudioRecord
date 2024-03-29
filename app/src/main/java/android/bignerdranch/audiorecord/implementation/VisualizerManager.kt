package android.bignerdranch.audiorecord.implementation

import android.graphics.Color
import android.graphics.Paint
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.view.animation.LinearInterpolator
import me.bogerchan.niervisualizer.NierVisualizerManager
import me.bogerchan.niervisualizer.renderer.IRenderer
import me.bogerchan.niervisualizer.renderer.circle.CircleBarRenderer
import me.bogerchan.niervisualizer.renderer.circle.CircleRenderer
import me.bogerchan.niervisualizer.renderer.circle.CircleSolidRenderer
import me.bogerchan.niervisualizer.renderer.circle.CircleWaveRenderer
import me.bogerchan.niervisualizer.renderer.columnar.ColumnarType1Renderer
import me.bogerchan.niervisualizer.renderer.columnar.ColumnarType2Renderer
import me.bogerchan.niervisualizer.renderer.columnar.ColumnarType3Renderer
import me.bogerchan.niervisualizer.renderer.columnar.ColumnarType4Renderer
import me.bogerchan.niervisualizer.renderer.line.LineRenderer
import me.bogerchan.niervisualizer.renderer.other.ArcStaticRenderer
import me.bogerchan.niervisualizer.util.NierAnimator

class VisualizerManager {

    companion object {
        const val STATUS_UNKNOWN = 0
        const val STATUS_AUDIO_RECORD = 1
        const val STATUS_MEDIA_PLAYER = 2

        const val STATE_STOP = 0
        const val STATE_PAUSE = 1
        const val STATE_RECORDING = 2


        const val SAMPLING_RATE = 44100
    }

    val mAudioBufferSize by lazy {
        AudioRecord.getMinBufferSize(SAMPLING_RATE, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_8BIT)
    }
    val mAudioRecord by lazy {
        AudioRecord(MediaRecorder.AudioSource.MIC, SAMPLING_RATE, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_8BIT, mAudioBufferSize)
    }

    var mVisualizerManager: NierVisualizerManager? = null
    var mStatus = STATUS_UNKNOWN


    fun createNewVisualizerManager(){
        mVisualizerManager?.release()
        mVisualizerManager = NierVisualizerManager().apply {
            when(mStatus) {
                STATUS_AUDIO_RECORD -> {
                    init(object :NierVisualizerManager.NVDataSource {
                        private val mBuffer: ByteArray = ByteArray(512)
                        private val mAudioRecordByteBuffer by lazy { ByteArray(mAudioBufferSize / 2) }
                        private val audioLength = (mAudioRecordByteBuffer.size * 1000F / SAMPLING_RATE).toInt()

                        override fun fetchFftData(): ByteArray? {
                            return null
                        }

                        override fun fetchWaveData(): ByteArray? {
                            if (mAudioRecord.recordingState != AudioRecord.RECORDSTATE_RECORDING) {
                                return null
                            }

                            mAudioRecordByteBuffer.fill(0)
                            mAudioRecord.read(mAudioRecordByteBuffer, 0, mAudioRecordByteBuffer.size)
                            var tempCounter = 0
                            for (idx in mAudioRecordByteBuffer.indices step (mAudioRecordByteBuffer.size / (audioLength + mBuffer.size))) {
                                if (tempCounter >= mBuffer.size) {
                                    break
                                }
                                mBuffer[tempCounter++] = mAudioRecordByteBuffer[idx]
                            }
                            return mBuffer
                        }

                        override fun getDataLength() = mBuffer.size

                        override fun getDataSamplingInterval() = 0L
                    })
                }
            }
        }
    }

    val mRenderers = arrayOf<Array<IRenderer>>(
        arrayOf(ColumnarType1Renderer()),
        arrayOf(ColumnarType2Renderer()),
        arrayOf(ColumnarType3Renderer()),
        arrayOf(ColumnarType4Renderer()),
        arrayOf(LineRenderer(true)),
        arrayOf(CircleBarRenderer()),
        arrayOf(CircleRenderer(true)),
        arrayOf(
            CircleRenderer(true),
            CircleBarRenderer(),
            ColumnarType4Renderer()
        ),
        arrayOf(CircleRenderer(true), CircleBarRenderer(), LineRenderer(true)),
        arrayOf(
            ArcStaticRenderer(
                paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                    color = Color.parseColor("#cfa9d0fd")
                }),
            ArcStaticRenderer(
                paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                    color = Color.parseColor("#dad2eafe")
                },
                amplificationOuter = .83f,
                startAngle = -90f,
                sweepAngle = 225f),
            ArcStaticRenderer(
                paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                    color = Color.parseColor("#7fa9d0fd")
                },
                amplificationOuter = .93f,
                amplificationInner = 0.8f,
                startAngle = -45f,
                sweepAngle = 135f),
            CircleSolidRenderer(
                paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                    color = Color.parseColor("#d2eafe")
                },
                amplification = .45f),
            CircleBarRenderer(
                paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                    strokeWidth = 4f
                    color = Color.parseColor("#efe3f2ff")
                },
                modulationStrength = 1f,
                type = CircleBarRenderer.Type.TYPE_A_AND_TYPE_B,
                amplification = 1f, divisions = 8),
            CircleBarRenderer(
                paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                    strokeWidth = 5f
                    color = Color.parseColor("#e3f2ff")
                },
                modulationStrength = 0.1f,
                amplification = 1.2f,
                divisions = 8),
            CircleWaveRenderer(
                paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                    strokeWidth = 6f
                    color = Color.WHITE
                },
                modulationStrength = 0.2f,
                type = CircleWaveRenderer.Type.TYPE_B,
                amplification = 1f,
                animator = NierAnimator(
                    interpolator = LinearInterpolator(),
                    duration = 20000,
                    values = floatArrayOf(0f, -360f))
            ),
            CircleWaveRenderer(
                paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                    strokeWidth = 6f
                    color = Color.parseColor("#7fcee7fe")
                },
                modulationStrength = 0.2f,
                type = CircleWaveRenderer.Type.TYPE_B,
                amplification = 1f,
                divisions = 8,
                animator = NierAnimator(
                    interpolator = LinearInterpolator(),
                    duration = 20000,
                    values = floatArrayOf(0f, -360f))
            )
        )
    )
}