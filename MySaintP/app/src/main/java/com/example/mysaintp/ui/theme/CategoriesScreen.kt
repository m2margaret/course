package com.example.mysaintp.ui.theme

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mysaintp.data.LocalCategoryDataProvider
import com.example.mysaintp.model.Category
import com.example.mysaintp.R

@Composable
fun CategoriesScreen(onItemClick: (Category, Int) -> Unit) {
    val categories = LocalCategoryDataProvider.getCategorysData()
    val contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    LazyColumn( //Создание ленивого столбца, который позволяет отображать большие списки элементов с прокруткой только видимых элементов
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium)),
        modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_medium)),
    ) {
        items(categories, key = { category -> category.id }) { category ->
            CategoriesListItem(
                category = category,
                onItemClick = onItemClick
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CategoriesListItem(
    category: Category,
    onItemClick: (Category, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        elevation = CardDefaults.cardElevation(),
        modifier = modifier,
        shape = RoundedCornerShape(dimensionResource(R.dimen.card_corner_radius)),
        onClick = { onItemClick(category, category.placeType) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .size(dimensionResource(R.dimen.card_image_height))
        ) {
            CategoriesListImageItem(
                category = category,
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
                    text = stringResource(category.titleId),
                    style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = dimensionResource(R.dimen.card_text_vertical_space))
                )
            }
        }
    }
}

@Composable
private fun CategoriesListImageItem(category: Category, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
    ) {
        Image(
            painter = painterResource(category.imageId),
            contentDescription = null,
            alignment = Alignment.Center,
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
private fun CategoriesList(
    categories: List<Category>,
    onClick: (Category, Int) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    LazyColumn(
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium)),
        modifier = modifier.padding(top = dimensionResource(R.dimen.padding_medium)),
    ) {
        items(categories, key = { category -> category.id }) { category ->
            CategoriesListItem(
                category = category,
                onItemClick = onClick
            )
        }
    }
}

@Preview
@Composable
fun CategoriesListItemPreview() {
    MySaintPTheme {
        CategoriesListItem(
            category = LocalCategoryDataProvider.defaultCategory,
            onItemClick = { _, _ -> }
        )
    }
}

@Preview
@Composable
fun CategoriesListPreview() {
    MySaintPTheme {
        Surface {
            CategoriesList(
                categories = LocalCategoryDataProvider.getCategorysData(),
                onClick = { _, _ -> },
            )
        }
    }
}
