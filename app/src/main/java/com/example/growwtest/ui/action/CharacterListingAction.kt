package com.example.growwtest.ui.action

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/*
 * Created by Sudhanshu Kumar on 10/01/24.
 */

@Parcelize
sealed class CharacterListingAction : Parcelable {

    @Parcelize
    data object Filter : CharacterListingAction(), Parcelable
    @Parcelize
    data object Sort : CharacterListingAction(), Parcelable

}