package com.example.compose.ui.component

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.R

@Composable
fun ScreenTitleComponent(primaryText: String, secondaryText: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(end = 4.dp),
            text = primaryText,
            style = MaterialTheme.typography.h6
        )
        Divider(
            color = MaterialTheme.colors.secondary,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .width(2.dp)
                .fillMaxHeight()
        )
        IconWithTextComponent(
            iconResource = R.drawable.ic_gas,
            text = secondaryText,
            style = MaterialTheme.typography.subtitle2.copy(fontWeight = FontWeight.Bold),
        )
    }
}

@Composable
@SuppressLint("ModifierParameter")
fun IconWithTextComponent(
    modifier: Modifier = Modifier,
    @DrawableRes iconResource: Int,
    text: String,
    style: TextStyle = LocalTextStyle.current,
) {
    Row(modifier) {
        Icon(painter = painterResource(id = iconResource), contentDescription = null, tint = Color.Unspecified)
        Text(text = text, modifier = Modifier.align(Alignment.CenterVertically), style = style)
    }
}

@Preview
@Composable
fun TitlePreview() {
    ScreenTitleComponent("QX 55", "120ml")
}
