package com.example.ui

import androidx.lifecycle.ViewModel
import com.example.ui.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

enum class NavigationSection(val displayName: String, val icon: String) {
    HERO("Home", "⚡"),
    SERVICES("Services", "🌐"),
    TECHNOLOGIES("Tech", "⚛️"),
    PORTFOLIO("Portfolio", "🚀"),
    ESTIMATE("Calculator", "🧮"),
    ABOUT("About", "💎"),
    CONTACT("Contact", "📧")
}

data class MNDUiState(
    val currentSection: NavigationSection = NavigationSection.HERO,
    val selectedPortfolioCategory: String = "All",
    val selectedProjectDetail: PortfolioProject? = null,
    val selectedServiceDetail: ServiceItem? = null,
    val selectedTechDetail: TechItem? = null,
    
    // Project Estimate Calculator State
    val estimateSelectedServices: Set<String> = setOf("web_dev", "mobile_resp"),
    val estimatePlatform: String = "Web & Mobile",
    val estimateUrgency: String = "Standard (2-4 wks)",
    
    // Contact Form State
    val contactName: String = "",
    val contactEmail: String = "",
    val contactSubject: String = "Project Inquiry",
    val contactMessage: String = "",
    val isSubmittingInquiry: Boolean = false,
    val inquirySuccessReference: String? = null,
    
    // Submitted Inquiries List
    val submittedInquiries: List<ContactInquiry> = emptyList()
)

class MNDViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(MNDUiState())
    val uiState: StateFlow<MNDUiState> = _uiState.asStateFlow()

    fun selectSection(section: NavigationSection) {
        _uiState.update { it.copy(currentSection = section) }
    }

    fun setPortfolioCategory(category: String) {
        _uiState.update { it.copy(selectedPortfolioCategory = category) }
    }

    fun selectProjectDetail(project: PortfolioProject?) {
        _uiState.update { it.copy(selectedProjectDetail = project) }
    }

    fun selectServiceDetail(service: ServiceItem?) {
        _uiState.update { it.copy(selectedServiceDetail = service) }
    }

    fun selectTechDetail(tech: TechItem?) {
        _uiState.update { it.copy(selectedTechDetail = tech) }
    }

    // Estimate Calculator Functions
    fun toggleEstimateService(serviceId: String) {
        _uiState.update { state ->
            val updated = state.estimateSelectedServices.toMutableSet()
            if (updated.contains(serviceId)) {
                if (updated.size > 1) updated.remove(serviceId)
            } else {
                updated.add(serviceId)
            }
            state.copy(estimateSelectedServices = updated)
        }
    }

    fun setEstimatePlatform(platform: String) {
        _uiState.update { it.copy(estimatePlatform = platform) }
    }

    fun setEstimateUrgency(urgency: String) {
        _uiState.update { it.copy(estimateUrgency = urgency) }
    }

    fun calculateTotalEstimate(): Pair<Int, Int> {
        val state = _uiState.value
        var baseCost = 0
        var baseDays = 0

        MNDData.services.forEach { service ->
            if (state.estimateSelectedServices.contains(service.id)) {
                baseCost += when(service.id) {
                    "web_dev" -> 2500
                    "mobile_resp" -> 3000
                    "app_dev" -> 4500
                    "ui_ux" -> 1800
                    "cloud_sol" -> 2200
                    else -> 2000
                }
                baseDays += 10
            }
        }

        // Platform multiplier
        val platformMultiplier = when (state.estimatePlatform) {
            "Web Only" -> 1.0f
            "Mobile Only" -> 1.1f
            "Web & Mobile" -> 1.4f
            "Cross-Platform Suite" -> 1.7f
            else -> 1.0f
        }

        // Urgency multiplier
        val urgencyMultiplier = when (state.estimateUrgency) {
            "Relaxed (6+ wks)" -> 0.9f
            "Standard (2-4 wks)" -> 1.0f
            "Rush (1-2 wks)" -> 1.35f
            else -> 1.0f
        }

        val totalCost = (baseCost * platformMultiplier * urgencyMultiplier).toInt()
        val totalDays = (baseDays * (if (urgencyMultiplier > 1.0f) 0.7f else 1.0f)).toInt().coerceAtLeast(5)

        return Pair(totalCost, totalDays)
    }

    // Contact Form Functions
    fun updateContactName(name: String) { _uiState.update { it.copy(contactName = name) } }
    fun updateContactEmail(email: String) { _uiState.update { it.copy(contactEmail = email) } }
    fun updateContactSubject(subject: String) { _uiState.update { it.copy(contactSubject = subject) } }
    fun updateContactMessage(message: String) { _uiState.update { it.copy(contactMessage = message) } }

    fun submitInquiry() {
        val state = _uiState.value
        if (state.contactName.isBlank() || state.contactEmail.isBlank() || state.contactMessage.isBlank()) return

        _uiState.update { it.copy(isSubmittingInquiry = true) }

        val refId = "MND-" + (100000..999999).random()
        val newInquiry = ContactInquiry(
            name = state.contactName,
            email = state.contactEmail,
            subject = state.contactSubject.ifBlank { "General Inquiry" },
            message = state.contactMessage
        )

        _uiState.update {
            it.copy(
                isSubmittingInquiry = false,
                inquirySuccessReference = refId,
                submittedInquiries = listOf(newInquiry) + it.submittedInquiries,
                contactName = "",
                contactEmail = "",
                contactSubject = "Project Inquiry",
                contactMessage = ""
            )
        }
    }

    fun dismissSuccessDialog() {
        _uiState.update { it.copy(inquirySuccessReference = null) }
    }
}
