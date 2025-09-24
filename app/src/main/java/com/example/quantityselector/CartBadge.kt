package com.example.quantityselector

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import com.example.quantityselector.R

@Composable
fun CartBadge(
    cartCount: Int,
    onPositioned: (Offset) -> Unit
) {
    val density = LocalDensity.current

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Box(
            modifier = Modifier
                .size(320.dp)
                .onGloballyPositioned { coordinates ->
                    val position = coordinates.positionInWindow()
                    val size = coordinates.size
                    val centerOffset = Offset(
                        position.x + size.width / 2f,
                        position.y + size.height / 2f
                    )
                    onPositioned(centerOffset)
                }
        ) {
            Image(
                painter = painterResource(id = R.drawable.cart),
                contentDescription = "Cart",
                modifier = Modifier.fillMaxSize()
            )

            if (cartCount > 0) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(Color.DarkGray, shape = CircleShape)
                        .align(Alignment.TopEnd),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "$cartCount",
                        color = Color.White,
                        fontSize = 18.sp,
                        maxLines = 1
                    )
                }
            }
        }
    }
}
