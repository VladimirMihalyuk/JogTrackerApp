package com.example.jogtrackerapp.allRuns

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.jogtrackerapp.R
import com.example.jogtrackerapp.app.getDate
import com.example.jogtrackerapp.app.parseToTime
import com.example.jogtrackerapp.netwok.models.api.JogsItem

class RecyclerAdaptor(): ListAdapter<JogsItem, RecyclerAdaptor.ViewHolder>(
    DiffCallback()
){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    class ViewHolder (itemView: View): RecyclerView.ViewHolder(itemView){
        private val time: TextView = itemView.findViewById(R.id.time)
        private val distance: TextView = itemView.findViewById(R.id.distance)
        private val date: TextView = itemView.findViewById(R.id.date)

        fun bind(jogsItem: JogsItem){
            distance.text = "Distancee: ${jogsItem?.distance ?: 0} m"
            time.text = "Time: ${(jogsItem?.time ?: 0).parseToTime()}"
            date.text = "Date: ${((jogsItem?.date ?: 0) * 1000).getDate()}"
        }
    }
}


private class DiffCallback : DiffUtil.ItemCallback<JogsItem>() {
    override fun areItemsTheSame(oldItem: JogsItem, newItem: JogsItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: JogsItem, newItem: JogsItem): Boolean {
        return oldItem == newItem
    }
}
