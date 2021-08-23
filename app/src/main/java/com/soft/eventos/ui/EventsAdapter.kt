package com.soft.eventos.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.soft.eventos.R
import com.soft.eventos.data.model.Events
import com.soft.eventos.ui.EventsAdapter.*
import com.soft.eventos.utils.DateTime
import kotlinx.android.synthetic.main.item_events.view.*
import java.time.format.DateTimeFormatter

class EventsAdapter(
    private val listEvents: MutableList<Events> = mutableListOf(),
    val listener: ListEventsFragment
) : RecyclerView.Adapter<DataViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder =
        DataViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_events, parent, false)
        )

    inner class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(events: Events) {
            itemView.apply {
                nameEvents.text = events.title
                dateEvents.text = "Data: " + DateTime.getDate(events.date)
                priceEvents.text = "R$" + events.price
                Glide.with(imgEvents.context)
                    .load(events.image)
                    .into(imgEvents)
            }
        }
    }


    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val event = listEvents[position]
        holder.bind(listEvents[position])
//        holder.bindClick(event, position, listener)
    }

    override fun getItemCount(): Int = listEvents.size

    fun addEvents(events: List<Events>) {
        this.listEvents.apply {
            clear()
            addAll(events)
            notifyDataSetChanged()
        }
    }
}