package android.bignerdranch.audiorecord.activity

import android.Manifest
import android.bignerdranch.audiorecord.R
import android.bignerdranch.audiorecord.implementation.Buttons
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.SurfaceView
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_record.*


class RecordActivity : AppCompatActivity() {
    private val svWave by lazy { findViewById<SurfaceView>(R.id.sv_wave) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record)

        val button = Buttons(svWave = svWave, context = this)

        checkPermission()

        button_start_recording.setOnClickListener {
            checkPermission()

            button_start_recording.isEnabled = false
            button_stop_recording.isEnabled = true
            button_pause_recording.isEnabled = true

            button.startRecording()
        }

        button_stop_recording.setOnClickListener {
            button.stopRecording()

            button_stop_recording.isEnabled = false
            button_pause_recording.isEnabled = false
            button_start_recording.isEnabled = true
            button_resume_recording.visibility = View.INVISIBLE
            button_pause_recording.visibility = View.VISIBLE
        }

        button_pause_recording.setOnClickListener {
            button.pauseRecording()

            button_pause_recording.visibility = View.INVISIBLE
            button_resume_recording.visibility = View.VISIBLE
        }

        button_resume_recording.setOnClickListener {
            button.resumeRecording()

            button_resume_recording.visibility = View.INVISIBLE
            button_pause_recording.visibility = View.VISIBLE
        }
    }

    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            val permissions = arrayOf(
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )

            ActivityCompat.requestPermissions(this, permissions, 0)
        }
    }
}