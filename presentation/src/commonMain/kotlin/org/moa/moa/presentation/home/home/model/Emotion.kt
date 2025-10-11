package org.moa.moa.presentation.home.home.model

import com.moa.domain.model.RecordByDateResponse
import org.moa.moa.presentation.record.model.Record

enum class Emotion(val label: String) {
    SMILE("smile"),
    SOSO("soso"),
    SAD("sad"),
    BAD("bad");

    companion object {
        fun stringToEmotion(record: RecordByDateResponse): Record {
            return Record(
                date = record.date,
                content = record.content,
                imageUrl = record.imageUrl,
                emotion = when (record.emotion) {
                    "smile" -> SMILE
                    "soso" -> SOSO
                    "sad" -> SAD
                    "bad" -> BAD
                    else -> null
                }
            )
        }
    }
}