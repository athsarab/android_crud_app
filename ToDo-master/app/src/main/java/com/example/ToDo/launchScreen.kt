package com.example.ToDo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class launchScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.luanch_screen)

        // You can add a delay if needed
        Thread {
            Thread.sleep(3000) // 3 seconds delay
            startActivity(Intent(this@launchScreen, OnBoard_1::class.java))
            finish()
        }.start()
    }
}

