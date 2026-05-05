package com.example.miprimeraappmultplatform

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform