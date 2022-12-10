package com.udacity.project4.locationreminders.reminderslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.udacity.project4.R
import com.udacity.project4.base.BaseRecyclerViewAdapter


//Use data binding to show the reminder on the item
class RemindersListAdapter(private val onClickEvent:OnClickListener,var items:List<ReminderDataItem>): RecyclerView.Adapter<RemindersListAdapter.ViewHolder>() {
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val title: TextView = itemView.findViewById(R.id.title)
        val des:TextView = itemView.findViewById(R.id.description)
        val location: TextView = itemView.findViewById(R.id.location)
    }
    interface OnClickListener{
        fun onClick(item: ReminderDataItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.it_reminder,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.title.text = item.title
        holder.des.text = item.description
        holder.location.text = "GooglePlex"//item.location
        holder.location.setOnClickListener {
            onClickEvent.onClick(item)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

}