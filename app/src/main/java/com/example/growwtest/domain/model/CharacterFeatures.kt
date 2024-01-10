package com.example.growwtest.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/*
 * Created by Sudhanshu Kumar on 10/01/24.
 */

@Parcelize
data class CharacterFeatures(
    val features: List<CharacterFeature>
) : Parcelable