package org.moa.moa.presentation.home.home.model

enum class Emotion(val label: String) {
    SMILE("smile"),
    SOSO("soso"),
    SAD("sad"),
    BAD("bad");

    companion object {
        fun String?.toEmotion() = when (this) {
            "smile" -> SMILE
            "soso" -> SOSO
            "sad" -> SAD
            "bad" -> BAD
            else -> null
        }
    }
}

