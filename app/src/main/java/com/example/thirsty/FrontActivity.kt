package com.example.thirsty

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView

const val EXTRA_MESSAGE = "com.example.thirsty.MESSAGE"

class FrontActivity : AppCompatActivity() {

    private var mProgressBar: ProgressBar? = null
    private var mLoadingText: TextView? = null
    private var mProgressStatus = 0
    private val mHandler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_front2)

            mProgressBar = findViewById(R.id.poggersbar) as ProgressBar?
            mLoadingText = findViewById(R.id.waterCompleteTextView) as TextView?
            Thread {
                while (mProgressStatus < 100) {
                    mProgressStatus++
                    SystemClock.sleep(50)
                    mHandler.post { mProgressBar?.setProgress(mProgressStatus) }
                }
                mHandler.post { mLoadingText?.setVisibility(View.VISIBLE) }
            }.start()
        }

    }