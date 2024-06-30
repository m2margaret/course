package com.example.artspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.artspace.ui.theme.ArtSpaceTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.font.FontWeight
import androidx.constraintlayout.compose.ConstraintLayout

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArtSpaceTheme {
                artApp()
            }
        }
    }
}

data class Arts(val imageId: Int, val descriptionId: Int)

@Composable
fun artApp() {
    val arts = listOf(
        Arts(R.drawable.pic1, R.string.name1),
        Arts(R.drawable.pic2, R.string.name2),
        Arts(R.drawable.pic3, R.string.name3)
    )
    var artIndex by remember { mutableStateOf(0) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround,
        modifier = Modifier.fillMaxSize()
    ) {
        ArtTextAndImage(
            imageId = arts[artIndex].imageId,
            descriptionId = arts[artIndex].descriptionId,
            onClickPrev = {
                artIndex = (artIndex - 1).takeIf { it >= 0 } ?: arts.size - 1
            },
            onClickNext = {
                artIndex = (artIndex + 1).takeIf { it < arts.size } ?: 0
            }
        )
    }

}

@Composable
fun ArtTextAndImage(
    imageId: Int,
    descriptionId: Int,
    onClickPrev: () -> Unit,
    onClickNext: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ) {
        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
        ) {
            val image = createRef()
            val text = createRef()
            val prevButton = createRef()
            val nextButton = createRef()

            Image(
                painter = painterResource(imageId),
                contentDescription = stringResource(descriptionId),
                modifier = Modifier
                    .constrainAs(image) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .width(300.dp)
                    .height(300.dp)
                    .padding(24.dp)
            )
            Text(
                text = stringResource(descriptionId),
                modifier = Modifier
                    .constrainAs(text) {
                        top.linkTo(image.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .wrapContentSize()
            )
            Button(
                onClick = onClickPrev,
                shape = RoundedCornerShape(40.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
                modifier = Modifier
                    .constrainAs(prevButton) {
                        top.linkTo(text.bottom)
                        start.linkTo(parent.start)
                        bottom.linkTo(parent.bottom)
                    }
                    .width(160.dp)
                    .height(70.dp)
                    .padding(10.dp),
            ) {
                Text(text = stringResource(R.string.prevButton))
            }
            Button(
                onClick = onClickNext,
                shape = RoundedCornerShape(40.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
                modifier = Modifier
                    .constrainAs(nextButton) {
                        top.linkTo(text.bottom)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }
                    .width(160.dp)
                    .height(70.dp)
                    .padding(10.dp),
            ) {
                Text(text = stringResource(R.string.nextButton))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ArtSpaceTheme {
        artApp()
    }
}