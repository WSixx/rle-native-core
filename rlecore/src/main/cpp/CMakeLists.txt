cmake_minimum_required(VERSION 3.22.1)

# Activate pure C compiler (disables C++ runtime overhead)
project("rlecore" C)

set(CMAKE_C_STANDARD 23)
set(CMAKE_C_STANDARD_REQUIRED ON)

add_library(${CMAKE_PROJECT_NAME} SHARED
        rlecore.c)

# Enable dead-code stripping at link time
target_link_options(${CMAKE_PROJECT_NAME} PRIVATE "-Wl,--gc-sections")

target_link_libraries(${CMAKE_PROJECT_NAME}
        android
        log)
