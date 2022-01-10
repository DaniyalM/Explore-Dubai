package com.dubaiculture.ui.postLogin.events.adapters

import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import com.dubaiculture.R
import com.dubaiculture.data.repository.event.local.models.Events
import com.dubaiculture.databinding.AttractionDetailUpComingItemsBinding
import com.dubaiculture.databinding.EventItemsBinding
import com.dubaiculture.databinding.ItemEventListingBinding
import com.dubaiculture.databinding.UpcomingEventsInnerItemCellBinding
import com.dubaiculture.ui.postLogin.events.`interface`.FavouriteChecker
import com.dubaiculture.ui.postLogin.events.`interface`.RowClickListener
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.xwray.groupie.databinding.BindableItem

data class EventListItem<T : ViewDataBinding>(
    private val favChecker: FavouriteChecker? = null,
    private val rowClickListener: RowClickListener? = null,
    private val submitSurveyRowClickListener: SurveySubmitListener?=null,
    val event: Events,
    val resLayout: Int = R.layout.item_event_listing,
    val context: Context,
    val hasSurvey: Boolean = false
) : BindableItem<T>() {
    override fun getLayout() = resLayout
    override fun bind(viewBinding: T, position: Int) {
        when (viewBinding) {
            is UpcomingEventsInnerItemCellBinding -> {
                viewBinding.let {binding->
                    binding.events = event
                    if (event.isFavourite) {
                        binding.favourite.background =
                            ContextCompat.getDrawable(context, R.drawable.heart_icon_fav)
                    }

                    binding.favourite.setOnClickListener {
                        event.id?.let { itemId ->
                            favChecker!!.checkFavListener(
                                binding.favourite,
                                position,
                                event.isFavourite,
                                itemId
                            )

                        }
                    }
                    binding.cardview.setOnClickListener {
                        rowClickListener!!.rowClickListener(position)
                    }
                }
            }
            is ItemEventListingBinding -> {
                viewBinding.let { binding->
                    binding.events = event
                    if (event.isFavourite) {
                        binding.favourite.background =
                                ContextCompat.getDrawable(context, R.drawable.heart_icon_fav)
                    }
                    binding.favourite.setOnClickListener {
                        event.id?.let { itemId ->
                            favChecker!!.checkFavListener(
                                    binding.favourite,
                                    position,
                                    event.isFavourite,
                                    itemId
                            )

                        }
                    }
                    if (!event.isSurveySubmitted) {
                        binding.btnSurvery.visibility = View.VISIBLE
                        binding.btnSurvery.background = ContextCompat.getDrawable(
                            context,
                            R.drawable.my_event_btn_enable
                            //if you want to disable then use R.drawable.bg_btn_filled_disabled.
                            // change text color too white to purple.
                        )
                        binding.btnSurvery.text = context.resources.getString(R.string.submit_survey)
                        binding.btnSurvery.setOnClickListener {
                            submitSurveyRowClickListener?.submitBtnClickListener(position)
                        }

                    }else{
                        binding.btnSurvery.visibility = View.VISIBLE
                        binding.btnSurvery.background = ContextCompat.getDrawable(
                            context,
                            R.drawable.bg_btn_filled_disabled
                            //if you want to disable then use R.drawable.bg_btn_filled_disabled.
                            // change text color too white to purple.
                        )
                        binding.btnSurvery.text = context.resources.getString(R.string.submit_survey)
                        binding.btnSurvery.setTextColor(context.resources.getColor(R.color.white_900))
                    }

                    binding.cardview.setOnClickListener {
                        rowClickListener!!.rowClickListener(position)
                    }
                }
            }
            is EventItemsBinding -> {
                viewBinding.let {binding->
                    binding.events = event
                    if (event.isFavourite) {
                        binding.favourite.background =
                            ContextCompat.getDrawable(context, R.drawable.heart_icon_fav)
                    }
                    binding.favourite.setOnClickListener {
                        event.id?.let { itemId ->
                            favChecker!!.checkFavListener(
                                binding.favourite,
                                position,
                                event.isFavourite,
                                itemId
                            )

                        }
                    }
                    binding.cardview.setOnClickListener {
                        rowClickListener!!.rowClickListener(position)
                    }

                }
            }
            is AttractionDetailUpComingItemsBinding -> {
                viewBinding.let {binding->
                    YoYo.with(Techniques.Swing)
                        .duration(1000)
                        .playOn(binding.root)
                    binding.events = event
                    if (event.isFavourite) {
                        binding.favourite.background =
                            ContextCompat.getDrawable(context, R.drawable.heart_icon_fav)
                    }
                    binding.favourite.setOnClickListener {
                        event.id?.let { itemId ->
                            favChecker!!.checkFavListener(
                                binding.favourite,
                                position,
                                event.isFavourite,
                                itemId
                            )

                        }
                    }
                    binding.cardview.setOnClickListener {
                        rowClickListener!!.rowClickListener(position)
                    }
                }
            }
        }


    }

    interface SurveySubmitListener{
        fun submitBtnClickListener(position: Int)

    }
}