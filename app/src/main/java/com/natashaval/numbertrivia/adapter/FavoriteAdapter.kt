package com.natashaval.numbertrivia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.natashaval.numbertrivia.databinding.ItemFavoriteBinding
import com.natashaval.numbertrivia.model.NumberData

class FavoriteAdapter(private val onItemClicked: (NumberData) -> Unit) :
  ListAdapter<NumberData, FavoriteAdapter.FavoriteViewHolder>(DiffCallback) {
  class FavoriteViewHolder(private val binding: ItemFavoriteBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(numberData: NumberData) {
      binding.apply {
        btNumber.text = String.format(numberData.number.toString())
        tvDesc.text = numberData.description
      }
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
    return FavoriteViewHolder(ItemFavoriteBinding.inflate(
      LayoutInflater.from(parent.context), parent, false))
  }

  override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
    val current = getItem(position)
    holder.bind(current)
    holder.itemView.setOnClickListener { onItemClicked(current) }
  }

  companion object DiffCallback : DiffUtil.ItemCallback<NumberData>() {
    override fun areItemsTheSame(oldItem: NumberData, newItem: NumberData): Boolean {
      return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: NumberData, newItem: NumberData): Boolean {
      return oldItem == newItem
    }
  }
}