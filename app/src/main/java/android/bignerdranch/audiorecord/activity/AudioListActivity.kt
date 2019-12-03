package android.bignerdranch.audiorecord.activity

import android.bignerdranch.audiorecord.fileSystems.FileModel
import android.bignerdranch.audiorecord.fileSystems.FilesListFragment
import android.bignerdranch.audiorecord.fileSystems.launchFileIntent
import android.bignerdranch.audiorecord.R
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.View

class AudioListActivity : AppCompatActivity(),FilesListFragment.OnItemClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = window.decorView.systemUiVisibility.or(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
        }
        setContentView(R.layout.activity_audio_list)

        if (savedInstanceState == null) {
            val filesListFragment = FilesListFragment.build {
                path = getExternalFilesDir(Environment.DIRECTORY_MUSIC).toString()
            }

            supportFragmentManager.beginTransaction()
                .add(R.id.container, filesListFragment)
                .addToBackStack(getExternalFilesDir(Environment.DIRECTORY_MUSIC).toString())
                .commit()

        }
    }

    override fun onClick(fileModel: FileModel) {
        launchFileIntent(fileModel)
    }

    override fun onLongClick(fileModel: FileModel) {

    }
}
