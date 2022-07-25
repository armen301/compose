package com.example.compose.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.R

@Composable
fun EngineComponent(data: EngineData, onStart: () -> Unit, onStop: () -> Unit) {
    Column {
        Text(
            text = stringResource(id = R.string.engine),
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(top = 4.dp, bottom = 8.dp)
        )

        CardComponent(contentLeft = {
            CircleButtonComponent(
                color = data.startColor,
                content = {
                    Text(
                        text = stringResource(id = R.string.start),
                        color = MaterialTheme.colors.primary
                    )
                },
                onButtonClick = onStart
            )
        }) {
            CircleButtonComponent(
                color = data.stopColor,
                content = {
                    Text(
                        text = stringResource(id = R.string.stop),
                        color = MaterialTheme.colors.primary
                    )
                },
                onButtonClick = onStop
            )
        }
    }
}

class EngineData(
    val startColor: Color = Color.Black,
    val stopColor: Color = Color.Black,
)

@Preview
@Composable
fun EnginePreview() {
    EngineComponent(data = EngineData(), onStart = { }) {}
}
