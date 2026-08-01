package com.example.ui.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.MNDUiState
import com.example.ui.MNDViewModel
import com.example.ui.NavigationSection
import com.example.ui.components.GalaxyBackground
import com.example.ui.components.GlassCard
import com.example.ui.components.SectionHeader
import com.example.ui.model.*
import com.example.ui.theme.*

@Composable
fun MNDMainContent(
    viewModel: MNDViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(DarkBackground)
    ) {
        // 1. Animated Galaxy Starfield Canvas
        GalaxyBackground()

        // 2. Main Content Scroll View
        Column(modifier = Modifier.fillMaxSize()) {
            // Header Bar
            MNDTopHeader(
                activeSection = uiState.currentSection,
                onSectionSelected = { viewModel.selectSection(it) }
            )

            // Scrollable Sections
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                contentPadding = PaddingValues(top = 12.dp, bottom = 90.dp),
                verticalArrangement = Arrangement.spacedBy(32.dp)
            ) {
                when (uiState.currentSection) {
                    NavigationSection.HERO -> {
                        item { HeroSection(onViewWorkClick = { viewModel.selectSection(NavigationSection.PORTFOLIO) }, onLetTalkClick = { viewModel.selectSection(NavigationSection.CONTACT) }) }
                        item { ServicesSection(onServiceClick = { viewModel.selectServiceDetail(it) }) }
                        item { TechStackSection(selectedTech = uiState.selectedTechDetail, onTechClick = { viewModel.selectTechDetail(it) }) }
                        item { PortfolioSection(selectedCategory = uiState.selectedPortfolioCategory, onCategorySelect = { viewModel.setPortfolioCategory(it) }, onProjectClick = { viewModel.selectProjectDetail(it) }) }
                        item { CalculatorSection(uiState = uiState, viewModel = viewModel) }
                        item { AboutSection() }
                        item { ContactSection(uiState = uiState, viewModel = viewModel, context = context) }
                    }
                    NavigationSection.SERVICES -> {
                        item { ServicesSection(onServiceClick = { viewModel.selectServiceDetail(it) }) }
                        item { CalculatorSection(uiState = uiState, viewModel = viewModel) }
                    }
                    NavigationSection.TECHNOLOGIES -> {
                        item { TechStackSection(selectedTech = uiState.selectedTechDetail, onTechClick = { viewModel.selectTechDetail(it) }) }
                    }
                    NavigationSection.PORTFOLIO -> {
                        item { PortfolioSection(selectedCategory = uiState.selectedPortfolioCategory, onCategorySelect = { viewModel.setPortfolioCategory(it) }, onProjectClick = { viewModel.selectProjectDetail(it) }) }
                    }
                    NavigationSection.ESTIMATE -> {
                        item { CalculatorSection(uiState = uiState, viewModel = viewModel) }
                    }
                    NavigationSection.ABOUT -> {
                        item { AboutSection() }
                    }
                    NavigationSection.CONTACT -> {
                        item { ContactSection(uiState = uiState, viewModel = viewModel, context = context) }
                    }
                }

                // Footer
                item { MNDFooter() }
            }
        }

        // Modals / Dialogs
        uiState.selectedProjectDetail?.let { project ->
            ProjectDetailDialog(
                project = project,
                onDismiss = { viewModel.selectProjectDetail(null) },
                onRequestQuote = {
                    viewModel.selectProjectDetail(null)
                    viewModel.updateContactSubject("Inquiry regarding ${project.title}")
                    viewModel.selectSection(NavigationSection.CONTACT)
                }
            )
        }

        uiState.selectedServiceDetail?.let { service ->
            ServiceDetailDialog(
                service = service,
                onDismiss = { viewModel.selectServiceDetail(null) },
                onRequestQuote = {
                    viewModel.selectServiceDetail(null)
                    viewModel.updateContactSubject("Inquiry: ${service.title}")
                    viewModel.selectSection(NavigationSection.CONTACT)
                }
            )
        }

        uiState.inquirySuccessReference?.let { refId ->
            InquirySuccessDialog(
                referenceId = refId,
                onDismiss = { viewModel.dismissSuccessDialog() }
            )
        }
    }
}

