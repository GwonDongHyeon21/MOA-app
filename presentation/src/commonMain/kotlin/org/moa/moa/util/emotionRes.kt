package org.moa.moa.util

import moa.presentation.generated.resources.Res
import moa.presentation.generated.resources.emotion_bad
import moa.presentation.generated.resources.emotion_sad
import moa.presentation.generated.resources.emotion_smile
import moa.presentation.generated.resources.emotion_soso
import org.moa.moa.presentation.home.model.Emotion

fun emotionRes(emotion: Emotion) = when (emotion) {
    Emotion.SMILE -> Res.drawable.emotion_smile
    Emotion.SOSO -> Res.drawable.emotion_soso
    Emotion.SAD -> Res.drawable.emotion_sad
    Emotion.BAD -> Res.drawable.emotion_bad
}