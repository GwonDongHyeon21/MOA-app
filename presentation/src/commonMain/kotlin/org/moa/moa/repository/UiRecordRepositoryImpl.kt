package org.moa.moa.repository

import com.moa.domain.model.request.AddRecordRequest
import com.moa.domain.model.response.Diary
import com.moa.domain.model.response.RecordItem
import com.moa.domain.usecase.record.RecordUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class UiRecordRepositoryImpl(
    private val recordUseCase: RecordUseCase,
) {

    private val defaultTimeZone = TimeZone.currentSystemDefault()
    private val today = Clock.System.now().toLocalDateTime(defaultTimeZone).date

    private fun String.isToday(
        timeZone: TimeZone = defaultTimeZone,
        date: LocalDate = today,
    ): Boolean = Instant.parse(this).toLocalDateTime(timeZone).date == date

    private val _diaries = MutableStateFlow(emptyList<Diary>())
    val diaries = _diaries.asStateFlow()

    val todayDiary = _diaries
        .map { diaries -> diaries.find { it.date.isToday() } }
        .distinctUntilChanged()

    private val _records = MutableStateFlow(emptyList<RecordItem>())
    val records = _records.asStateFlow()

    val todayRecord = _records
        .map { records -> records.filter { it.date.isToday() } }
        .distinctUntilChanged()

    suspend fun getDiaries() {
        runCatching {
            recordUseCase.getDiaries()
        }.onSuccess { diaries ->
            _diaries.value = diaries.diaries
        }.onFailure {
            throw it
        }
    }

    suspend fun getRecords() {
        runCatching {
            recordUseCase.getRecords()
        }.onSuccess { records ->
            _records.value = records.records
        }.onFailure {
            throw it
        }
    }

    suspend fun addRecord(record: AddRecordRequest) {
        runCatching {
            recordUseCase.addRecord(record)
        }.onSuccess {
//            getDiaries()
            getRecords()
        }.onFailure {
            throw it
        }
    }

//    fun decideEmotion(emotion:String) = recordUseCase.decideEmotion(emotion)
}
