package dev.giridharaspk.clickcountercompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.giridharaspk.clickcountercompose.ui.theme.ClickCounterComposeTheme

class MainActivity : ComponentActivity() {
    private var counter by mutableStateOf(0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ClickCounterComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ClickCounter(counter) { onClickCount ->
                        counter = onClickCount
                    }
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        counter = 0
    }
}

@Composable
fun ClickCounter(counter: Int, clickListener: (Int) -> Unit) {

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Text("CLICK COUNT $counter")
        Button(
            onClick = { clickListener(counter + 1) },
            modifier = Modifier,
            shape = CutCornerShape(2),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
            border = BorderStroke(width = 4.dp, color = Color.Blue)
        ) {
            Text(text = "BUTTON")
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    ClickCounterComposeTheme {
//        ClickCounter(0)
    }
}

