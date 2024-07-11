package com.example.mysaintp.ui.theme

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mysaintp.data.LocalPlaceDataProvider
import com.example.mysaintp.model.Place
import com.example.mysaintp.utils.PlacesContentType
import com.example.mysaintp.R

@Composable
fun PlacesScreen(
    type: Int?,
    onBackPressed: () -> Unit
) {
    val viewModel: PlacesViewModel = viewModel()
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp

    val windowSize = when {
        screenWidthDp < 600 -> WindowWidthSizeClass.Compact
        screenWidthDp < 960 -> WindowWidthSizeClass.Medium
        else -> WindowWidthSizeClass.Expanded
    }
    val uiState by viewModel.uiState.collectAsState()
    Log.d("ФЛЩ", "ФДЩ")
    val contentType = when (windowSize) {
        WindowWidthSizeClass.Expanded -> PlacesContentType.ListAndDetail
        else -> PlacesContentType.ListOnly
    }

    Scaffold(
        topBar = {
            PlacesAppBar(
                isShowingListPage = uiState.isShowingListPage,
                onBackButtonClick = onBackPressed,
                windowSize = windowSize
            )
        }
    ) { innerPadding ->
        if (contentType == PlacesContentType.ListAndDetail) {
            PlacesListAndDetail(
                places = uiState.placesList,
                selectedPlace = uiState.currentPlace,
                onClick = {
                    viewModel.updateCurrentPlace(it)
                },
                onBackPressed = onBackPressed,
                contentPadding = innerPadding,
                modifier = Modifier.fillMaxWidth(),
                type = type
            )
        } else {
            if (uiState.isShowingListPage) {
                PlacesList(
                    places = uiState.placesList,
                    onClick = {
                        viewModel.updateCurrentPlace(it)
                        viewModel.navigateToDetailPage()
                    },
                    modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_medium)),
                    contentPadding = innerPadding,
                    type = type
                )
            } else {
                PlacesDetail(
                    selectedPlace = uiState.currentPlace,
                    contentPadding = innerPadding,
                    onBackPressed = {
                        viewModel.navigateToListPage()
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlacesAppBar(
    onBackButtonClick: () -> Unit,
    isShowingListPage: Boolean,
    windowSize: WindowWidthSizeClass,
    modifier: Modifier = Modifier
) {
    val isShowingDetailPage = windowSize != WindowWidthSizeClass.Expanded && !isShowingListPage
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.label),
                fontWeight = FontWeight.Bold
            )
        },
        navigationIcon = if (isShowingDetailPage) {
            {
                IconButton(onClick = onBackButtonClick) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        } else {
            { Box {} }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PlacesListItem(
    place: Place,
    onItemClick: (Place) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        elevation = CardDefaults.cardElevation(),
        modifier = modifier,
        shape = RoundedCornerShape(dimensionResource(R.dimen.card_corner_radius)),
        onClick = { onItemClick(place) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .size(dimensionResource(R.dimen.card_image_height))
        ) {
            PlacesListImageItem(
                place = place,
                modifier = Modifier.size(dimensionResource(R.dimen.card_image_height))
            )
            Column(
                modifier = Modifier
                    .padding(
                        vertical = dimensionResource(R.dimen.padding_small),
                        horizontal = dimensionResource(R.dimen.padding_medium)
                    )
                    .weight(1f)
            ) {
                Text(
                    text = stringResource(place.titleId),
                    style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = dimensionResource(R.dimen.card_text_vertical_space))
                )
            }
        }
    }
}

@Composable
private fun PlacesListImageItem(place: Place, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
    ) {
        Image(
            painter = painterResource(place.imageId),
            contentDescription = null,
            alignment = Alignment.Center,
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
private fun PlacesList(
    places: List<Place>,
    onClick: (Place) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    type: Int?
) {
    val places = places.filter { it.type == type }

    LazyColumn(
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium)),
        modifier = modifier.padding(top = dimensionResource(R.dimen.padding_medium)),
    ) {
        items(places, key = { place -> place.id }) { place ->
            PlacesListItem(
                place = place,
                onItemClick = onClick
            )
        }
    }
}

@Composable
private fun PlacesDetail(
    selectedPlace: Place,
    onBackPressed: () -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    BackHandler {
        onBackPressed()
    }
    val scrollState = rememberScrollState()
    val layoutDirection = LocalLayoutDirection.current
    Box(
        modifier = modifier
            .verticalScroll(state = scrollState)
            .padding(top = contentPadding.calculateTopPadding())
    ) {
        Column(
            modifier = Modifier
                .padding(
                    bottom = contentPadding.calculateTopPadding(),
                    start = contentPadding.calculateStartPadding(layoutDirection),
                    end = contentPadding.calculateEndPadding(layoutDirection)
                )
        ) {
            Box {
                Box {
                    Image(
                        painter = painterResource(selectedPlace.imageId),
                        contentDescription = null,
                        alignment = Alignment.TopCenter,
                        contentScale = ContentScale.FillWidth,
                    )
                }
                Column(
                    Modifier
                        .align(Alignment.BottomStart)
                        .fillMaxWidth()
                        .background(
                            Brush.verticalGradient(
                                listOf(Color.Transparent, MaterialTheme.colorScheme.scrim),
                                0f,
                                400f
                            )
                        )
                ) {
                    Text(
                        text = stringResource(selectedPlace.titleId),
                        style = MaterialTheme.typography.headlineLarge,
                        color = Color.Black,
                        modifier = Modifier
                            .padding(horizontal = dimensionResource(R.dimen.padding_small))
                    )
                    Row(
                        modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))
                    ) {
                        Spacer(Modifier.weight(1f))
                    }
                }
            }
            Text(
                text = stringResource(selectedPlace.details),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(
                    vertical = dimensionResource(R.dimen.padding_detail_content_vertical),
                    horizontal = dimensionResource(R.dimen.padding_detail_content_horizontal)
                )
            )
        }
    }
}

