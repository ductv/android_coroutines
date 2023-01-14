package com.example.androidcoroutines

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    lateinit var tvData: TextView
    lateinit var loading: ProgressBar

    private var count = 0
    private var isShowedResult = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvData = findViewById(R.id.txt_data)
        loading = findViewById(R.id.progress_bar_loading)

        findViewById<Button>(R.id.btn_start).setOnClickListener {
            requestDataWithSuspend()
        }

        findViewById<Button>(R.id.btn_increase).setOnClickListener {
            if (!isShowedResult){
                count ++
                tvData.text = "count: $count"
            }
        }
    }

    private fun requestData(){
        Log.e("requestData","requestData on: ${Thread.currentThread().name}")
        Thread.sleep(2000L)
        loading.visibility = View.GONE
        isShowedResult = true
        tvData.text = "data from server"
    }

    private fun requestDataWithSuspend(){
        val scope = CoroutineScope(Job() + Dispatchers.Main)
        scope.launch {
            Log.e("requestDataWithSuspend", "requestDataWithSuspend ${Thread.currentThread().name}")
            delay(3000L)
            val startTime = System.currentTimeMillis()
            val executeTime = System.currentTimeMillis() - startTime
            Log.e("executeTime: ", "executeTime: $executeTime")
            loading.visibility = View.GONE
            isShowedResult = true
            tvData.text = "data from server"
        }
    }
}