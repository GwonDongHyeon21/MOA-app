package org.moa.moa.util

import org.moa.moa.presentation.home.model.Emotion
import org.moa.moa.presentation.record.model.Record

object DummyData {
    val sampleRecords = listOf(
        Record("2025-10-01", "새 프로젝트 시작해서 설렘 😊", "", Emotion.fromLabel("smile")),
        Record("2025-10-05", "시험 공부하느라 피곤하다", "", Emotion.fromLabel("sad")),
        Record(
            "2025-10-09",
            "날씨가 너무 좋아서 산책함",
            "https://picsum.photos/400/400",
            Emotion.fromLabel("smile")
        ),
        Record(
            "2025-10-11",
            "조별과제 스트레스 폭발",
            "https://picsum.photos/400/400",
            Emotion.fromLabel("bad")
        ),
        Record("2025-10-13", "점심으로 먹은 국밥이 진짜 맛있었다", "", Emotion.fromLabel("soso")),
        Record("2025-10-15", "친구랑 오랜만에 영화 봄 🎬", "", Emotion.fromLabel("smile")),
        Record(
            "2025-10-17",
            "하루 종일 비와서 우울했음",
            "https://picsum.photos/400/400",
            Emotion.fromLabel("sad")
        ),
        Record(
            "2025-10-20",
            "시험 끝!! 이제 쉰다 🥳",
            "https://picsum.photos/400/400",
            Emotion.fromLabel("smile")
        ),
        Record("2025-10-22", "버스 놓쳐서 지각함...", "", Emotion.fromLabel("bad")),
        Record(
            "2025-10-25",
            "새로운 노래 들으면서 기분 전환  새로운 노래 들으면서 기분 전환  새로운 노래 들으면서 기분 전환  새로운 노래 들으면서 기분 전환  새로운 노래 들으면서 기분 전환  새로운 노래 들으면서 기분 전환  새로운 노래 들으면서 기분 전환  새로운 노래 들으면서 기분 전환  새로운 노래 들으면서 기분 전환  새로운 노래 들으면서 기분 전환  새로운 노래 들으면서 기분 전환  새로운 노래 들으면서 기분 전환  새로운 노래 들으면서 기분 전환  새로운 노래 들으면서 기분 전환" +
                    "\n\n새로운 노래 들으면서 기분 전환  새로운 노래 들으면서 기분 전환  새로운 노래 들으면서 기분 전환  새로운 노래 들으면서 기분 전환  새로운 노래 들으면서 기분 전환  새로운 노래 들으면서 기분 전환",
            "https://picsum.photos/400/400",
            Emotion.fromLabel("soso")
        )
    )
}