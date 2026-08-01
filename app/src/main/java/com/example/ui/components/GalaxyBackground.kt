package com.example.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.PrimaryBlue
import com.example.ui.theme.PrimaryCyan
import kotlin.random.Random

private data class StarParticle(
    val xRatio: Float,
    val yRatio: Float,
    val baseRadius: Float,
    val color: Color,
    val speed: Float,
    val alphaOffset: Float
)

@Composable
fun GalaxyBackground(
    modifier: Modifier = Modifier
) {
    val stars = remember {
        val random = Random(42)
        List(100) {
            val colorVal = random.nextFloat()
            val color = when {
                colorVal < 0.4f -> PrimaryCyan.copy(alpha = 0.8f)
                colorVal < 0.7f -> PrimaryBlue.copy(alpha = 0.7f)
                else -> Color.White.copy(alpha = 0.9f)
            }
            StarParticle(
                xRatio = random.nextFloat(),
                yRatio = random.nextFloat(),
                baseRadius = random.nextFloat() * 2.5f + 1.0f,
                color = color,
                speed = random.nextFloat() * 0.5f + 0.2f,
                alphaOffset = random.nextFloat() * 6.28f
            )
        }
    }

    val infiniteTransition = rememberInfiniteTransition(label = "GalaxyAnimation")
    val time by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 6.28318f,
        animationSpec = infiniteRepeatable(
            animation = tween(12000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "TimeRotation"
    )

    val moonGlow by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 0.8f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "MoonGlow"
    )

    Canvas(modifier = modifier.fillMaxSize()) {
        val width = size.width
        val height = size.height

        // 1. Solid Dark Navy Base
        drawRect(color = DarkBackground)

        // 2. Radial Ambient Glow from top right (Moon/Nebula Effect)
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(
                    PrimaryCyan.copy(alpha = 0.15f * moonGlow),
                    PrimaryBlue.copy(alpha = 0.08f * moonGlow),
                    Color.Transparent
                ),
                center = Offset(width * 0.8f, height * 0.15f),
                radius = width * 0.65f
            ),
            center = Offset(width * 0.8f, height * 0.15f),
            radius = width * 0.65f
        )

        // 3. Radial Ambient Glow from bottom left
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(
                    PrimaryBlue.copy(alpha = 0.12f),
                    Color.Transparent
                ),
                center = Offset(width * 0.1f, height * 0.85f),
                radius = width * 0.5f
            ),
            center = Offset(width * 0.1f, height * 0.85f),
            radius = width * 0.5f
        )

        // 4. Draw Twinkling Galaxy Stars
        stars.forEach { star ->
            val sinVal = kotlin.math.sin(time * star.speed + star.alphaOffset)
            val currentAlpha = (0.3f + 0.7f * ((sinVal + 1f) / 2f)).coerceIn(0.1f, 1f)
            val yOffset = (kotlin.math.cos(time * 0.2f + star.alphaOffset) * 10f)

            val x = star.xRatio * width
            val y = (star.yRatio * height + yOffset + height) % height

            drawCircle(
                color = star.color.copy(alpha = star.color.alpha * currentAlpha),
                radius = star.baseRadius,
                center = Offset(x, y)
            )
        }
    }
}
