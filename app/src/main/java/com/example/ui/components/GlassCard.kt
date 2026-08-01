package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.ui.theme.GlassBorder
import com.example.ui.theme.GlassCardBg
import com.example.ui.theme.PrimaryBlue
import com.example.ui.theme.PrimaryCyan

@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 20.dp,
    borderColor: Color = GlassBorder,
    glowColor: Color? = null,
    onClick: (() -> Unit)? = null,
    content: @Composable BoxScope.() -> Unit
) {
    val shape = RoundedCornerShape(cornerRadius)

    val backgroundBrush = if (glowColor != null) {
        Brush.linearGradient(
            colors = listOf(
                glowColor.copy(alpha = 0.15f),
                GlassCardBg,
                Color.White.copy(alpha = 0.02f)
            )
        )
    } else {
        Brush.linearGradient(
            colors = listOf(
                Color.White.copy(alpha = 0.06f),
                Color.White.copy(alpha = 0.02f)
            )
        )
    }

    var cardModifier = modifier
        .clip(shape)
        .background(brush = backgroundBrush)
        .border(width = 1.dp, color = borderColor, shape = shape)

    if (onClick != null) {
        cardModifier = cardModifier.clickable { onClick() }
    }

    Box(
        modifier = cardModifier.padding(20.dp),
        content = content
    )
}
