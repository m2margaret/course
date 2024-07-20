package com.example.catfacts_services_br

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class FactAdapter : RecyclerView.Adapter<FactAdapter.FactViewHolder>() {
    private var facts: List<Fact> = emptyList()
    var onItemClick: (Fact) -> Unit = {} //lambda-функция для обработки клика

    //oбновление списка фактов
    fun updateFacts(newFacts: List<Fact>?) {
        facts = newFacts ?: emptyList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FactViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.fact_item, parent, false)
        return FactViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FactViewHolder, position: Int) {
        val fact = facts[position]
        holder.factTextView.text = fact.fact
        holder.itemView.setOnClickListener {
            onItemClick(fact) //вызываем лямбда-функцию при клике
        }
    }

    override fun getItemCount(): Int {
        return facts.size
    }

    //внутренний класс для ViewHolder
    inner class FactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val factTextView: TextView = itemView.findViewById(R.id.factTextView)
    }
}