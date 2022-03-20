package com.example.thirsty

import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.view.View
import android.widget.*

const val EXTRA_MESSAGE = "com.example.thirsty.MESSAGE"

class FrontActivity : AppCompatActivity() {

    private var mProgressBar: ProgressBar? = null
    private var mLoadingText: TextView? = null
    private var mProgressStatus = 0
    private lateinit var sButton: Button
    private lateinit var hButton: ImageButton
    private lateinit var fButton: ImageButton
    private val mHandler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_front2)

        sButton = findViewById<Button>(R.id.buttonThirsty)
        hButton = findViewById<ImageButton>(R.id.halfButton)
        fButton = findViewById<ImageButton>(R.id.fullButton)

        sButton.setOnClickListener{
            val mapint = Intent(this,MapsActivity::class.java)
            startActivity(mapint)

        }

            mProgressBar = findViewById<ProgressBar>(R.id.poggersbar)
            mLoadingText = findViewById<TextView>(R.id.waterCompleteTextView)
            Thread {
                while (mProgressStatus < 80) {
                    hButton.setOnClickListener{
                        mProgressStatus += 5
                    }
                    fButton.setOnClickListener{
                        mProgressStatus += 10
                    }
                    SystemClock.sleep(100)
                    mHandler.post { mProgressBar?.setProgress(mProgressStatus) }
                }
                mHandler.post { mLoadingText?.setVisibility(View.VISIBLE) }
            }.start()
        }

    }