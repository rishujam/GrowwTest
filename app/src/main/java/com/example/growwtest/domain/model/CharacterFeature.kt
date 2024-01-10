package com.example.growwtest.domain.model

import android.os.Parcelable
import com.example.growwtest.ui.filter.model.FeatureAttr
import kotlinx.parcelize.Parcelize

/*
 * Created by Sudhanshu Kumar on 10/01/24.
 */

@Parcelize
sealed class CharacterFeature(
    val id: String,
    val attributes: List<FeatureAttr>,
    val selected: Boolean? = null
): Parcelable {

    @Parcelize
    data class Gender(
        val name: String,
        val attrs: List<FeatureAttr>,
        val isSelected: Boolean? = false
    ) : CharacterFeature(name, attrs, isSelected), Parcelable

    @Parcelize
    data class EyeColor(
        val name: String,
        val attrs: List<FeatureAttr>,
        val isSelected: Boolean? = false
    ) : CharacterFeature(name, attrs, isSelected), Parcelable

    @Parcelize
    data class HairColor(
        val name: String,
        val attrs: List<FeatureAttr>,
        val isSelected: Boolean? = false
    ) : CharacterFeature(name, attrs, isSelected), Parcelable

    @Parcelize
    data class SkinColor(
        val name: String,
        val attrs: List<FeatureAttr>,
        val isSelected: Boolean? = false
    ) : CharacterFeature(name, attrs, isSelected), Parcelable

    @Parcelize
    data class BirthYear(
        val name: String,
        val attrs: List<FeatureAttr>,
        val isSelected: Boolean? = false
    ) : CharacterFeature(name, attrs, isSelected), Parcelable

    @Parcelize
    data class Height(
        val name: String,
        val attrs: List<FeatureAttr>,
        val isSelected: Boolean? = false
    ) : CharacterFeature(name, attrs, isSelected), Parcelable

    @Parcelize
    data class Created(
        val name: String,
        val attrs: List<FeatureAttr>,
        val isSelected: Boolean? = false
    ) : CharacterFeature(name, attrs, isSelected), Parcelable

    @Parcelize
    data class Edited(
        val name: String,
        val attrs: List<FeatureAttr>,
        val isSelected: Boolean? = false
    ) : CharacterFeature(name, attrs, isSelected), Parcelable

    @Parcelize
    data class Mass(
        val name: String,
        val attrs: List<FeatureAttr>,
        val isSelected: Boolean? = false
    ) : CharacterFeature(name, attrs, isSelected), Parcelable
}
