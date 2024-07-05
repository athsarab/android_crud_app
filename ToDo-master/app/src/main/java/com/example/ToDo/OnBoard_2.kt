package com.example.ToDo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class OnBoard_2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.onboard_2)

        // Delay navigation to MainActivity after 3 seconds
        android.os.Handler().postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish() // Finish this activity to prevent back navigation
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left) // Animation effect
        }, 3000) // 3000 milliseconds = 3 seconds
    }
}
