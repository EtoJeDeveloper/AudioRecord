package android.bignerdranch.audiorecord.activity

import android.bignerdranch.audiorecord.R
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent = Intent(this, Record::class.java)

        newActivityButton.setOnClickListener {
            startActivity(intent)
        }
    }
}
