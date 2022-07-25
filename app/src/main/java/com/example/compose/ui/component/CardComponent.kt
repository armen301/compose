package com.example.compose.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.R

@Composable
fun CardComponent(
    contentLeft: @Composable () -> Unit,
    contentRight: @Composable () -> Unit,
) {
    Card {
        Row(horizontalArrangement = Arrangement.SpaceBetween) {
            Box(modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 8.dp, bottom = 16.dp)) {
                contentLeft()
            }

            Box(modifier = Modifier.padding(top = 16.dp, end = 16.dp, bottom = 16.dp)) {
                contentRight()
            }
        }
    }
}

@Preview
@Composable
fun CardComponentPreview() {
    CardComponent(
        {
            CircleButtonComponent(content = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_unlock),
                    contentDescription = null
                )
            }) {}
        },
        {
            CircleButtonComponent(content = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_unlock),
                    contentDescription = null
                )
            }) {}
        }
    )
}
