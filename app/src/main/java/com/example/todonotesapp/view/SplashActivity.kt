package com.example.todonotesapp.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.todonotesapp.R
import com.example.todonotesapp.onboarding.OnBoardingActivity
import com.example.todonotesapp.utils.PrefConstant
import com.example.todonotesapp.utils.StoreSession
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        ////////
        val anim = android.view.animation.AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in)
        anim.setAnimationListener(object : Animation.AnimationListener{
            override fun onAnimationRepeat(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                setUpSharedPreferences()
                checkLoginStatus()
                getFCMToken()
            }

            override fun onAnimationStart(animation: Animation?) {
            }

        })
        ////////
        imageView3.startAnimation(anim)
        textView2.startAnimation(anim)
    }

    private fun getFCMToken() {
        FirebaseInstanceId.getInstance().instanceId
                .addOnCompleteListener(OnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        Log.w("SplashActivity", "getInstanceId failed", task.exception)
                        return@OnCompleteListener
                    }

                    // Get new Instance ID token
                    val token = task.result?.token

                    // Log and toast
                    Log.d("SplashActivity", token)
                    Toast.makeText(baseContext, token, Toast.LENGTH_SHORT).show()
                })
    }

    private fun setUpSharedPreferences() {
             StoreSession.init(this)
    }

    private fun checkLoginStatus() {
        val isLoggedIn = StoreSession.read(PrefConstant.IS_LOGGED_IN)
        val isBoardingSuccess = StoreSession.read(PrefConstant.ON_BOARDED_SUCCESSFULLY)

        if (isLoggedIn!!) {
            val intent = Intent(this@SplashActivity, MyNotesActivity::class.java)
            startActivity(intent)
        } else {
            if (isBoardingSuccess!!) {
                val intent = Intent(this@SplashActivity, LoginActivity::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(this@SplashActivity, OnBoardingActivity::class.java)
                startActivity(intent)
            }
        }

        finish()
    }

}