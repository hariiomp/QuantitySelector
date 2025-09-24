package com.example.quantityselector

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProductScreen()
        }
    }
}

@Composable
fun ProductScreen() {
    val scope = rememberCoroutineScope()

    var showFlyingItem by remember { mutableStateOf(false) }
    var startPosition by remember { mutableStateOf(Offset.Zero) }
    var endPosition by remember { mutableStateOf(Offset.Zero) }
    var cartCount by remember { mutableStateOf(0) }

    val flyingItemAnimatable = remember { Animatable(0f) }
    val density = LocalDensity.current

    Box(modifier = Modifier.fillMaxSize()) {

        CartBadge(cartCount = cartCount) { centerOffset ->
            endPosition = centerOffset
        }

        QuantitySelector(
            onAddClick = { plusBtnPosition ->
                startPosition = plusBtnPosition
                showFlyingItem = true

                scope.launch {
                    flyingItemAnimatable.animateTo(
                        targetValue = 1f,
                        animationSpec = tween(
                            durationMillis = 1200,
                            easing = FastOutSlowInEasing
                        )
                    )
                    showFlyingItem = false
                    flyingItemAnimatable.snapTo(0f)
                    cartCount++
                }
            },
            modifier = Modifier
                .align(androidx.compose.ui.Alignment.BottomCenter)
                .padding(bottom = 100.dp)
        )


        if (showFlyingItem) {
            val offsetInPx = remember(flyingItemAnimatable.value) {
                Offset(
                    lerp(startPosition.x, endPosition.x, flyingItemAnimatable.value) - 50,
                    lerp(startPosition.y, endPosition.y, flyingItemAnimatable.value) - 50
                )
            }

            val animatedOffsetDp = with(density) {
                offsetInPx.copy(
                    x = offsetInPx.x.toDp().value,
                    y = offsetInPx.y.toDp().value
                )
            }

            Image(
                painter = painterResource(id = R.drawable.cart),
                contentDescription = "Flying Cart",
                modifier = Modifier
                    .offset(x = animatedOffsetDp.x.dp, y = animatedOffsetDp.y.dp)
                    .size(100.dp)
            )
        }
    }
}


private fun lerp(start: Float, stop: Float, fraction: Float): Float {
    return start + (stop - start) * fraction
}
