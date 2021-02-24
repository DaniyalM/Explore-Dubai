package com.app.dubaiculture.ui.postLogin.explore.mustsee.adapters

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.explore.local.models.BaseModel
import com.app.dubaiculture.databinding.MustSeeInnerItemCellBinding
import com.app.dubaiculture.ui.base.recyclerstuf.BaseRecyclerAdapter
import com.app.dubaiculture.utils.AsyncCell
import com.bumptech.glide.RequestManager
import timber.log.Timber

class MustSeeInnerAdapter(val glide: RequestManager) :
    BaseRecyclerAdapter() {


    var mustSees: List<BaseModel>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun getItemCount() = mustSees.size

    inner class MustSeeViewHolder(view: ViewGroup) : RecyclerView.ViewHolder(view)


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
        position: Int
    ) {
        (holder.itemView as MustSeeInnerAdapter.MustSeeInnerItemCell).bindWhenInflated {

                holder.itemView.binding?.mustsee = mustSees[position]
                if (!mustSees[position].color.isNullOrEmpty())
                holder.itemView.binding?.cardViewTitle?.setCardBackgroundColor(Color.parseColor(mustSees[position].color))


        }
    }


}