package com.codepath.bitfit.adapter;

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.codepath.bitfit.R
import com.codepath.bitfit.databinding.ItemSleepBinding
import com.codepath.bitfit.model.SleepItem
import java.text.SimpleDateFormat
import java.util.*


class SleepAdapter : ListAdapter<SleepItem, SleepAdapter.SleepViewHolder>(
    DIFF_CALLBACK
) {



    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SleepViewHolder {
        return SleepViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: SleepViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    class SleepViewHolder(
        private val itemSleepBinding: ItemSleepBinding
    ) : RecyclerView.ViewHolder(itemSleepBinding.root){

        private val dateFormat = SimpleDateFormat("MMM dd yyyy", Locale.US)
        private val context = itemSleepBinding.root.context

        fun bind(item: SleepItem) {
            val date = dateFormat.format(item.date)
            itemSleepBinding.sleepDateTv.text = date


            val sleepTimeText = String.format(context.getString(R.string.slept_hours_format), item.sleepTime)
            itemSleepBinding.sleepTimeTv.text = sleepTimeText

            val feelingText = String.format(context.getString(R.string.feeling_format),
                item.feeling
            )
            itemSleepBinding.sleepFeelingTv.text = feelingText

            itemSleepBinding.sleepNotesTv.text = item.notes?:""


        }



        companion object {

            fun from(
                parent: ViewGroup
            ): SleepViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val itemSleepBinding = ItemSleepBinding.inflate(inflater, parent, false)
                return SleepViewHolder(itemSleepBinding)
            }

        }


    }


    companion object {

        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<SleepItem>() {
            override fun areItemsTheSame(
                oldItem: SleepItem,
                newItem: SleepItem
            ): Boolean {
                return oldItem.date == newItem.date
            }

            override fun areContentsTheSame(
                oldItem: SleepItem,
                newItem: SleepItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }


}