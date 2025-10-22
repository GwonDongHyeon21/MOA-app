package com.moa.domain

import com.moa.domain.model.response.RecordResponse
import com.moa.domain.model.response.RecordByDateResponse
import com.moa.domain.model.response.TodoItemResponse

object DummyData {
    val sampleRecords = listOf(
        RecordByDateResponse(
            date = "2025-10-15",
            content = "주말이라 하루 종일 카페에서 공부했다 ☕️  주말이라 하루 종일 카페에서 공부했다 ☕\uFE0F   주말이라 하루 종일 카페에서 공부했다 ☕\uFE0F  주말이라 하루 종일 카페에서 공부했다 ☕\uFE0F  주말이라 하루 종일 카페에서 공부했다 ☕\uFE0F         주말이라 하루 종일 카페에서 공부했다 ☕\uFE0F  주말이라 하루 종일 카페에서 공부했다 ☕\uFE0F \n\n 주말이라 하루 종일 카페에서 공부했다 ☕\uFE0F  주말이라 하루 종일 카페에서 공부했다 ☕\uFE0F  주말이라 하루 종일 카페에서 공부했다 ☕\uFE0F  주말이라 하루 종일 카페에서 공부했다 ☕\uFE0F",
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
                    content = "1번 테스트 기록, 1번 테스트 기록, 1번 테스트 기록, 1번 테스트 기록, 1번 테스트 기록, 1번 테스트 기록, 1번 테스트 기록, 1번 테스트 기록, 1번 테스트 기록, 1번 테스트 기록, 1번 테스트 기록, 1번 테스트 기록, 1번 테스트 기록, 1번 테스트 기록, 1번 테스트 기록, 1번 테스트 기록, 1번 테스트 기록, 1번 테스트 기록, 1번 테스트 기록, 1번 테스트 기록, 1번 테스트 기록, 1번 테스트 기록, 1번 테스트 기록, 1번 테스트 기록, 1번 테스트 기록, 1번 테스트 기록, 1번 테스트 기록, 1번 테스트 기록, 1번 테스트 기록, 1번 테스트 기록, 1번 테스트 기록, 1번 테스트 기록, 1번 테스트 기록, 1번 테스트 기록, 1번 테스트 기록, 1번 테스트 기록",
                    imageUrl = "https://picsum.photos/400/400",
                ),
                RecordResponse(
                    date = "2025-10-11T14:00:00",
                    content = "2번 테스트 기록",
                    imageUrl = "https://picsum.photos/400/400",
                ),
                RecordResponse(
                    date = "2025-10-11T21:00:00",
                    content = "3번 테스트 기록",
                    imageUrl = null,
                ),
                RecordResponse(
                    date = "2025-10-11T19:00:00",
                    content = "4번 테스트 기록",
                    imageUrl = null,
                ),
                RecordResponse(
                    date = "2025-10-11T20:00:00",
                    content = "5번 테스트 기록",
                    imageUrl = null,
                )
            ),
            emotion = "soso"
        ),
        RecordByDateResponse(
            date = "2025-10-14",
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

    val todoExamples = listOf(
        TodoItemResponse(
            id = "a1f4b2d7-34c1-4ef0-bd85-9e2b42a7f010",
            content = "CSE304 알고리즘 중간고사 복습하기",
            date = "2025-10-07",
            done = false,
            createdAt = "2025-10-06T22:10:00Z",
            updatedAt = "2025-10-06T22:10:00Z"
        ),
        TodoItemResponse(
            id = "f8cc0d94-17d3-4b76-a401-82dc925ef3e9",
            content = "헬스장 하체 운동 (스쿼트, 런지)",
            date = "2025-10-09",
            done = true,
            createdAt = "2025-10-08T13:45:00Z",
            updatedAt = "2025-10-09T19:30:00Z"
        ),
        TodoItemResponse(
            id = "b9e5c4a1-907f-493d-87a5-431cdd1d6a23",
            content = "Deep Compression VGG16 실험 보고서 제출",
            date = "2025-10-12",
            done = false,
            createdAt = "2025-10-01T08:00:00Z",
            updatedAt = "2025-10-12T02:30:00Z"
        ),
        TodoItemResponse(
            id = "9e2fdad7-c95f-47d7-845b-8f311993cb21",
            content = "친구 생일 선물 사기 🎁",
            date = "2025-10-15",
            done = true,
            createdAt = "2025-10-10T09:00:00Z",
            updatedAt = "2025-10-15T14:20:00Z"
        ),
        TodoItemResponse(
            id = "efcb12a2-762f-4da4-b3a4-fbf5c86c24e1",
            content = "팀 프로젝트 10월 회의 (리뷰 & 플래닝)",
            date = "2025-10-30",
            done = false,
            createdAt = "2025-10-25T16:00:00Z",
            updatedAt = "2025-10-30T09:30:00Z"
        )
    )
}