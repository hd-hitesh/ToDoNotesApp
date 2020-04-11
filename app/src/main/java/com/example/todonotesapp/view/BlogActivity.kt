package com.example.todonotesapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener
import com.example.todonotesapp.R
import com.example.todonotesapp.adapter.BlogsAdapter
import com.example.todonotesapp.model.JsonResponse

class BlogActivity : AppCompatActivity() {

    lateinit var recyclerViewBlogs: RecyclerView
    val TAG = "BlogActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blog)
        bindViews()
        getBlogs()
    }

    private fun bindViews() {
        recyclerViewBlogs = findViewById(R.id.recyclerViewBlogs)
    }

    private fun getBlogs() {
        AndroidNetworking.get("http://www.mocky.io/v2/5926ce9d11000096006ccb30")
                .setPriority(Priority.HIGH)
                .build()
                .getAsObject(JsonResponse::class.java, object : ParsedRequestListener<JsonResponse> {
                    override fun onResponse(response: JsonResponse) {
                        setupRecyclerView(response)
                    }

                    override fun onError(anError: ANError?) {
                        Log.d(TAG, anError?.localizedMessage!!)
                    }

                })
    }

    private fun setupRecyclerView(response: JsonResponse) {
        val notesAdapter = BlogsAdapter(response.data)
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        recyclerViewBlogs.layoutManager = linearLayoutManager
        recyclerViewBlogs.adapter = notesAdapter
    }
}
