package org.moa.moa.presentation.record.recorder.platform

expect suspend fun readFileAsBytes(filePath: String): ByteArray?