package org.wit.recipesapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import org.wit.recipesapp.R
import org.wit.recipesapp.ui.home.Home

class SplashActivity : AppCompatActivity() {
    lateinit var handler: Handler
    // This is the loading time of the splash screen
    private val SPLASH_TIME_OUT:Long = 6000 // 1 sec
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        handler = Handler()
        handler.postDelayed({
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
            finish()
        }, SPLASH_TIME_OUT)
    }
}