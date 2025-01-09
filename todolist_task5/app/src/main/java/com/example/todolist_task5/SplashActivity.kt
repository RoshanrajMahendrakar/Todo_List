package com.example.todolist_task5
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)
        Handler(Looper.getMainLooper()).postDelayed({
            // Navigate to MainActivity after the delay
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Close the SplashActivity so the user can't go back to it
        }, 1000) // 3000 milliseconds = 3 seconds
    }
}