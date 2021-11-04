package com.app.dubaiculture.ui.postLogin.plantrip.mytrip.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.dubaiculture.data.repository.trip.local.Duration
import com.app.dubaiculture.data.repository.trip.local.Trip
import com.app.dubaiculture.databinding.AttractionListItemCellBinding
import com.app.dubaiculture.databinding.ItemMySavedTripBinding
import com.app.dubaiculture.databinding.ItemTripDateBinding
import com.app.dubaiculture.ui.components.staggeredImagesView.StaggeredImagesView
import com.app.dubaiculture.ui.postLogin.attractions.clicklisteners.AttractionClickListener
import com.app.dubaiculture.ui.postLogin.events.`interface`.FavouriteChecker
import com.app.dubaiculture.ui.postLogin.plantrip.mytrip.adapter.clicklisteners.SaveTripClickListener
import com.bumptech.glide.RequestManager
import java.text.SimpleDateFormat

class SaveTripAdapter(
    val glide: RequestManager,
    val rowClickListener: SaveTripClickListener
) : PagingDataAdapter<Trip, SaveTripAdapter.TripViewHolder>(object :
    DiffUtil.ItemCallback<Trip>() {
    override fun areItemsTheSame(
        oldItem: Trip,
        newItem: Trip
    ) = oldItem.hashCode() == newItem.hashCode()


    override fun areContentsTheSame(
        oldItem: Trip,
        newItem: Trip
    ) = oldItem.tripId == newItem.tripId
}) {

    inner class TripViewHolder(
        val binding: ItemMySavedTripBinding,
        val rowClickListener: SaveTripClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        val input = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val output = SimpleDateFormat("dd MMMM,yyyy")

        fun bind(trip: Trip) {
            val startDate = output.format(input.parse(trip.startDate))
            val endDate = output.format(input.parse(trip.endDate))
            binding.data = trip
            binding.tvTripDates.text =
                startDate.substring(0, 2) + " - " + endDate.substring(0, 2) + " "+ endDate.substring(3)
            trip.images.apply {
                binding.staggeredImageView.initialize(
                    this,
                    clickListener = object :
                        StaggeredImagesView.StaggeredImagesClickListener {
                        override fun onClick(pos: Int) {
                        }
                    }, glide!!
                )
            }
            binding.cvTrip.setOnClickListener {
                rowClickListener.rowClickListener(trip)

            }
        }
    }

    override fun onBindViewHolder(holder: TripViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripViewHolder {
        return TripViewHolder(
            ItemMySavedTripBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            rowClickListener
        )
    }

}