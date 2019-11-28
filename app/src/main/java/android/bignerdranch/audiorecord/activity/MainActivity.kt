package android.bignerdranch.audiorecord.activity

import android.bignerdranch.audiorecord.R
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window.FEATURE_NO_TITLE
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(FEATURE_NO_TITLE)
        setContentView(R.layout.activity_main)

        val intent = Intent(this, Record::class.java)

        newActivityButton.setOnClickListener {
            startActivity(intent)
        }
    }
}
