package com.example.happybirthday

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.happybirthday.ui.theme.HAPPYBIRTHDAYTheme

class Logger {
    companion object {
        fun logLifecycleEvent(tag: String, methodName: String) {
            Log.d(tag, "$methodName was called")
        }
    }
}

class MainActivity : ComponentActivity() {
    private val tag = "lifeСycle"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Logger.logLifecycleEvent(tag, "onCreate")

        finish()
        setContent {
            HAPPYBIRTHDAYTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GreetingText(
                        message = "Happy Birthday Margo!",
                        from = "From Sasha",
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    }
    override fun onStart() {
        super.onStart()
        Logger.logLifecycleEvent(tag, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Logger.logLifecycleEvent(tag, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Logger.logLifecycleEvent(tag, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Logger.logLifecycleEvent(tag, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Logger.logLifecycleEvent(tag, "onDestroy")
    }
}

@Composable
fun GreetingText(message: String, from: String, modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Text(
            text = message,
            fontSize = 100.sp,
            lineHeight = 116.sp,
            textAlign = TextAlign.Center
        )
        Text(
            text = from,
            fontSize = 36.sp,
            modifier = Modifier
                .padding(16.dp)
                .align(alignment = Alignment.End)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BirthdayCardPreview() {
    HAPPYBIRTHDAYTheme {
        GreetingText(message = "Happy Birthday Margo!", from = "From Sasha")
    }
}