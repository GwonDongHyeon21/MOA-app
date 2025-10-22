package org.moa.moa.presentation.record.recorder.platform

import java.io.File

actual suspend fun readFileAsBytes(filePath: String): ByteArray? {
    return try {
        File(filePath).readBytes()
    } catch (e: Exception) {
        null
    }
}