package com.example.network

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.network.Networking.messageApi
import kotlinx.coroutines.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val theMessageApiService = messageApi

        val adapter = MessageAdapter()
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.adapter = adapter

        lifecycleScope.launch {
            val messages: List<message> = theMessageApiService.getPosts()
            adapter.submitList(messages) //отправили их в адаптер
        }
    }
}
