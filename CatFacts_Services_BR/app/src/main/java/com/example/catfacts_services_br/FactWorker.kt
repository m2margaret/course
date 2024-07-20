package com.example.catfacts_services_br

import android.content.Context
import android.content.Intent
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.catfacts_services_br.Networking.factApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.withContext

//позволяет выполнять задачи даже без доступа к интернету

class FactWorker(private val context: Context, workerParams: WorkerParameters) :
    CoroutineWorker(context, workerParams) {

    private val workerParams: WorkerParameters = workerParams

    override suspend fun doWork(): Result {
        return try {
            val facts = fetchFacts()
            if (facts.isNotEmpty()) {
                Result.success()
            } else {
                Result.failure()
            }
        } catch (e: Exception) {
            Result.failure()
        }

    }

    private suspend fun fetchFacts(): List<Fact> {
        return withContext(Dispatchers.IO) {
            try {
                val factText = workerParams.inputData.getString("fact") ?: return@withContext emptyList()
                val factLength = workerParams.inputData.getInt("factLength", 0)
                val response = factApi.postFact(factText, factLength)

                if (response.isSuccessful) {
                    val facts = response.body()
                    return@withContext facts ?: emptyList()
                } else {
                    return@withContext emptyList()
                }
            } catch (e: Exception) {
                return@withContext emptyList()
            }

        }
    }
}