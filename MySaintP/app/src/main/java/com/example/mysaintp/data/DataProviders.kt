package com.example.mysaintp.data

import com.example.mysaintp.model.Category
import com.example.mysaintp.model.Place
import com.example.mysaintp.R

object LocalCategoryDataProvider {
    val defaultCategory = getCategorysData()[0]

    fun getCategorysData(): List<Category> {
        return listOf(
            Category(
                id = 1,
                titleId = R.string.cafe,
                imageId = R.drawable.cafe,
                placeType = 1
            ),
            Category(
                id = 2,
                titleId = R.string.park,
                imageId = R.drawable.park,
                placeType = 2
            )
        )
    }
}

object LocalPlaceDataProvider {
    val defaultPlace = getPlaceData()[0]

    fun getPlaceData(): List<Place> {
        return listOf(
            Place(
                type = 2,
                id = 1,
                titleId = R.string.park1,
                imageId = R.drawable.park1,
                details = R.string.park1_text
            ),
            Place(
                type = 2,
                id = 2,
                titleId = R.string.park2,
                imageId = R.drawable.park2,
                details = R.string.park2_text
            ),
            Place(
                type = 1,
                id = 1,
                titleId = R.string.cafe1,
                imageId = R.drawable.cafe1,
                details = R.string.cafe1_text
            ),
            Place(
                type = 1,
                id = 2,
                titleId = R.string.cafe2,
                imageId = R.drawable.cafe2,
                details = R.string.cafe2_text
            )
        )
    }
}