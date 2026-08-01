package com.example.ui.model

object MNDData {

    val services = listOf(
        ServiceItem(
            id = "web_dev",
            icon = "🌐",
            title = "Website Design & Dev",
            description = "Custom websites with premium UX, cinematic visuals, and ultra-fast performance.",
            features = listOf("Custom 3D & Canvas Animations", "SEO Optimization & Core Web Vitals", "CMS Integration & Headless Architecture"),
            averageTimeline = "2-4 Weeks",
            startingPrice = "$2,500"
        ),
        ServiceItem(
            id = "mobile_resp",
            icon = "📱",
            title = "Mobile Responsive & Apps",
            description = "Flawless experiences across all mobile devices, foldables, and desktop displays.",
            features = listOf("Native Android & iOS Apps", "Responsive Touch Interfaces", "Offline-first Sync & Caching"),
            averageTimeline = "3-5 Weeks",
            startingPrice = "$3,000"
        ),
        ServiceItem(
            id = "app_dev",
            icon = "💻",
            title = "Application Development",
            description = "Web apps, real-time analytics dashboards, and interactive cloud platforms.",
            features = listOf("Real-time Data Visualizations", "Secure Role-based Access", "API & Webhook Integrations"),
            averageTimeline = "4-8 Weeks",
            startingPrice = "$4,500"
        ),
        ServiceItem(
            id = "ui_ux",
            icon = "🎨",
            title = "UI/UX Design",
            description = "Human-centered design systems with futuristic glassmorphic aesthetics.",
            features = listOf("Figma Interactive Prototypes", "Custom Iconography & Typography", "Accessibility & Usability Audits"),
            averageTimeline = "1-3 Weeks",
            startingPrice = "$1,800"
        ),
        ServiceItem(
            id = "cloud_sol",
            icon = "☁️",
            title = "Cloud Solutions",
            description = "Scalable, secure, and high-performance cloud infrastructure and microservices.",
            features = listOf("AWS & GCP Serverless Setup", "CI/CD Deployment Pipelines", "Auto-scaling & Security Hardening"),
            averageTimeline = "2-4 Weeks",
            startingPrice = "$2,200"
        )
    )

    val technologies = listOf(
        TechItem("react", "React", "⚛️", "Frontend", "Component-driven UI library for web applications"),
        TechItem("ts", "TypeScript", "🟦", "Language", "Strongly typed JavaScript superset for scalable apps"),
        TechItem("node", "Node.js", "🟢", "Backend", "Event-driven asynchronous JavaScript runtime"),
        TechItem("python", "Python", "🐍", "Backend/AI", "Versatile language for AI models, analytics & APIs"),
        TechItem("aws", "AWS", "☁️", "Cloud", "Comprehensive cloud platform for serverless infrastructure"),
        TechItem("figma", "Figma", "🎨", "Design", "Industry standard collaborative vector design software"),
        TechItem("gsap", "GSAP", "🧩", "Animation", "High-performance JavaScript animation engine"),
        TechItem("three", "Three.js", "🌌", "3D Web", "WebGL 3D graphics rendering engine for browsers"),
        TechItem("kotlin", "Kotlin", "💜", "Mobile", "Modern, expressive language for native Android apps"),
        TechItem("compose", "Compose", "⚡", "Mobile", "Declarative UI framework for modern Android apps")
    )

    val portfolio = listOf(
        PortfolioProject(
            id = "nebula_dashboard",
            title = "Nebula Dashboard",
            category = "Analytics Platform",
            tagline = "Real-time analytics platform with immersive data visualization.",
            description = "A futuristic cloud monitoring dashboard built for high-throughput streaming telemetry data, featuring live node topographies and AI predictive load forecasting.",
            techStack = listOf("React", "TypeScript", "Three.js", "AWS"),
            clientName = "Nebula Cloud Inc.",
            year = "2024",
            keyResults = listOf("99.99% Uptime Monitored", "40ms Real-time Latency", "50k+ Active Nodes"),
            primaryColorHex = 0xFF2D8CFF
        ),
        PortfolioProject(
            id = "lunar_ecommerce",
            title = "Lunar E-Commerce",
            category = "3D Commerce Store",
            tagline = "Premium online store with interactive 3D product previews.",
            description = "An immersive luxury e-commerce experience allowing buyers to inspect products in full 3D, customize materials in real-time, and execute instant biometrics checkout.",
            techStack = listOf("React", "Three.js", "Node.js", "GSAP"),
            clientName = "Lunar Atelier",
            year = "2024",
            keyResults = listOf("3.2x Conversion Boost", "45% Lower Returns", "120k Monthly Visits"),
            primaryColorHex = 0xFF6EE7FF
        ),
        PortfolioProject(
            id = "orbit_cms",
            title = "Orbit CMS",
            category = "Headless CMS",
            tagline = "Headless CMS platform with AI-powered content creation.",
            description = "A headless content engine built for global media teams, featuring AI automatic translation, multi-channel publishing, and visual drag-and-drop layout builders.",
            techStack = listOf("TypeScript", "Python", "AWS", "Figma"),
            clientName = "Orbit Media Group",
            year = "2023",
            keyResults = listOf("10x Faster Content Deployment", "Auto-Translate 18 Languages", "Zero Downtime"),
            primaryColorHex = 0xFF9D4EDD
        ),
        PortfolioProject(
            id = "aether_mobile",
            title = "Aether Mobile",
            category = "Fintech Mobile App",
            tagline = "Next-generation fintech application with biometrics and smart analytics.",
            description = "A native Android mobile wallet with real-time spending insights, biometric security, micro-investments, and seamless cross-border transfers.",
            techStack = listOf("Kotlin", "Compose", "Node.js", "AWS"),
            clientName = "Aether Capital",
            year = "2024",
            keyResults = listOf("100k+ Play Store Downloads", "4.9 Rating", "Bank-grade Encryption"),
            primaryColorHex = 0xFF00F5D4
        )
    )

    val stats = listOf(
        AgencyStat("60+", "Projects Delivered", "🚀"),
        AgencyStat("24/7", "Global Support", "⚡"),
        AgencyStat("100%", "Client Satisfaction", "💎"),
        AgencyStat("4.9★", "Average Rating", "⭐")
    )
}
