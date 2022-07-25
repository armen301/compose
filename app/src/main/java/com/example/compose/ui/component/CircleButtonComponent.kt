package com.example.compose.ui.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.R

@Composable
fun CircleButtonComponent(
    color: Color = Color.Black,
    content: @Composable () -> Unit,
    onButtonClick: () -> Unit = {}
) {
    Button(
        modifier = Modifier.size(dimensionResource(id = R.dimen.circle_button_size)),
        contentPadding = PaddingValues(0.dp),
        onClick = onButtonClick,
        shape = CircleShape,
        elevation = ButtonDefaults.elevation(defaultElevation = 0.dp, pressedElevation = 0.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = color)
    ) {
        content()
    }
}

@Preview
@Composable
fun CircleButtonPreview() {
    CircleButtonComponent(
        content = { Text(text = "START", color = MaterialTheme.colors.primary) }
    )
}
