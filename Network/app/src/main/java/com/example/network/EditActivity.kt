package com.example.network

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.network.Networking.messageApi
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Response

class EditActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.message_item_edit)

        val messageTitleView = findViewById<EditText>(R.id.messageTitleEdit)
        val messageBodyView = findViewById<EditText>(R.id.messageBodyEdit)

        val intent = intent
        val messageTitle = intent.getStringExtra("messageTitle")
        val messageBody = intent.getStringExtra("messageBody")
        val messageUserId = intent.getIntExtra("messageUserId", 0)
        val messageId = intent.getIntExtra("messageId", 0)

        messageTitleView.setText(messageTitle ?: "")
        messageBodyView.setText(messageBody ?: "")

        val theMessageApiService = messageApi

        val mySaveButton = findViewById<Button>(R.id.saveButton)
        mySaveButton.setOnClickListener {
            lifecycleScope.launch {
                val newMessage = message(
                    userId = messageUserId,
                    id = messageId,
                    titleMessage = messageTitleView.text.toString(),
                    bodyMessage = messageBodyView.text.toString(),
                )
                val response: Call<Post> = theMessageApiService.savePosts(newMessage)
                if (response.execute().isSuccessful){
                    val intent = Intent(this@EditActivity, MainActivity::class.java)
                    startActivity(intent)
                }
            }
        }

    }
}
