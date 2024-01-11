package com.example.growwtest.ui.lsitener

import com.example.growwtest.domain.model.CharacterFeatures

/*
 * Created by Sudhanshu Kumar on 11/01/24.
 */

interface BottomSheetListener {

    fun onApplyClickListener(selectedFeatures: CharacterFeatures)

    fun onClearClickListener()

}