package android.bignerdranch.audiorecord.activity

import android.Manifest
import android.bignerdranch.audiorecord.R
import android.bignerdranch.audiorecord.implementation.Buttons
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_record.*
import java.io.File



class Record : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record)


        val button = Buttons()

        checkPermission()

        button_start_recording.setOnClickListener {
            checkPermission()

            button.startRecording()
        }

        button_stop_recording.setOnClickListener{
            button.stopRecording()
        }

        button_pause_recording.setOnClickListener {
            button.pauseRecording()
        }
    }

    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                val permissions = arrayOf(Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE)

                ActivityCompat.requestPermissions(this, permissions,0)
        }
    }
}



