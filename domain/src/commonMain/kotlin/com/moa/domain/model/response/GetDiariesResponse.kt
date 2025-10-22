package com.moa.domain.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetDiariesResponse(
    @SerialName("count") val count: Int,
    @SerialName("diaries") val diaries: List<Diary>,
)

@Serializable
data class Diary(
    @SerialName("작성날짜") val date: String,
    @SerialName("내용") val content: String,
    @SerialName("기록리스트") val records: List<DiaryRecord>,
    @SerialName("이미지리스트") val images: List<Image>?,
    @SerialName("emotion") val emotion: String?,
    @SerialName("meta") val meta: Meta,
)

@Serializable
data class DiaryRecord(
    @SerialName("작성날짜") val date: String,
    @SerialName("타입") val type: String,
    @SerialName("내용") val content: String?,
    @SerialName("이미지") val image: String?,
    @SerialName("오디오") val audio: String?,
)

@Serializable
data class Image(
    @SerialName("url") val url: String,
    @SerialName("createdAt") val date: String,
)

@Serializable
data class Meta(
    @SerialName("id") val id: String,
    @SerialName("persona") val persona: Int,
    @SerialName("createdAt") val date: String,
    @SerialName("updatedAt") val updatedAt: String,
)