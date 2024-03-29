package com.soft.eventos.ui.splash

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.soft.eventos.BuildConfig
import com.soft.eventos.MainActivity
import com.soft.eventos.R
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity(R.layout.activity_splash) {

    private var animation: Animation? = null

    private val SPLASH_DELAY = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        components()
        versionSplash.text = BuildConfig.VERSION_NAME
    }

    fun components() {
        splash()
        animImage()
    }

    /**
     * Metodo para SplashScreen
     */
    fun splash() {
        val handler = Handler()
        handler.postDelayed({
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.flipper_in_animation, R.anim.flipper_out_animation)
            finish()
        }, SPLASH_DELAY.toLong())
    }

    /**
     * Metodo para animar imagem na tela
     */
    fun animImage() {
        animation = AnimationUtils.loadAnimation(applicationContext, R.anim.flip_animation)
        imageSplash.startAnimation(animation)
    }

    /**
     * Metoddo implementa o hasFocus
     * @param hasFocus
     */
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            hideSystemUI()
        }
    }

    /**
     * Metodo para esconder os botoes nativos do android
     */
    private fun hideSystemUI() {
        val decorView = window.decorView
        decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }

    /**
     * Metodo para Ajustar o size do dispositivo
     */
    fun adjustFontScale(configuration: Configuration) {
        configuration.fontScale = 1.0.toFloat()
        val metrics = resources.displayMetrics
        val wm = getSystemService(WINDOW_SERVICE) as WindowManager
        wm.defaultDisplay.getMetrics(metrics)
        metrics.scaledDensity = configuration.fontScale * metrics.density
        baseContext.resources.updateConfiguration(configuration, metrics)
    }
}