package com.app.dubaiculture.data.repository.explore.local.models

data class AttractionCategory(val id: Int, val title: String, val icon: String, val imgSelected: Int=0,
                              val imgUnSelected: Int=0, var isSelected:Boolean=false,val color: String?="")