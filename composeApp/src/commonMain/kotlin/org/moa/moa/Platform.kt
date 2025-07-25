package org.moa.moa

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform