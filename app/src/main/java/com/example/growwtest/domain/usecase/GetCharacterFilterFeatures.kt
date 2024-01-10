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

class GetCharacterFilterFeatures @Inject constructor() {

    operator fun invoke(): CharacterFeatures {
        val colors = listOf(
            FeatureAttr(Constants.CharacterFeatureColor.YELLOW),
            FeatureAttr(Constants.CharacterFeatureColor.RED),
            FeatureAttr(Constants.CharacterFeatureColor.BLUE),
            FeatureAttr(Constants.CharacterFeatureColor.GRAY),
            FeatureAttr(Constants.CharacterFeatureColor.BLACK),
            FeatureAttr(Constants.CharacterFeatureColor.HAZEL),
            FeatureAttr(Constants.CharacterFeatureColor.BLOND),
            FeatureAttr(Constants.CharacterFeatureColor.NONE),
            FeatureAttr(Constants.CharacterFeatureColor.BROWN),
            FeatureAttr(Constants.CharacterFeatureColor.AUBURN),
            FeatureAttr(Constants.CharacterFeatureColor.NA),
            FeatureAttr(Constants.CharacterFeatureColor.FAIR),
            FeatureAttr(Constants.CharacterFeatureColor.GOLD),
            FeatureAttr(Constants.CharacterFeatureColor.WHITE),
            FeatureAttr(Constants.CharacterFeatureColor.LIGHT),
            FeatureAttr(Constants.CharacterFeatureColor.MOTTLED_GREEN),
            FeatureAttr(Constants.CharacterFeatureColor.ORANGE),
            FeatureAttr(Constants.CharacterFeatureColor.GREY),
            FeatureAttr(Constants.CharacterFeatureColor.GREEN),
        )
        return CharacterFeatures(
            listOf(
                CharacterFeature.EyeColor(Constants.CharacterFilterFeatures.EYE_COLOR, colors),
                CharacterFeature.Gender(
                    Constants.CharacterFilterFeatures.GENDER,
                    listOf(
                        FeatureAttr(Constants.Gender.NA),
                        FeatureAttr(Constants.Gender.FEMALE),
                        FeatureAttr(Constants.Gender.MALE)
                    )
                ),
                CharacterFeature.HairColor(
                    Constants.CharacterFilterFeatures.HAIR_COLOR,
                    colors
                ),
                CharacterFeature.SkinColor(Constants.CharacterFilterFeatures.SKIN_COLOR, colors)
            )
        )
    }

}