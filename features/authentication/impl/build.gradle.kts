apply {
    from("$rootDir/compose-module.gradle")
}

dependencies {
    "implementation"(project(Modules.core))
    "implementation"(project(Modules.authentication))
    "implementation"(platform(Google.firebase))
    "implementation"(Google.googleAnalytics)
    "implementation"(Google.firebaseAuthentication)
    "implementation"(Google.firebaseCloudStore)
}