@Composable
private fun PlacesListAndDetail(
    places: List<Place>,
    selectedPlace: Place,
    onClick: (Place) -> Unit,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    type: Int?
) {
    val places = places.filter { it.type == type }

    Row(
        modifier = modifier
    ) {
        PlacesList(
            places = places,
            onClick = onClick,
            contentPadding = PaddingValues(
                top = contentPadding.calculateTopPadding(),
            ),
            modifier = Modifier
                .weight(2f)
                .padding(horizontal = dimensionResource(R.dimen.padding_medium)),
            type = type
        )
        PlacesDetail(
            selectedPlace = selectedPlace,
            modifier = Modifier.weight(3f),
            contentPadding = PaddingValues(
                top = contentPadding.calculateTopPadding(),
            ),
            onBackPressed = onBackPressed
        )
    }
}

@Preview
@Composable
fun PlacesListItemPreview() {
    MySaintPTheme {
        PlacesListItem(
            place = LocalPlaceDataProvider.defaultPlace,
            onItemClick = {}
        )
    }
}

@Preview
@Composable
fun PlacesListPreview() {
    MySaintPTheme {
        Surface {
            PlacesList(
                places = LocalPlaceDataProvider.getPlaceData(),
                onClick = {},
                type = 2
            )
        }
    }
}

@Preview(device = Devices.TABLET)
@Composable
fun PlacesListAndDetailsPreview() {
    MySaintPTheme {
        Surface {
            PlacesListAndDetail(
                places = LocalPlaceDataProvider.getPlaceData(),
                selectedPlace = LocalPlaceDataProvider.getPlaceData().getOrElse(0) {
                    LocalPlaceDataProvider.defaultPlace
                },
                onClick = {},
                onBackPressed = {},
                type = 2
            )
        }
    }
}
