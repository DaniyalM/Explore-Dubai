package com.app.dubaiculture.ui.postLogin.attractions.adapters

import android.graphics.Color
import androidx.databinding.ViewDataBinding
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.attraction.local.models.AttractionCategory
import com.app.dubaiculture.databinding.AttractionsCategoryItemCellBinding
import com.app.dubaiculture.ui.postLogin.events.`interface`.RowClickListener
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.google.android.material.shape.CornerFamily
import com.xwray.groupie.databinding.BindableItem

class AttractionCategoryListItem<T : ViewDataBinding>(
    private val rowClickListener: RowClickListener? = null,
    val attractionCat: AttractionCategory? = null,
    val resLayout: Int = R.layout.attractions_category_item_cell,
    private val isArabic: Boolean,
) : BindableItem<T>() {
    override fun getLayout() = resLayout
    override fun bind(viewBinding: T, position: Int) {

        when (viewBinding) {

            is AttractionsCategoryItemCellBinding -> {

                viewBinding.attractionsCat = attractionCat
                YoYo.with(Techniques.RollIn)
                    .duration(1000)
                    .playOn(viewBinding.root)
                viewBinding.apply {
                    attractionImage.setOnClickListener {
                        rowClickListener!!.rowClickListener(position)
                    }
                    val radius = root.resources.getDimension(R.dimen.my_corner_radius)
                    val zeroDp = root.resources.getDimension(R.dimen.my_corner_radius)
                    if (isArabic)
                        attractionImage.shapeAppearanceModel =
                            attractionImage.shapeAppearanceModel
                                .toBuilder()
                                .setTopLeftCorner(CornerFamily.ROUNDED, radius)
                                .setBottomRightCornerSize(zeroDp)
                                .build()
                    else
                        attractionImage.shapeAppearanceModel =
                            attractionImage.shapeAppearanceModel
                                .toBuilder()
                                .setTopRightCorner(CornerFamily.ROUNDED, radius)
                                .setBottomLeftCornerSize(zeroDp)
                                .build()

                    if (!attractionCat?.color.isNullOrEmpty()) {
                        attractionImage.setCardBackgroundColor(Color.parseColor(attractionCat?.color))
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
                            attractionImage.outlineSpotShadowColor =
                                Color.parseColor(attractionCat?.color)
                        }
                    }
                }

            }

        }
    }
}