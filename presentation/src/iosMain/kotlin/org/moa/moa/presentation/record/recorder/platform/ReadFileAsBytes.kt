package org.moa.moa.presentation.record.recorder.platform

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.refTo
import platform.Foundation.NSData
import platform.Foundation.dataWithContentsOfFile
import platform.Foundation.getBytes

@OptIn(ExperimentalForeignApi::class)
actual suspend fun readFileAsBytes(filePath: String): ByteArray? {
    return try {
        val data = NSData.dataWithContentsOfFile(filePath) ?: return null
        ByteArray(data.length.toInt()).apply {
            data.getBytes(refTo(0))
        }
    } catch (e: Exception) {
        null
    }
}