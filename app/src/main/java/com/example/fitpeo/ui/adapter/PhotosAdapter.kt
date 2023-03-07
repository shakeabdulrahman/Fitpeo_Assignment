package com.example.fitpeo.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.fitpeo.R
import com.example.fitpeo.data.model.Photos
import com.example.fitpeo.databinding.CellPhotosBinding

class PhotosAdapter(
    private var mListener: CallBack
) : PagingDataAdapter<Photos, PhotosAdapter.ViewHolder>(DiffCallback) {

    private var retry: (() -> Unit)? = null

    class ViewHolder(
        private var binding: CellPhotosBinding,
        listener: CallBack
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Photos) {
            binding.photoData = data
            binding.executePendingBindings()
        }

        init {
            binding.parentLayout.setOnClickListener {
                binding.photoData?.let { data ->
                    listener.onPhotoCardClick(data)
                }
            }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Photos>() {
        override fun areItemsTheSame(oldItem: Photos, newItem: Photos): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Photos,
            newItem: Photos
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            CellPhotosBinding.inflate(LayoutInflater.from(parent.context)),
            mListener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val photo = getItem(position)!!
        holder.bind(photo)
    }

    interface CallBack {
        fun onPhotoCardClick(photo: Photos)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < itemCount && getItem(position) == null) {
            R.layout.cell_photos
        } else {
            R.layout.cell_photos
        }
    }

    fun setRetry(retry: () -> Unit) {
        this.retry = retry
    }
}