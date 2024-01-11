package com.example.growwtest.ui.filter.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/*
 * Created by Sudhanshu Kumar on 10/01/24.
 */

@Parcelize
data class FeatureAttr(
    val name: String,
    var isChecked: Boolean? = false
) : Parcelable
