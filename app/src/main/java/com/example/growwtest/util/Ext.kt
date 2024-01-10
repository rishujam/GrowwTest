package com.example.growwtest.util

import android.view.View
import com.example.growwtest.data.model.EntityPeopleListing
import com.example.growwtest.data.model.ResPeopleListing

/*
 * Created by Sudhanshu Kumar on 10/01/24.
 */
 
fun View.hide() {
    this.visibility = View.GONE
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun EntityPeopleListing.toResPeopleListing(): ResPeopleListing {
    return ResPeopleListing(
        count = count,
        next = if(next == "null") null else next,
        previous = previous,
        results = results
    )
}