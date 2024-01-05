plugins {
    id("android-base")
}

android {
    namespace = "com.example.data"
}

dependencies {
    implementation(project(":domain"))
    retrofit()
    room()

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}