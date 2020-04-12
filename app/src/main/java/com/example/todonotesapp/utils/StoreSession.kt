package com.example.todonotesapp.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity

object StoreSession {
    private var sharedPreferences: SharedPreferences? = null
    fun init(context: Context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(PrefConstant.SHARED_PREFERENCE_NAME, AppCompatActivity.MODE_PRIVATE)
        }
    }

    fun write(key: String, value: Boolean){
        val editor = sharedPreferences?.edit()
        editor?.putBoolean(key, value)
        editor?.apply()
    }

    fun read(key: String): Boolean? {
        return sharedPreferences?.getBoolean(key,false)
    }

    fun write(key: String, value: String){
        val editor = sharedPreferences?.edit()
        editor?.putString(key, value)
        editor?.apply()
    }

    fun readString(key: String): String? {
        return sharedPreferences?.getString(key,"")
    }
}