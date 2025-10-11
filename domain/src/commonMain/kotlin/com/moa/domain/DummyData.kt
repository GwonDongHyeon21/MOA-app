package com.moa.domain

import com.moa.domain.model.RecordResponse
import com.moa.domain.model.RecordByDateResponse

object DummyData {
    val sampleRecords = listOf(
        RecordByDateResponse(
            date = "2025-10-12",
            content = "주말이라 하루 종일 카페에서 공부했다 ☕️",
            imageUrl = listOf(
                "https://picsum.photos/400/400",
                "https://picsum.photos/400/800",
                "https://picsum.photos/800/400",
                "https://picsum.photos/600/400",
                "https://picsum.photos/400/600"
            ),
            records = listOf(
                RecordResponse(
                    date = "2025-10-11T09:30:00",
                    content = "아침에 느긋하게 카페 도착 ☀️",
                    imageUrl = "https://picsum.photos/400/400",
                ),
                RecordResponse(
                    date = "2025-10-11T14:00:00",
                    content = "점심 이후 집중 잘 됐다!",
                    imageUrl = "https://picsum.photos/400/400",
                ),
                RecordResponse(
                    date = "2025-10-11T20:00:00",
                    content = "오늘 하루 만족 😊",
                    imageUrl = null,
                )
            ),
            emotion = "soso"
        ),
        RecordByDateResponse(
            date = "2025-10-10",
            content = "팀 프로젝트 회의로 하루 종일 정신없던 날 💻",
            imageUrl = listOf("https://picsum.photos/400/400"),
            records = listOf(
                RecordResponse(
                    date = "2025-10-10T15:00:00",
                    content = "회의 중 새로운 아이디어 나옴!",
                    imageUrl = null,
                )
            ),
            emotion = "bad"
        ),
        RecordByDateResponse(
            date = "2025-10-09",
            content = "오늘은 쉬는 날. 아무 일도 안 했다 😴",
            imageUrl = null,
            records = emptyList(),
            emotion = "smile"
        )
    )
}