package com.app.dubaiculture.ui.postLogin.explore.mustsee.adapters

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.attraction.local.models.Attractions
import com.app.dubaiculture.databinding.MustSeeInnerItemCellBinding
import com.app.dubaiculture.ui.base.recyclerstuf.BaseRecyclerAdapter
import com.app.dubaiculture.ui.postLogin.attractions.AttractionListingFragment
import com.app.dubaiculture.ui.postLogin.events.`interface`.FavouriteChecker
import com.app.dubaiculture.ui.postLogin.explore.ExploreFragment
import com.app.dubaiculture.utils.AsyncCell
import kotlinx.android.synthetic.main.must_see_inner_item_cell.view.*

class MustSeeInnerAdapter(
    private val favChecker: FavouriteChecker? = null,
    private val fragment: ExploreFragment? = null,
) :
    BaseRecyclerAdapter<Attractions>() {


    var mustSees: List<Attractions>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun getItemCount() = mustSees.size

    inner class MustSeeViewHolder(view: ViewGroup) : RecyclerView.ViewHolder(view)
//    @BindingAdapter("imageUrl")
//    override fun loadImage(view: RoundedImageView, url: String?) {
//        glide.load(BuildConfig.BASE_URL+url).into(view)
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MustSeeViewHolder(MustSeeInnerItemCell(parent.context).apply { inflate() })
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MustSeeViewHolder -> setUpMustSeeViewHolder(holder, position)
        }
    }

    private inner class MustSeeInnerItemCell(context: Context) : AsyncCell(context, true) {
        var binding: MustSeeInnerItemCellBinding? = null
        override val layoutId = R.layout.must_see_inner_item_cell
        override fun createDataBindingView(view: View): View? {
            binding = MustSeeInnerItemCellBinding.bind(view)
            return view.rootView
        }
    }


    private fun setUpMustSeeViewHolder(
        holder: MustSeeInnerAdapter.MustSeeViewHolder,
        position: Int,
    ) {
        (holder.itemView as MustSeeInnerAdapter.MustSeeInnerItemCell).bindWhenInflated {

            holder.itemView.binding?.apply {
                attractions = mustSees[position]
                attraction_image.setOnClickListener {
                    fragment?.navigate(R.id.action_exploreFragment_to_attractionDetailFragment,
                        Bundle().apply {
                            val attraction = mustSees[position]
                            this.putString(AttractionListingFragment.ATTRACTION_DETAIL_ID,
                                attraction.id)
                            this.putString(AttractionListingFragment.ATTRACTION_DETAIL_IMAGE,
                                attraction.portraitImage)
                            this.putString(AttractionListingFragment.ATTRACTION_DETAIL_TITLE,
                                attraction.title)
                            this.putString(AttractionListingFragment.ATTRACTION_DETAIL_CATEGORY,
                                attraction.category)
                        })
                }
                favourite.setOnClickListener {
                    favChecker!!.checkFavListener(it as CheckBox,
                        position,
                        mustSees[position].IsFavourite)
                }



                if (!mustSees[position].color.isNullOrEmpty())
                    cardViewTitle.setCardBackgroundColor(Color.parseColor(mustSees[position].color))

            }

        }
    }


}