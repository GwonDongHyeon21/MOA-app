package org.moa.moa.presentation.home.home.model

enum class Emotion(val label: String) {
    SMILE("smile"),
    SOSO("soso"),
    SAD("sad"),
    BAD("bad");

    companion object {
        fun stringToEmotion(emotion: String?): Emotion? {
            return when (emotion) {
                "smile" -> SMILE
                "soso" -> SOSO
                "sad" -> SAD
                "bad" -> BAD
                else -> null
            }
        }
    }
}