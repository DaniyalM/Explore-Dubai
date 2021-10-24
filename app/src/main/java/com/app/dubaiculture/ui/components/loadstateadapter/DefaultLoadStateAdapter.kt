package com.app.dubaiculture.ui.components.loadstateadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.dubaiculture.databinding.LoadingPageStateItemBinding
import com.app.dubaiculture.utils.show

class DefaultLoadStateAdapter : LoadStateAdapter<DefaultLoadStateAdapter.LoadStateViewHolder>() {

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val binding = LoadingPageStateItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return LoadStateViewHolder(binding)
    }

    inner class LoadStateViewHolder(private val binding: LoadingPageStateItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

//        init {
//            binding.buttonRetry.setOnClickListener {
//                retry.invoke()
//            }
//        }

        fun bind(loadState: LoadState) {
            binding.apply {
                llProgress.show(loadState is LoadState.Loading)
//                buttonRetry.isVisible = loadState !is LoadState.Loading
//                textViewError.isVisible = loadState !is LoadState.Loading
            }
        }
    }
}
