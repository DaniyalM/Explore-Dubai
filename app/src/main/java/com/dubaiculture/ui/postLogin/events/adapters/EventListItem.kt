package com.dubaiculture.ui.postLogin.events.adapters

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.dubaiculture.R
import com.dubaiculture.data.repository.event.local.models.Events
import com.dubaiculture.databinding.AttractionDetailUpComingItemsBinding
import com.dubaiculture.databinding.EventItemsBinding
import com.dubaiculture.databinding.ItemEventListingBinding
import com.dubaiculture.databinding.UpcomingEventsInnerItemCellBinding
import com.dubaiculture.ui.postLogin.events.`interface`.FavouriteChecker
import com.dubaiculture.ui.postLogin.events.`interface`.RowClickListener
import com.dubaiculture.utils.hide
import com.dubaiculture.utils.show
import com.xwray.groupie.databinding.BindableItem

data class EventListItem<T : ViewDataBinding>(
    private val favChecker: FavouriteChecker? = null,
    private val rowClickListener: RowClickListener? = null,
    private val submitSurveyRowClickListener: SurveySubmitListener? = null,
    val event: Events,
    val resLayout: Int = R.layout.item_event_listing,
    val context: Context,
    val hasSurvey: Boolean = false,
    val myEvents: Boolean = false
) : BindableItem<T>() {
    override fun getLayout() = resLayout
    override fun bind(viewBinding: T, position: Int) {
        when (viewBinding) {
            is UpcomingEventsInnerItemCellBinding -> {
                viewBinding.let { binding ->
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
                viewBinding.let { binding ->
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

                    binding.btnSurvery.text = context.resources.getString(R.string.submit_survey)
                    if (myEvents) {
                        binding.btnSurvery.show()
                        if (!event.IsSurveyed && hasSurvey) {
                            binding.btnSurvery.background = ContextCompat.getDrawable(
                                context,
                                R.drawable.my_event_btn_enable
                                //if you want to disable then use R.drawable.bg_btn_filled_disabled.
                                // change text color too white to purple.
                            )
                            binding.btnSurvery.setOnClickListener {
                                submitSurveyRowClickListener?.submitBtnClickListener(position)
                            }

                        } else {
                            binding.btnSurvery.isEnabled = false
                            binding.btnSurvery.text=context.resources.getString(R.string.survey_submitted)
                            binding.btnSurvery.background = ContextCompat.getDrawable(
                                context,
                                R.drawable.bg_btn_filled_disabled
                                //if you want to disable then use R.drawable.bg_btn_filled_disabled.
                                // change text color too white to purple.
                            )
                            binding.btnSurvery.setTextColor(
                                ContextCompat.getColor(
                                    context,
                                    R.color.white_900
                                )
                            )
                        }
                    } else {
                        binding.btnSurvery.hide()
                    }


                    binding.cardview.setOnClickListener {
                        rowClickListener!!.rowClickListener(position)
                    }
                }
            }
            is EventItemsBinding -> {
                viewBinding.let { binding ->
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
                viewBinding.let { binding ->
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

    interface SurveySubmitListener {
        fun submitBtnClickListener(position: Int)

    }
}