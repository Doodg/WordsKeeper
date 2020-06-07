package com.enigma.wordskeeper.addwords

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.enigma.wordskeeper.R
import kotlinx.android.synthetic.main.sleep_timer_item_list.view.*


class TimerListAdapter(
    val context: Context,
    val onTimeSlotChecked: (position: Int) -> Unit
) :
    RecyclerView.Adapter<TimerListAdapter.ViewHolder>() {
    private var lastCheckedPosition = -1
    private var timeSlot = ArrayList<String>()


    fun setTimeSlots() {
        timeSlot.addAll(context.resources.getStringArray(R.array.time_slot))
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.sleep_timer_item_list, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return timeSlot.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.textViewTimeToUnlock.text = timeSlot.get(position)
        holder.itemView.radioButtonTimer.isChecked = position == lastCheckedPosition

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                lastCheckedPosition = getAdapterPosition()
                onTimeSlotChecked(adapterPosition)
                notifyDataSetChanged()
            }
            itemView.radioButtonTimer.setOnClickListener {
                lastCheckedPosition = getAdapterPosition()
                onTimeSlotChecked(adapterPosition)
                notifyDataSetChanged()
            }

        }
    }
}