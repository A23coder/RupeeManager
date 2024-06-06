package com.example.expensetracker

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.expensetracker.databinding.SplashBinding

class Splash : AppCompatActivity() {
    private lateinit var binding: SplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val animationSlideDown = AnimationUtils.loadAnimation(this , R.anim.slide_down)
        animationSlideDown.fillAfter = true
        binding.tvSplash.startAnimation(animationSlideDown)
        binding.imgSplash.startAnimation(animationSlideDown)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN ,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this@Splash , MainActivity::class.java)
            startActivity(intent)
            finish()
        } , 3000)
    }
}