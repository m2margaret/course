package com.example.catfacts_services_br

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

//Получает список фактов из API.
//Отправляет список фактов в MainActivity с помощью BroadcastReceiver.
class FactReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == "facts_updated") {
            val facts = intent?.getParcelableArrayExtra("facts") as List<Fact>
            // Получаем экземпляр MainActivity
            val mainActivity = context as? MainActivity ?: return // Если context не MainActivity, выходим

            // Обновляем RecyclerView в MainActivity
            mainActivity.updateRecyclerView(facts)

        }

    }
}

