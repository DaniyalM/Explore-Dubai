package com.dubaiculture.ui.postLogin.attractions.detail.ibecon.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.dubaiculture.R
import com.dubaiculture.data.repository.sitemap.local.BeaconItems
import com.dubaiculture.databinding.ItemsYourJourneyBinding
import com.dubaiculture.ui.postLogin.attractions.detail.ibecon.adapter.clicklisteners.BeaconClickListener
import com.dubaiculture.utils.glideInstance
import com.dubaiculture.utils.hide

class BeaconListAdapter(
    private val beaconClickListener: BeaconClickListener
) : ListAdapter<BeaconItems, BeaconListAdapter.BeaconListViewHolder>(
    BeaconComparator()
) {

    class BeaconComparator : DiffUtil.ItemCallback<BeaconItems>() {
        override fun areItemsTheSame(oldItem: BeaconItems, newItem: BeaconItems) =
            oldItem.hashCode() == newItem.hashCode()

        override fun areContentsTheSame(oldItem: BeaconItems, newItem: BeaconItems) =
            oldItem.id == newItem.id

    }


    inner class BeaconListViewHolder(
        val binding: ItemsYourJourneyBinding,
        private val beaconClickListener: BeaconClickListener,
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(beaconItem: BeaconItems) {
            binding.apply {
                tvCircle.text = beaconItem.id.toString()
                if (beaconItem.id == 1) {
                    binding.linesScheduleUP.hide()
                }
                YoYo.with(Techniques.BounceInDown)
                    .duration(2000)
                    .playOn(root)
                rootlayout.setOnClickListener {
                    beaconClickListener.onClick(beaconItem)
//                        rowClickListener!!.rowClickListener(position)
                }
                if (beaconItem.visited) {
                    rootlayout.setOnClickListener {
                        beaconClickListener.onClick(beaconItem)
//                        rowClickListener!!.rowClickListener(position)
                    }
                } else {
                    rootlayout.alpha = 0.5f
                    tickMark.visibility = View.GONE
                    tvCircle.setBackgroundResource(R.drawable.circle_purple)
//                    tvCircle.setTextColorRes(R.color.black_200)
                }


//                tvCircle.text = beaconItem.step
                title.text = beaconItem.title

                imgMuseums.glideInstance(beaconItem.image, false)
                    .into(imgMuseums)
            }


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        BeaconListViewHolder(
            ItemsYourJourneyBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            beaconClickListener

        )

    override fun onBindViewHolder(holder: BeaconListViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }
}
