package com.example.lemonade

import androidx.compose.ui.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lemonade.ui.theme.LemonadeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LemonadeTheme {
                LemonadeApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LemonadeApp() {
    var step by remember { mutableStateOf(1)}
    var tap by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Lemonade", fontWeight = FontWeight.Bold)
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = Color.Yellow
                )
            )
        }
    ) { innerPadding -> Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.tertiaryContainer),
            color = MaterialTheme.colorScheme.background
        ) {
            when (step) {
                1 -> {
                    LemonTextAndImage(
                        headingId = R.string.state1,
                        imageId = R.drawable.lemon_tree,
                        descriptionId = R.string.move1,
                        onImageClick = {
                            step = 2
                            tap = (2..4).random()
                        }
                    )
                }
                2 -> {
                    LemonTextAndImage(
                        headingId = R.string.state2,
                        imageId = R.drawable.lemon_squeeze,
                        descriptionId = R.string.move2,
                        onImageClick = {
                            tap--
                            if (tap == 0) {
                                step = 3
                            }
                        }
                    )
                }

                3 -> {
                    LemonTextAndImage(
                        headingId = R.string.state3,
                        imageId = R.drawable.lemon_drink,
                        descriptionId = R.string.move3,
                        onImageClick = {
                            step = 4
                        }
                    )
                }
                4 -> {
                    LemonTextAndImage(
                        headingId = R.string.state4,
                        imageId = R.drawable.lemon_restart,
                        descriptionId = R.string.move4,
                        onImageClick = {
                            step = 1
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun LemonTextAndImage(
    headingId: Int,
    imageId: Int,
    descriptionId: Int,
    onImageClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Button(
                onClick = onImageClick,
                shape = RoundedCornerShape(40.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Green)
            ) {
                Image(
                    painter = painterResource(imageId),
                    contentDescription = stringResource(descriptionId),
                    modifier = Modifier
                        .width(128.dp)
                        .height(160.dp)
                        .padding(24.dp)
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = stringResource(headingId),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LemonadeTheme {
        LemonadeApp()
    }
}