package com.dubaiculture.ui.postLogin.more.about.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dubaiculture.R
import com.dubaiculture.data.repository.more.local.Library
import com.dubaiculture.databinding.ItemLibrariesBinding
import com.dubaiculture.ui.postLogin.more.about.adapter.clicklisteners.LibraryClickListener

class LibrariesAdapter(val rowClickListener: LibraryClickListener) :
    ListAdapter<Library, LibrariesAdapter.LibraryViewHolder>(LibraryDiffCallback()) {

    inner class LibraryViewHolder(
        val binding: ItemLibrariesBinding,
        val rowClickListener: LibraryClickListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(library: Library) {
            binding.library = library


            binding.rootView.setOnClickListener {
                rowClickListener.rowClickListener(library)
                rowClickListener.rowClickListener(library, absoluteAdapterPosition)
            }
        }
    }

    class LibraryDiffCallback : DiffUtil.ItemCallback<Library>() {
        override fun areItemsTheSame(
            oldItem: Library,
            newItem: Library
        ): Boolean =
            oldItem.name == newItem.name

        override fun areContentsTheSame(
            oldItem: Library,
            newItem: Library
        ): Boolean =
            oldItem.hashCode() == newItem.hashCode()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibraryViewHolder {
        return LibraryViewHolder(
            ItemLibrariesBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            rowClickListener
        )
    }

    override fun onBindViewHolder(holder: LibraryViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

}