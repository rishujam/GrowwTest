package com.example.growwtest.domain.usecase

import com.example.growwtest.domain.Resource
import com.example.growwtest.domain.model.CharacterFeature
import com.example.growwtest.domain.model.CharacterFeatures
import com.example.growwtest.ui.filter.model.FeatureAttr
import com.example.growwtest.util.Constants
import javax.inject.Inject

/*
 * Created by Sudhanshu Kumar on 10/01/24.
 */

class GetCharacterSortFeatures @Inject constructor() {

    operator fun invoke(): CharacterFeatures {
        val order = listOf(
            FeatureAttr(Constants.SortOrder.ASCENDING),
            FeatureAttr(Constants.SortOrder.DESCENDING)
        )
        return CharacterFeatures(
            listOf(
                CharacterFeature.BirthYear("Birth Year", order),
                CharacterFeature.Created("Created", order),
                CharacterFeature.Edited("Edited", order),
                CharacterFeature.Mass("Mass", order),
                CharacterFeature.Height("Height", order)
            )
        )

    }

}