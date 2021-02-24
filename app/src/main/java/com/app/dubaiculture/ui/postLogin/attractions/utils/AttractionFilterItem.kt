package com.app.dubaiculture.ui.postLogin.attractions.utils

import android.widget.Filter
import com.app.dubaiculture.data.repository.explore.local.models.BaseModel
import com.app.dubaiculture.ui.postLogin.attractions.adapters.AttractionListScreenAdapter

class AttractionFilterItem(attractions:ArrayList<BaseModel>,private val adapterItem:AttractionListScreenAdapter) :Filter(){

    private var attractions:ArrayList<BaseModel> = attractions


    override fun performFiltering(text: CharSequence?): FilterResults {
        var constraint:CharSequence?=text
        val results=FilterResults()
        if (constraint!=null &&constraint.isNotEmpty()){
            constraint=constraint.toString().toUpperCase()
            val filterModels=ArrayList<BaseModel>()
            for (i in attractions.indices){
                if (attractions[i].title!!.toUpperCase().contains(constraint)){
                    filterModels.add(attractions[i])
                }
            }
            results.count=filterModels.size
            results.values=filterModels
        }else{
            results.count=attractions.size
            results.values=attractions
        }

        return results

    }

    override fun publishResults(text: CharSequence?, p1: FilterResults?) {
        if (attractions.isNullOrEmpty()){
            adapterItem.onNothingFound?.invoke()



        }else{
            attractions= p1?.values as ArrayList<BaseModel>


        }
        adapterItem.notifyDataSetChanged()
    }
}