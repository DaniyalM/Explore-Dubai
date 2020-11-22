package com.daniyal.basicappimpl.ui.callbacks

import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item

interface GroupieInterface {

    fun invokeSingleItemClick(item: Item<GroupieViewHolder>)
    fun invokeOnLongItemClick(item: Item<GroupieViewHolder>)

}