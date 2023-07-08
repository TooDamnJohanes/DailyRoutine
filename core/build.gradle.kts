apply {
    from("$rootDir/compose-module.gradle")
}

dependencies {
    "kapt"(Room.roomCompiler)
    "implementation"(Room.roomKtx)
    "implementation"(Room.roomRuntime)
}