@Composable
private fun MNDTopHeader(
    activeSection: NavigationSection,
    onSectionSelected: (NavigationSection) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(SurfaceDark.copy(alpha = 0.85f))
            .border(width = 0.5.dp, color = GlassBorder)
            .statusBarsPadding()
            .padding(vertical = 10.dp)
    ) {
        // Logo and Brand Name Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Diamond Logo Icon
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(PrimaryBlue, PrimaryCyan)
                            )
                        )
                        .padding(2.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(6.dp))
                            .background(DarkBackground),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "M",
                            color = PrimaryCyan,
                            fontWeight = FontWeight.Black,
                            fontSize = 18.sp
                        )
                    }
                }

                Column {
                    Text(
                        text = "MND",
                        color = TextWhite,
                        fontWeight = FontWeight.Black,
                        fontSize = 20.sp,
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = "CODE. CREATE. ELEVATE.",
                        color = PrimaryCyan,
                        fontWeight = FontWeight.Bold,
                        fontSize = 9.sp,
                        letterSpacing = 1.2.sp
                    )
                }
            }

            // Quick Status Pill
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0x2200F5D4))
                    .border(1.dp, AccentNeonGreen.copy(alpha = 0.4f), RoundedCornerShape(16.dp))
                    .padding(horizontal = 10.dp, vertical = 4.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .clip(CircleShape)
                            .background(AccentNeonGreen)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Available Q3/Q4",
                        color = AccentNeonGreen,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Navigation Bar Chips
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(NavigationSection.entries.toTypedArray()) { section ->
                val isSelected = section == activeSection
                val chipBg = if (isSelected) {
                    Brush.horizontalGradient(listOf(PrimaryBlue, PrimaryCyan))
                } else {
                    Brush.horizontalGradient(listOf(GlassCardBg, GlassCardBg))
                }

                Box(
                    modifier = Modifier
                        .testTag("nav_chip_${section.name.lowercase()}")
                        .clip(RoundedCornerShape(20.dp))
                        .background(chipBg)
                        .border(
                            width = 1.dp,
                            color = if (isSelected) PrimaryCyan else GlassBorder,
                            shape = RoundedCornerShape(20.dp)
                        )
                        .clickable { onSectionSelected(section) }
                        .padding(horizontal = 14.dp, vertical = 6.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = section.icon, fontSize = 12.sp)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = section.displayName,
                            color = if (isSelected) DarkBackground else TextWhite,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun HeroSection(
    onViewWorkClick: () -> Unit,
    onLetTalkClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Large Diamond Hero Logo
        Box(
            modifier = Modifier
                .size(72.dp)
                .clip(RoundedCornerShape(18.dp))
                .background(
                    brush = Brush.linearGradient(listOf(PrimaryBlue, PrimaryCyan, AccentPurple))
                )
                .padding(3.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(15.dp))
                    .background(DarkBackground),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "MND",
                    color = PrimaryCyan,
                    fontWeight = FontWeight.Black,
                    fontSize = 28.sp,
                    letterSpacing = 2.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "MND",
            color = TextWhite,
            fontSize = 54.sp,
            fontWeight = FontWeight.Black,
            letterSpacing = (-1).sp,
            textAlign = TextAlign.Center
        )

        Text(
            text = "Website Design & Development",
            color = TextWhite.copy(alpha = 0.85f),
            fontSize = 18.sp,
            fontWeight = FontWeight.Light,
            letterSpacing = 2.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(30.dp))
                .background(Color(0x1A6EE7FF))
                .border(1.dp, PrimaryCyan.copy(alpha = 0.3f), RoundedCornerShape(30.dp))
                .padding(horizontal = 16.dp, vertical = 6.dp)
        ) {
            Text(
                text = "CODE. CREATE. ELEVATE.",
                color = PrimaryCyan,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 12.sp,
                letterSpacing = 3.sp
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "We merge cinematic aesthetics with high-performance engineering to deliver unforgettable digital experiences for visionary brands.",
            color = TextMuted,
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            lineHeight = 22.sp,
            modifier = Modifier.padding(horizontal = 12.dp)
        )

        Spacer(modifier = Modifier.height(28.dp))

        // CTA Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally)
        ) {
            Button(
                onClick = onViewWorkClick,
                modifier = Modifier
                    .testTag("hero_view_work_btn")
                    .height(48.dp),
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Unspecified)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.horizontalGradient(listOf(PrimaryBlue, PrimaryCyan)),
                            shape = RoundedCornerShape(24.dp)
                        )
                        .padding(horizontal = 20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("View Our Work", color = DarkBackground, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Spacer(modifier = Modifier.width(6.dp))
                        Icon(Icons.Default.ArrowForward, contentDescription = null, tint = DarkBackground, modifier = Modifier.size(16.dp))
                    }
                }
            }

            OutlinedButton(
                onClick = onLetTalkClick,
                modifier = Modifier
                    .testTag("hero_lets_talk_btn")
                    .height(48.dp),
                shape = RoundedCornerShape(24.dp),
                border = ButtonDefaults.outlinedButtonBorder.copy(brush = Brush.horizontalGradient(listOf(GlassBorder, PrimaryCyan))),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = TextWhite)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Outlined.Chat, contentDescription = null, tint = PrimaryCyan, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Let's Talk", color = TextWhite, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                }
            }
        }
    }
}

