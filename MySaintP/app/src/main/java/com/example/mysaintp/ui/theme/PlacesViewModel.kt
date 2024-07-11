package com.example.mysaintp.ui.theme

import androidx.lifecycle.ViewModel
import com.example.mysaintp.data.LocalPlaceDataProvider
import com.example.mysaintp.model.Place
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class PlacesViewModel() : ViewModel() {

    private val _uiState = MutableStateFlow(
        PlacesUiState(
            placesList = LocalPlaceDataProvider.getPlaceData(),
            currentPlace = LocalPlaceDataProvider.getPlaceData().getOrElse(0) {
                LocalPlaceDataProvider.defaultPlace
            }
        )
    )
    val uiState: StateFlow<PlacesUiState> = _uiState

    fun updateCurrentPlace(selectedPlace: Place) {
        _uiState.update {
            it.copy(currentPlace = selectedPlace)
        }
    }

    fun navigateToListPage() {
        _uiState.update {
            it.copy(isShowingListPage = true)
        }
    }

    fun navigateToDetailPage() {
        _uiState.update {
            it.copy(isShowingListPage = false)
        }
    }

}

data class PlacesUiState(
    val placesList: List<Place> = emptyList(),
    val currentPlace: Place = LocalPlaceDataProvider.defaultPlace,
    val isShowingListPage: Boolean = true
)