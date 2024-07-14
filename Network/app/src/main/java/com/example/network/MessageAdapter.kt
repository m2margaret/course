package com.example.network

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MessageAdapter : RecyclerView.Adapter<MessageAdapter.ViewHolder>() {

    private var messages: List<message> = emptyList()

    fun submitList(messages: List<message>) {
        this.messages = messages
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.message_item, parent, false)
        return ViewHolder(itemView, parent.context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val message = messages[position]
        holder.bind(message)
    }

    override fun getItemCount(): Int = messages.size

    class ViewHolder(itemView: View, val context: Context) : RecyclerView.ViewHolder(itemView) {
        private val messageTitle: TextView = itemView.findViewById(R.id.messageTitle)
        private val messageNumber: TextView = itemView.findViewById(R.id.messageNumber)

        fun bind(message: message) {
            messageTitle.text = message.titleMessage
            messageNumber.text = "Сообщение № ${message.id}"

            itemView.setOnClickListener {
                val intent = Intent(context, EditActivity::class.java)
                val bundle = Bundle()
                bundle.putString("messageTitle", message.titleMessage)
                bundle.putString("messageBody", message.bodyMessage)
                bundle.putInt("messageUserId", message.userId)
                bundle.putInt("messageId", message.id)
                intent.putExtras(bundle)
                context.startActivity(intent)
            }
        }

    }
}