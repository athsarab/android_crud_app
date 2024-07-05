package com.example.ToDo

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class OnBoard_1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.onboard_1)

        // Delay navigation to OnBoard_2 after 3 seconds
        Handler().postDelayed({
            startActivity(Intent(this, OnBoard_2::class.java))
            finish() // Finish this activity to prevent back navigation
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left) // Animation effect
        }, 3000) // 3000 milliseconds = 3 seconds
    }
}
