package com.example.ui.model

data class ServiceItem(
    val id: String,
    val icon: String,
    val title: String,
    val description: String,
    val features: List<String>,
    val averageTimeline: String,
    val startingPrice: String
)

data class TechItem(
    val id: String,
    val name: String,
    val iconEmoji: String,
    val category: String,
    val description: String
)

data class PortfolioProject(
    val id: String,
    val title: String,
    val category: String,
    val tagline: String,
    val description: String,
    val techStack: List<String>,
    val clientName: String,
    val year: String,
    val keyResults: List<String>,
    val primaryColorHex: Long
)

data class AgencyStat(
    val value: String,
    val label: String,
    val icon: String
)

data class ContactInquiry(
    val id: String = java.util.UUID.randomUUID().toString(),
    val name: String,
    val email: String,
    val subject: String,
    val message: String,
    val timestamp: Long = System.currentTimeMillis(),
    val status: String = "Pending Review"
)

data class ProjectEstimate(
    val selectedServices: Set<String> = emptySet(),
    val platformType: String = "Web & Mobile",
    val timelineSpeed: String = "Standard",
    val estimatedDays: Int = 14,
    val estimatedCostUsd: Int = 3500
)
