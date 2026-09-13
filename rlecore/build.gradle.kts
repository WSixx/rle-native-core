import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.library)
}

android {
    namespace = "br.com.lucad.rlecore"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()

        externalNativeBuild {
            cmake {
                cFlags("-std=c23", "-Wall", "-Wextra", "-Werror")
            }
        }
    }

    buildTypes {
        release {
            externalNativeBuild {
                cmake {
                    cFlags("-O3", "-DNDEBUG")
                }
            }
        }
        debug {
            externalNativeBuild {
                cmake {
                    cFlags("-O0", "-g")
                }
            }
        }
    }

    externalNativeBuild {
        cmake {
            path = file("src/main/cpp/CMakeLists.txt")
            version = "4.4.3"
        }
    }

    ndkVersion = libs.versions.ndk.get()

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
}

kotlin {
    jvmToolchain(21)
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_21)
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    testImplementation(libs.junit)
}
