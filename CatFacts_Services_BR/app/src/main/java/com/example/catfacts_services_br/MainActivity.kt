package com.example.catfacts_services_br

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.RecyclerView

// позволяют создавать приложения, работающие в фоновом режиме

class MainActivity : ComponentActivity() {

    private val factsReceiver = FactReceiver()
    private lateinit var adapter: FactAdapter
    private lateinit var receiver: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = FactAdapter()
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.adapter = adapter

        //регистрация Broadcast Receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(
            factsReceiver,
            IntentFilter("facts_updated")
        )

        //запуск Service
        val factService = Intent(this, FactService::class.java)
        startService(factService)

        //настройка клика по факту
        adapter.onItemClick = { fact ->
            val intent = Intent(this, EditActivity::class.java)
            val bundle = Bundle()
            bundle.putString("factText", fact.fact)
            bundle.putInt("factLength", fact.length)
            intent.putExtras(bundle)
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        unregisterReceiver(receiver)
        super.onDestroy()
    }

    fun updateRecyclerView(facts: List<Fact>) { //обновляем ui
        Log.d("РАЗМЕРr", "updateFacts: ${facts.size}")
        adapter.updateFacts(facts)
    }

}