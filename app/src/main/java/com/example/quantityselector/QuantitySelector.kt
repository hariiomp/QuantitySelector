package com.example.quantityselector

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@Composable
fun QuantitySelector(
    onAddClick: (Offset) -> Unit,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    var quantity by remember { mutableStateOf(0) }


    val textScale = remember { Animatable(1f) }
    val plusButtonScale = remember { Animatable(1f) }
    val minusButtonScale = remember { Animatable(1f) }
    var plusButtonPosition by remember { mutableStateOf(Offset.Zero) }

    LaunchedEffect(quantity) {
        textScale.animateTo(1.2f, tween(100))
        textScale.animateTo(1f, tween(100))
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        IconButton(
            onClick = {
                if (quantity > 0) {
                    quantity--
                    scope.launch {
                        minusButtonScale.animateTo(0.9f, tween(100))
                        minusButtonScale.animateTo(1f, tween(100))
                    }
                }
            }
        ) {
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = "Remove",
                modifier = Modifier.scale(minusButtonScale.value)
            )
        }

        Spacer(modifier = Modifier.width(24.dp))

        // Quantity Text
        Text(
            text = "$quantity",
            fontSize = 32.sp,
            modifier = Modifier
                .scale(textScale.value)
                .animateContentSize()
        )

        Spacer(modifier = Modifier.width(24.dp))

        IconButton(
            onClick = {
                quantity++
                onAddClick(plusButtonPosition)
                scope.launch {
                    plusButtonScale.animateTo(0.9f, tween(100))
                    plusButtonScale.animateTo(1f, tween(100))
                }
            },
            modifier = Modifier.onGloballyPositioned { coordinates ->
                plusButtonPosition = coordinates.positionInWindow()
            }
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add",
                modifier = Modifier.scale(plusButtonScale.value)
            )
        }
    }
}
