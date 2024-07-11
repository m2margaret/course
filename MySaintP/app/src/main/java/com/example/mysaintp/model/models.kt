package com.example.mysaintp.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Category(
    val id: Int,
    @StringRes val titleId: Int,
    @DrawableRes val imageId: Int,
    val placeType: Int = -1
)

data class Place(
    val type: Int,
    val id: Int,
    @StringRes val titleId: Int,
    @DrawableRes val imageId: Int,
    @StringRes val details: Int
)