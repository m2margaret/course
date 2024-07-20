package com.example.catfacts_services_br

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.catfacts_services_br.Networking.factApi
import kotlinx.coroutines.*
import retrofit2.Call

//AppCompatActivity()
//Отображает текст выбранного факта.
//Предоставляет возможность редактировать текст.
class EditActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fact_item_edit)

        val factEdit = findViewById<EditText>(R.id.factEdit)
        val saveButton = findViewById<Button>(R.id.saveButton)

        val factText = intent.getStringExtra("factText")
        factEdit.setText(factText ?: "")

        //oбработка сохранения факта
        saveButton.setOnClickListener {
            val factText = factEdit.text.toString()
            val factLength = factEdit.text.length

            // Запускаем WorkManager
            val workRequest = OneTimeWorkRequestBuilder<FactWorker>()
                .setInputData(workDataOf(
                    "fact" to factText,
                    "factLength" to factLength
                ))
                .build()
            WorkManager.getInstance(this).enqueue(workRequest)

            Toast.makeText(this, "Факт сохранен", Toast.LENGTH_SHORT).show()
            finish()
        }

    }
}