@Composable
private fun ServicesSection(
    onServiceClick: (ServiceItem) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        SectionHeader(
            badgeText = "What We Build",
            title = "Services",
            subtitle = "Custom digital solutions built with cutting-edge tech and cinematic aesthetics."
        )

        MNDData.services.forEach { service ->
            GlassCard(
                onClick = { onServiceClick(service) },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("service_card_${service.id}"),
                glowColor = PrimaryBlue
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = service.icon, fontSize = 28.sp)
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = service.title,
                                color = TextWhite,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        }

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color(0x226EE7FF))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = service.startingPrice,
                                color = PrimaryCyan,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = service.description,
                        color = TextMuted,
                        fontSize = 13.sp,
                        lineHeight = 18.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "⏱️ Timeline: ${service.averageTimeline}",
                            color = TextWhite.copy(alpha = 0.7f),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("Details", color = PrimaryCyan, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            Icon(Icons.Default.ChevronRight, contentDescription = null, tint = PrimaryCyan, modifier = Modifier.size(16.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TechStackSection(
    selectedTech: TechItem?,
    onTechClick: (TechItem) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        SectionHeader(
            badgeText = "Our Toolchain",
            title = "Technologies",
            subtitle = "Leveraging modern frameworks for maximum performance and security."
        )

        // Grid of Technologies
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            val chunks = MNDData.technologies.chunked(2)
            chunks.forEach { rowItems ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    rowItems.forEach { tech ->
                        val isSelected = selectedTech?.id == tech.id
                        GlassCard(
                            onClick = { onTechClick(tech) },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("tech_item_${tech.id}"),
                            glowColor = if (isSelected) PrimaryCyan else null,
                            borderColor = if (isSelected) PrimaryCyan else GlassBorder
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Text(text = tech.iconEmoji, fontSize = 24.sp)
                                Column {
                                    Text(
                                        text = tech.name,
                                        color = TextWhite,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp
                                    )
                                    Text(
                                        text = tech.category,
                                        color = PrimaryCyan,
                                        fontSize = 11.sp
                                    )
                                }
                            }
                        }
                    }
                    if (rowItems.size == 1) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }

        // Tech Detail Banner when selected
        selectedTech?.let { tech ->
            GlassCard(
                glowColor = AccentPurple,
                borderColor = AccentPurple,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${tech.iconEmoji} ${tech.name} (${tech.category})",
                            color = TextWhite,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                        IconButton(onClick = { onTechClick(tech) }, modifier = Modifier.size(24.dp)) {
                            Icon(Icons.Default.Close, contentDescription = "Close", tint = TextMuted)
                        }
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = tech.description,
                        color = TextMuted,
                        fontSize = 13.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun PortfolioSection(
    selectedCategory: String,
    onCategorySelect: (String) -> Unit,
    onProjectClick: (PortfolioProject) -> Unit
) {
    val categories = listOf("All", "Analytics Platform", "3D Commerce Store", "Headless CMS", "Fintech Mobile App")
    val filteredProjects = if (selectedCategory == "All") {
        MNDData.portfolio
    } else {
        MNDData.portfolio.filter { it.category == selectedCategory }
    }

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        SectionHeader(
            badgeText = "Case Studies",
            title = "Portfolio",
            subtitle = "A selection of projects designed & engineered by MND."
        )

        // Filter Pills
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(vertical = 4.dp)
        ) {
            items(categories) { cat ->
                val isSelected = cat == selectedCategory
                Box(
                    modifier = Modifier
                        .testTag("portfolio_cat_${cat.lowercase().replace(" ", "_")}")
                        .clip(RoundedCornerShape(16.dp))
                        .background(if (isSelected) PrimaryCyan else GlassCardBg)
                        .clickable { onCategorySelect(cat) }
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = cat,
                        color = if (isSelected) DarkBackground else TextWhite,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        fontSize = 12.sp
                    )
                }
            }
        }

        // Project Cards
        filteredProjects.forEach { project ->
            GlassCard(
                onClick = { onProjectClick(project) },
                glowColor = Color(project.primaryColorHex),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("portfolio_card_${project.id}")
            ) {
                Column {
                    // Category Badge & Year
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = project.category.uppercase(),
                            color = Color(project.primaryColorHex),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                        Text(
                            text = project.year,
                            color = TextMuted,
                            fontSize = 11.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = project.title,
                        color = TextWhite,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 18.sp
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = project.tagline,
                        color = TextMuted,
                        fontSize = 13.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Key Stats Pills
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        project.keyResults.take(2).forEach { result ->
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color(0x1AFFFFFF))
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = "✨ $result",
                                    color = TextWhite.copy(alpha = 0.9f),
                                    fontSize = 11.sp
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Tech Stack Chips & Action
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            project.techStack.forEach { tech ->
                                Text(
                                    text = "#$tech",
                                    color = PrimaryCyan.copy(alpha = 0.8f),
                                    fontSize = 11.sp
                                )
                            }
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("View Case Study", color = PrimaryCyan, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            Icon(Icons.Default.ArrowForward, contentDescription = null, tint = PrimaryCyan, modifier = Modifier.size(14.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CalculatorSection(
    uiState: MNDUiState,
    viewModel: MNDViewModel
) {
    val (cost, days) = viewModel.calculateTotalEstimate()

    GlassCard(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("estimate_calculator_card"),
        glowColor = PrimaryCyan
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            SectionHeader(
                badgeText = "Instant Estimator",
                title = "Project Calculator",
                subtitle = "Configure your requirements for an instant cost & timeline estimate."
            )

            // 1. Service Toggles
            Text("Select Required Services:", color = TextWhite, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                MNDData.services.forEach { service ->
                    val isChecked = uiState.estimateSelectedServices.contains(service.id)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (isChecked) PrimaryBlue.copy(alpha = 0.25f) else Color(0x0AFFFFFF))
                            .border(1.dp, if (isChecked) PrimaryCyan else GlassBorder, RoundedCornerShape(12.dp))
                            .clickable { viewModel.toggleEstimateService(service.id) }
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = service.icon, fontSize = 20.sp)
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(text = service.title, color = TextWhite, fontSize = 13.sp, fontWeight = FontWeight.Medium)
                        }
                        Checkbox(
                            checked = isChecked,
                            onCheckedChange = { viewModel.toggleEstimateService(service.id) },
                            colors = CheckboxDefaults.colors(checkedColor = PrimaryCyan, checkmarkColor = DarkBackground)
                        )
                    }
                }
            }

            // 2. Target Platform Selection
            Text("Target Platform:", color = TextWhite, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            val platforms = listOf("Web Only", "Mobile Only", "Web & Mobile", "Cross-Platform Suite")
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(platforms) { platform ->
                    val isSelected = uiState.estimatePlatform == platform
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (isSelected) PrimaryCyan else GlassCardBg)
                            .clickable { viewModel.setEstimatePlatform(platform) }
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = platform,
                            color = if (isSelected) DarkBackground else TextWhite,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            fontSize = 12.sp
                        )
                    }
                }
            }

            // 3. Urgency Selection
            Text("Delivery Speed:", color = TextWhite, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            val urgencies = listOf("Relaxed (6+ wks)", "Standard (2-4 wks)", "Rush (1-2 wks)")
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                urgencies.forEach { urgency ->
                    val isSelected = uiState.estimateUrgency == urgency
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (isSelected) PrimaryBlue else GlassCardBg)
                            .clickable { viewModel.setEstimateUrgency(urgency) }
                            .padding(vertical = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = urgency.split(" ").first(),
                            color = TextWhite,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            fontSize = 11.sp
                        )
                    }
                }
            }

            Divider(color = GlassBorder)

            // Result Summary Card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        brush = Brush.horizontalGradient(listOf(SurfaceDark, SurfaceCard))
                    )
                    .border(1.dp, PrimaryCyan.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("Estimated Cost", color = TextMuted, fontSize = 12.sp)
                        Text(
                            text = "$${String.format("%,d", cost)} USD",
                            color = PrimaryCyan,
                            fontWeight = FontWeight.Black,
                            fontSize = 22.sp
                        )
                    }

                    Column(horizontalAlignment = Alignment.End) {
                        Text("Est. Timeline", color = TextMuted, fontSize = 12.sp)
                        Text(
                            text = "~$days Business Days",
                            color = TextWhite,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                    }
                }
            }

            // CTA Button to Lock in Quote
            Button(
                onClick = {
                    val summaryMsg = "Selected Services: ${uiState.estimateSelectedServices.joinToString()}, Platform: ${uiState.estimatePlatform}, Speed: ${uiState.estimateUrgency}. Estimated Cost: $$cost USD (~$days days)."
                    viewModel.updateContactSubject("Project Quote Request")
                    viewModel.updateContactMessage(summaryMsg)
                    viewModel.selectSection(NavigationSection.CONTACT)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("estimate_lock_quote_btn")
                    .height(46.dp),
                shape = RoundedCornerShape(23.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryCyan)
            ) {
                Text("Lock in This Quote →", color = DarkBackground, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            }
        }
    }
}

@Composable
private fun AboutSection() {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        SectionHeader(
            badgeText = "Our Story",
            title = "About MND",
            subtitle = "Code. Create. Elevate."
        )

        GlassCard(modifier = Modifier.fillMaxWidth()) {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(
                    text = "MND is a future-forward digital agency specializing in premium website design, application development, and cloud-native solutions.",
                    color = TextWhite,
                    fontSize = 14.sp,
                    lineHeight = 22.sp
                )

                Text(
                    text = "Our engineering and design teams operate at the intersection of art and code — pushing boundaries with every pixel and every line of logic.",
                    color = TextMuted,
                    fontSize = 13.sp,
                    lineHeight = 20.sp
                )
            }
        }

        // Stats Grid
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            val chunks = MNDData.stats.chunked(2)
            chunks.forEach { rowStats ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    rowStats.forEach { stat ->
                        GlassCard(
                            modifier = Modifier.weight(1f),
                            glowColor = PrimaryBlue
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(text = stat.icon, fontSize = 24.sp)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = stat.value,
                                    color = PrimaryCyan,
                                    fontWeight = FontWeight.Black,
                                    fontSize = 24.sp
                                )
                                Text(
                                    text = stat.label,
                                    color = TextMuted,
                                    fontSize = 11.sp,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ContactSection(
    uiState: MNDUiState,
    viewModel: MNDViewModel,
    context: Context
) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        SectionHeader(
            badgeText = "Let's Create",
            title = "Get in Touch",
            subtitle = "Ready to elevate your digital presence? Send us a message below."
        )

        // Contact Info Glass Card
        GlassCard(modifier = Modifier.fillMaxWidth()) {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text("Contact Details", color = TextWhite, fontWeight = FontWeight.Bold, fontSize = 15.sp)

                ContactDetailRow(
                    icon = Icons.Outlined.Email,
                    label = "hello@mnd.dev",
                    onClick = {
                        val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:hello@mnd.dev"))
                        runCatching { context.startActivity(intent) }
                    }
                )

                ContactDetailRow(
                    icon = Icons.Outlined.Phone,
                    label = "+1 (800) 555-MND",
                    onClick = {
                        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:+1800555663"))
                        runCatching { context.startActivity(intent) }
                    }
                )

                ContactDetailRow(
                    icon = Icons.Outlined.LocationOn,
                    label = "San Francisco, CA (Mon – Fri, 9AM – 6PM PST)",
                    onClick = null
                )
            }
        }

        // Contact Form Glass Card
        GlassCard(modifier = Modifier.fillMaxWidth(), glowColor = PrimaryCyan) {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text("Send an Inquiry", color = TextWhite, fontWeight = FontWeight.Bold, fontSize = 16.sp)

                OutlinedTextField(
                    value = uiState.contactName,
                    onValueChange = { viewModel.updateContactName(it) },
                    label = { Text("Your Name", color = TextMuted) },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("contact_name_input"),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = TextWhite,
                        unfocusedTextColor = TextWhite,
                        focusedBorderColor = PrimaryCyan,
                        unfocusedBorderColor = GlassBorder
                    )
                )

                OutlinedTextField(
                    value = uiState.contactEmail,
                    onValueChange = { viewModel.updateContactEmail(it) },
                    label = { Text("Your Email", color = TextMuted) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("contact_email_input"),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = TextWhite,
                        unfocusedTextColor = TextWhite,
                        focusedBorderColor = PrimaryCyan,
                        unfocusedBorderColor = GlassBorder
                    )
                )

                OutlinedTextField(
                    value = uiState.contactSubject,
                    onValueChange = { viewModel.updateContactSubject(it) },
                    label = { Text("Subject", color = TextMuted) },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("contact_subject_input"),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = TextWhite,
                        unfocusedTextColor = TextWhite,
                        focusedBorderColor = PrimaryCyan,
                        unfocusedBorderColor = GlassBorder
                    )
                )

                OutlinedTextField(
                    value = uiState.contactMessage,
                    onValueChange = { viewModel.updateContactMessage(it) },
                    label = { Text("Tell us about your project...", color = TextMuted) },
                    minLines = 3,
                    maxLines = 5,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("contact_message_input"),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = TextWhite,
                        unfocusedTextColor = TextWhite,
                        focusedBorderColor = PrimaryCyan,
                        unfocusedBorderColor = GlassBorder
                    )
                )

                Spacer(modifier = Modifier.height(4.dp))

                Button(
                    onClick = { viewModel.submitInquiry() },
                    enabled = !uiState.isSubmittingInquiry && uiState.contactName.isNotBlank() && uiState.contactEmail.isNotBlank() && uiState.contactMessage.isNotBlank(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("contact_submit_btn")
                        .height(48.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryCyan)
                ) {
                    if (uiState.isSubmittingInquiry) {
                        CircularProgressIndicator(modifier = Modifier.size(20.dp), color = DarkBackground)
                    } else {
                        Text("Send Message", color = DarkBackground, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    }
                }
            }
        }

        // Sent Inquiries History
        if (uiState.submittedInquiries.isNotEmpty()) {
            GlassCard(modifier = Modifier.fillMaxWidth()) {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("Sent Inquiries Log", color = TextWhite, fontWeight = FontWeight.Bold, fontSize = 14.sp)

                    uiState.submittedInquiries.forEach { inquiry ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color(0x0AFFFFFF))
                                .padding(10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(inquiry.subject, color = TextWhite, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                Text("${inquiry.name} (${inquiry.email})", color = TextMuted, fontSize = 11.sp)
                            }
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(Color(0x2200F5D4))
                                    .padding(horizontal = 8.dp, vertical = 2.dp)
                            ) {
                                Text(inquiry.status, color = AccentNeonGreen, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ContactDetailRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    onClick: (() -> Unit)?
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .then(if (onClick != null) Modifier.clickable { onClick() } else Modifier),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = PrimaryCyan, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(12.dp))
        Text(text = label, color = TextWhite.copy(alpha = 0.9f), fontSize = 13.sp)
    }
}

@Composable
private fun ProjectDetailDialog(
    project: PortfolioProject,
    onDismiss: () -> Unit,
    onRequestQuote: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = SurfaceDark,
        title = {
            Column {
                Text(
                    text = project.category.uppercase(),
                    color = Color(project.primaryColorHex),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = project.title,
                    color = TextWhite,
                    fontWeight = FontWeight.Black,
                    fontSize = 20.sp
                )
            }
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(project.description, color = TextMuted, fontSize = 13.sp, lineHeight = 19.sp)

                Divider(color = GlassBorder)

                Text("Key Results Delivered:", color = TextWhite, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                project.keyResults.forEach { res ->
                    Text("• $res", color = PrimaryCyan, fontSize = 12.sp)
                }

                Spacer(modifier = Modifier.height(4.dp))
                Text("Tech Stack: ${project.techStack.joinToString(", ")}", color = TextMuted, fontSize = 12.sp)
                Text("Client: ${project.clientName} (${project.year})", color = TextMuted, fontSize = 12.sp)
            }
        },
        confirmButton = {
            Button(
                onClick = onRequestQuote,
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryCyan)
            ) {
                Text("Inquire About Similar Project", color = DarkBackground, fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Close", color = TextMuted)
            }
        }
    )
}

@Composable
private fun ServiceDetailDialog(
    service: ServiceItem,
    onDismiss: () -> Unit,
    onRequestQuote: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = SurfaceDark,
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(service.icon, fontSize = 24.sp)
                Spacer(modifier = Modifier.width(10.dp))
                Text(service.title, color = TextWhite, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            }
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(service.description, color = TextMuted, fontSize = 13.sp)

                Text("Deliverables Included:", color = TextWhite, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                service.features.forEach { feat ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("✔ ", color = AccentNeonGreen, fontSize = 12.sp)
                        Text(feat, color = TextWhite, fontSize = 12.sp)
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Est. Timeline: ${service.averageTimeline}", color = PrimaryCyan, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    Text("From ${service.startingPrice}", color = AccentNeonGreen, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }
        },
        confirmButton = {
            Button(
                onClick = onRequestQuote,
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryCyan)
            ) {
                Text("Request Quote", color = DarkBackground, fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Close", color = TextMuted)
            }
        }
    )
}

@Composable
private fun InquirySuccessDialog(
    referenceId: String,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = SurfaceDark,
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("🎉 Message Received!", color = TextWhite, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            }
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "Thank you for reaching out to MND! Our team will review your inquiry and respond within 24 hours.",
                    color = TextMuted,
                    fontSize = 13.sp
                )
                Spacer(modifier = Modifier.height(6.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0x1A6EE7FF))
                        .padding(10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Ref ID: $referenceId",
                        color = PrimaryCyan,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryCyan)
            ) {
                Text("Done", color = DarkBackground, fontWeight = FontWeight.Bold)
            }
        }
    )
}

@Composable
private fun MNDFooter() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp, bottom = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "MND",
            color = TextWhite.copy(alpha = 0.6f),
            fontWeight = FontWeight.Black,
            fontSize = 22.sp,
            letterSpacing = 2.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "© MND DEVELOPMENT • 2K-22 TO PRESENT",
            color = TextMuted,
            fontSize = 10.sp,
            letterSpacing = 1.5.sp
        )
        Text(
            text = "CODE. CREATE. ELEVATE.",
            color = PrimaryCyan.copy(alpha = 0.7f),
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 2.sp,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}
