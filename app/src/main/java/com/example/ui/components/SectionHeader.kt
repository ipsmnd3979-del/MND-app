package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.PrimaryBlue
import com.example.ui.theme.PrimaryCyan
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextWhite

@Composable
fun SectionHeader(
    title: String,
    badgeText: String? = null,
    subtitle: String? = null,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (badgeText != null) {
            Box(
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(PrimaryBlue.copy(alpha = 0.2f), PrimaryCyan.copy(alpha = 0.2f))
                        ),
                        shape = RoundedCornerShape(20.dp)
                    )
                    .padding(horizontal = 14.dp, vertical = 6.dp)
            ) {
                Text(
                    text = badgeText.uppercase(),
                    color = PrimaryCyan,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.5.sp
                )
            }
        }

        Text(
            text = title,
            color = TextWhite,
            fontSize = 32.sp,
            fontWeight = FontWeight.ExtraBold,
            letterSpacing = 1.sp,
            textAlign = TextAlign.Center
        )

        if (subtitle != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = subtitle,
                color = TextMuted,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}
