package com.example.todonotesapp

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetailActivity : AppCompatActivity() {
    val TAG = "DetailActivity"
    lateinit var textViewtitle : TextView
    lateinit var textViewDescriptipn : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        bindViews()
        setIntentData()
    }

    private fun setIntentData() {
        val intent = intent //getIntent()
        val title = intent.getStringExtra(AppConstant.TITLE)
        val desc = intent.getStringExtra(AppConstant.DESCRIPTION)
        //setText()
        textViewtitle.text = title
        textViewDescriptipn.text = desc
    }

    private fun bindViews() {
        textViewtitle = findViewById(R.id.textViewTitle)
        textViewDescriptipn = findViewById(R.id.textViewDescription)

    }
}