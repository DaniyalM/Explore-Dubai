package com.app.dubaiculture.ui.base.recyclerstuf

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.dubaiculture.utils.AsyncCell

abstract class BaseAsyncRecyclerAdapter<T>(
    val resLayout: Int,
    val wrapLayout: Boolean = false,
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class ListViewHolder(view: ViewGroup) : RecyclerView.ViewHolder(view)

    inner class GenericAsyncCell(
        context: Context,
    ) : AsyncCell(context, wrapLayout) {
        private var binding: T? = null
        override val layoutId = resLayout
        override fun createDataBindingView(view: View): View? {
            binding = getBindingForView()
            return view.rootView
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ListViewHolder(GenericAsyncCell(parent.context).apply { inflate() })
    }

    abstract fun getBindingForView(): T
    abstract fun applyBindingAnimation(view: View)

    abstract fun setUpListViewHolder(listViewHolder: ListViewHolder, position: Int)


}