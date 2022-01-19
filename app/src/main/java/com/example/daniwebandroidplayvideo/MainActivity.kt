package com.example.daniwebandroidplayvideo

import android.media.MediaCodecList
import android.media.MediaCodecList.ALL_CODECS
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.VideoView
import androidx.activity.result.contract.ActivityResultContracts.OpenDocument

private const val TAG = "MAIN_ACTIVITY"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        logCodecs()

        //Gets the videoView reference
        val videoView = findViewById<VideoView>(R.id.videoView)

        val openVideoLauncher = registerForActivityResult(OpenDocument()) { uri ->
            //Start the video now that we have the uri
            videoView.apply {
                setVideoURI(uri) //sets the video uri
                visibility = VISIBLE //makes videoView visible
            }.start()
        }

        //Gets the button reference
        val button = findViewById<Button>(R.id.button)

        button.setOnClickListener {
            val mimeTypes = arrayOf("video/mp4") //filters for mp4 videos
            openVideoLauncher.launch(mimeTypes)
        }

    }

    private fun logCodecs(){
        val codecList = MediaCodecList(ALL_CODECS)

        for (codecInfo in codecList.codecInfos){
            for (mimeType in codecInfo.supportedTypes){
                Log.d(TAG, mimeType)
            }
        }
    }
}