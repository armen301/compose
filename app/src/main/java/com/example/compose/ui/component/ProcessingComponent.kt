package com.example.compose.ui.component

import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ProcessingComponent() {
    CircularProgressIndicator(
        color = MaterialTheme.colors.secondary,
        strokeWidth = 2.dp,
        modifier = Modifier.size(60.dp)
    )
}

@Preview
@Composable
fun ProgressPreview() {
    ProcessingComponent()
}
