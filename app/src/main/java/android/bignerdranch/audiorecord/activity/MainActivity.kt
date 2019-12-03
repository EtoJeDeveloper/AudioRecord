package android.bignerdranch.audiorecord.activity

import android.bignerdranch.audiorecord.R
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.Window.FEATURE_NO_TITLE
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(FEATURE_NO_TITLE)
        setContentView(R.layout.activity_main)

        val intentRecord = Intent(this, RecordActivity::class.java)
        val intentAudio = Intent(this, AudioListActivity::class.java)

        newActivityButton.setOnClickListener {
            startActivity(intentRecord)
        }

        myRecordsButton.setOnClickListener {
            startActivity(intentAudio)
        }
    }

}
