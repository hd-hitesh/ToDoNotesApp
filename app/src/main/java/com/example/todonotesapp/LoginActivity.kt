package com.example.todonotesapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class LoginActivity : AppCompatActivity() {

     lateinit var editTextFullName: EditText
     lateinit var editTextUsername: EditText
     lateinit var buttonLogin: Button
     lateinit var sharedPreferences: SharedPreferences
     lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        editTextFullName = findViewById(R.id.editTextFullName)
        editTextUsername = findViewById(R.id.editTextUserName)
        buttonLogin = findViewById(R.id.buttonLogin)
        setUpSharedPreferences()

        val clickAction = View.OnClickListener {
            val fullName = editTextFullName.text.toString()
            val user_name = editTextUsername.text.toString()

            if (fullName.isNotEmpty() && user_name.isNotEmpty()) {
                val intent = Intent(this@LoginActivity, MyNotesActivity::class.java)
                intent.putExtra(AppConstant.FULL_NAME, fullName)
                startActivity(intent)
                saveLoginStatus()
                saveFullName(fullName)
                //                Log.d("LoginActivity", "clicked");
            } else {
                Toast.makeText(this@LoginActivity, "Fullname and username can't be empty", Toast.LENGTH_SHORT).show()
            }
        }
        buttonLogin.setOnClickListener(clickAction)
    }

    private fun saveFullName(fullName: String) {
        editor = sharedPreferences.edit()
        editor.putString(PrefConstant.FULL_NAME, fullName)
        editor.apply()
    }

    private fun saveLoginStatus() {
        editor = sharedPreferences.edit()
        editor.putBoolean(PrefConstant.IS_LOGGED_IN, true)
        editor.apply()
    }

    private fun setUpSharedPreferences() {
        sharedPreferences = getSharedPreferences(PrefConstant.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
    }
}
