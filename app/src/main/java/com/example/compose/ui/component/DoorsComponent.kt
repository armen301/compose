package com.example.compose.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.R
import com.example.compose.ui.theme.Gray
import com.example.compose.ui.theme.TextSecondary

@Composable
fun DoorsComponent(data: DoorsData, onLock: () -> Unit = {}, onUnlock: () -> Unit = {}) {
    Column {
        Row(
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .padding(top = 4.dp, bottom = 8.dp),
        ) {
            Text(
                modifier = Modifier
                    .alignByBaseline()
                    .padding(end = 4.dp),
                text = stringResource(id = R.string.doors),
                style = MaterialTheme.typography.h6
            )
            Divider(
                color = TextSecondary,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .width(2.dp)
                    .height(20.dp)
            )
            Text(
                modifier = Modifier
                    .alignByBaseline()
                    .padding(horizontal = 4.dp),
                text = data.statusText,
                style = MaterialTheme.typography.body1,
                color = TextSecondary
            )
        }

        CardComponent(contentLeft = {
            if (data.lockedProcessing) {
                ProcessingComponent()
            } else {
                CircleButtonComponent(
                    color = data.lockColor,
                    content = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_lock),
                            contentDescription = null,
                            tint = Color.Unspecified
                        )
                    },
                    onButtonClick = onLock
                )
            }
        }) {
            if (data.unlockedProcessing) {
                ProcessingComponent()
                return@CardComponent
            }
            CircleButtonComponent(
                color = data.unlockColor,
                content = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_unlock),
                        contentDescription = null,
                        tint = Color.Unspecified
                    )
                },
                onButtonClick = onUnlock
            )
        }
    }
}

@Immutable
class DoorsData(
    val statusText: String = "Locked",
    val lockColor: Color = Gray,
    val unlockColor: Color = Color.Black,
    val lockedProcessing: Boolean = false,
    val unlockedProcessing: Boolean = false,
)

@Preview
@Composable
fun DoorsPreview() {
    DoorsComponent(DoorsData())
}
