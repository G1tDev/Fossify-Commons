package org.fossify.commons.samples.activities

import android.annotation.SuppressLint
import android.content.Intent
import org.fossify.commons.activities.BaseSplashActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseSplashActivity() {
    override fun initActivity() {
        if (intent.action == Intent.ACTION_VIEW && intent.data != null) {
            Intent(this, MainActivity::class.java).apply {
                action = Intent.ACTION_VIEW
                data = intent.data
                startActivity(this)
            }
        } else {
            startActivity(Intent(this, MainActivity::class.java))
        }
        finish()
    }
}
