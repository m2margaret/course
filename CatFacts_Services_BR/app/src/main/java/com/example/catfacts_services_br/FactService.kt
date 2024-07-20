package com.example.catfacts_services_br

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

//загрузка данных в фоновом режиме

class FactService() : Service() {
    private val factApi: FactApi = Networking.factApi
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        scope.launch {
            val facts = fetchFactsFromApi() //получение фактов из API
            Log.d("РАЗМЕРr", "updateFacts: ${facts.size}")
            sendFactsToActivity(facts) //отправили список
        }
        return START_STICKY
    }

    private suspend fun fetchFactsFromApi(): List<Fact> {
        return try {
            val response = factApi.getFacts()
            response
        } catch (e: Exception) {
            Log.e("API_ERROR", "Error fetching facts: ${e.message}")
            emptyList()
        }
    }

    private fun sendFactsToActivity(facts: List<Fact>) {
        val intent = Intent("facts_updated")
        intent.putExtra("facts", facts.toTypedArray())
        sendBroadcast(intent)
    }


}