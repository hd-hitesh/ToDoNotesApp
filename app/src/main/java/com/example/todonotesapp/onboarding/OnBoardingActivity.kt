package com.example.todonotesapp.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.viewpager.widget.ViewPager
import com.example.todonotesapp.R
import com.example.todonotesapp.view.LoginActivity

class OnBoardingActivity : AppCompatActivity(),OnBoardingOneFragment.OnNextClick , OnBoardingTwoFragment.OnOptionClick {

    lateinit var viewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)
        bindView()
    }

    private fun bindView() {
        viewPager = findViewById(R.id.viewPager)
        val adapter = FragmentAdapter(supportFragmentManager)
        viewPager.adapter = adapter
    }

    override fun onClick() {
        viewPager.currentItem =1
    }

    override fun onOptionBack() {
        Log.d("12451245","111111")
        viewPager.currentItem =0
    }

    override fun onOptionDone() {
        Log.d("12451245","222222")
        val intent = Intent(this@OnBoardingActivity, LoginActivity::class.java)
        startActivity(intent)
        Log.d("12451245","333333")

    }
}
