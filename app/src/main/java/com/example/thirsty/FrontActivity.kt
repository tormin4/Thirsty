package com.example.thirsty

import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.view.View
import android.widget.*
import org.w3c.dom.Text

const val EXTRA_MESSAGE = "com.example.thirsty.MESSAGE"

class FrontActivity : AppCompatActivity() {

    private var mProgressBar: ProgressBar? = null
    private var mLoadingText: TextView? = null
    private var mProgressStatus = 0
    private var dProgressStatus = 0.0
    private var numcups = 0.0
    private lateinit var sButton: Button
    private lateinit var hButton: ImageButton
    private lateinit var fButton: ImageButton
    private lateinit var rButton: ImageButton
    private lateinit var waterProgress: TextView
    private val mHandler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_front2)

        sButton = findViewById<Button>(R.id.buttonThirsty)
        hButton = findViewById<ImageButton>(R.id.halfButton)
        fButton = findViewById<ImageButton>(R.id.fullButton)
        rButton = findViewById<ImageButton>(R.id.refreshButton)
        waterProgress = findViewById<TextView>(R.id.waterProgressTextView)

        sButton.setOnClickListener{
            val mapint = Intent(this,MapsActivity::class.java)
            startActivity(mapint)
        }

            mProgressBar = findViewById<ProgressBar>(R.id.poggersbar)
            mLoadingText = findViewById<TextView>(R.id.waterCompleteTextView)
            Thread {
                rButton.setOnClickListener{
                    mProgressStatus = 0
                    dProgressStatus = 0.0
                    numcups = dProgressStatus/10
                    waterProgress.setText("0/8 Cups")
                    mHandler.post { mProgressBar?.setProgress(mProgressStatus) }
                    mHandler.post { mLoadingText?.setVisibility(View.INVISIBLE) }
                    mHandler.post { waterProgress?.setVisibility(View.VISIBLE) }
                }

                while (mProgressStatus < 80) {
                    hButton.setOnClickListener{
                        mProgressStatus += 5
                        dProgressStatus += 5.0
                        numcups = dProgressStatus/10
                        waterProgress.setText(numcups.toString()+ "/8 Cups")
                    }
                    fButton.setOnClickListener{
                        mProgressStatus += 10
                        dProgressStatus += 10.0
                        numcups = dProgressStatus/10
                        waterProgress.setText(numcups.toString()+ "/8 Cups")
                    }
                    SystemClock.sleep(50)
                    mHandler.post { mProgressBar?.setProgress(mProgressStatus) }
                }

                mHandler.post { mLoadingText?.setVisibility(View.VISIBLE) }
                mHandler.post { waterProgress?.setVisibility(View.INVISIBLE) }

            }.start()
        }

    }