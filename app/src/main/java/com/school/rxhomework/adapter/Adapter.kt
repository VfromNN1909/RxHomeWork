package com.school.rxhomework.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.school.rxhomework.databinding.ItemHolderBinding
import com.school.rxhomework.model.Item

class Adapter : ListAdapter<Item, Adapter.Holder>(DiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(parent)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
    }

    class Holder(private val binding: ItemHolderBinding) : RecyclerView.ViewHolder(binding.root) {
        constructor(parent: ViewGroup) : this(ItemHolderBinding.inflate(LayoutInflater.from(parent.context), parent, false))

        fun bind(item: Item) {
            binding.apply {
                titleTV.text = item.title
                bodyTV.text = item.body
            }
        }
    }
    object DiffCallback : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }
    }
}