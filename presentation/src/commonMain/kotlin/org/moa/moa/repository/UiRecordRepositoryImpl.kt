package org.moa.moa.repository

import com.moa.domain.model.request.AddAudioRecordRequest
import com.moa.domain.model.request.AddTextImageRecordRequest
import com.moa.domain.model.response.Diary
import com.moa.domain.usecase.record.RecordUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class UiRecordRepositoryImpl(
    private val recordUseCase: RecordUseCase,
) {

    private val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date

    private val _diaries = MutableStateFlow(emptyList<Diary>())
    val diaries = _diaries.asStateFlow()

    val todayDiary = _diaries
        .map { diaries -> diaries.find { it.date == today.toString() } }
        .distinctUntilChanged()

//    private val _records = MutableStateFlow(emptyList<RecordItem>())
//    val records = _records.asStateFlow()
//
//    val todayRecord = _records
//        .map { records -> records.filter { it.date == today.toString() } }
//        .distinctUntilChanged()

    suspend fun getDiaries() {
        runCatching {
            recordUseCase.getDiaries()
        }.onSuccess { diaries ->
            _diaries.value = diaries.diaries
        }.onFailure {
            throw it
        }
    }

//    suspend fun getRecords(date: String) {
//        runCatching {
//            recordUseCase.getRecords(date)
//        }.onSuccess { records ->
//            _records.value = records.records
//        }.onFailure {
//            throw it
//        }
//    }

    suspend fun addTextImageRecord(record: AddTextImageRecordRequest) {
        runCatching {
            recordUseCase.addTextImageRecord(record)
        }.onSuccess {
            getDiaries()
//            getRecords(today.toString())
        }.onFailure {
            throw it
        }
    }

    suspend fun addAudioRecord(record: AddAudioRecordRequest) {
        runCatching {
            recordUseCase.addAudioRecord(record)
        }.onSuccess {
            getDiaries()
//            getRecords(today.toString())
        }.onFailure {
            throw it
        }
    }

//    fun decideEmotion(emotion:String) = recordUseCase.decideEmotion(emotion)
}
