package org.moa.moa.usecaseimpl

import com.moa.domain.model.request.AddRecordRequest
import com.moa.domain.model.request.CreateDiaryRequest
import com.moa.domain.model.request.UpdateDiaryRequest
import com.moa.domain.model.response.Diary
import com.moa.domain.model.response.RecordItem
import com.moa.domain.usecase.record.RecordUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class UiRecordRepositoryImpl(
    private val recordUseCase: RecordUseCase,
) {
    private val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

    private val _diaries = MutableStateFlow(emptyList<Diary>())
    val diaries = _diaries.asStateFlow()

    val todayDiary = _diaries
        .map { diaries -> diaries.find { it.date == today.date.toString() } }

    private val _records = MutableStateFlow(emptyList<RecordItem>())
    val records = _records.asStateFlow()

    val todayRecords = _records
        .map { records -> records.filter { it.dataToLocalDateTime.date == today.date } }

    suspend fun getDiaries() {
        runCatching {
            recordUseCase.getDiaries()
        }.onSuccess { response ->
            _diaries.value = response.diaries
        }.onFailure {
            throw it
        }
    }

    suspend fun createDiary(diary: CreateDiaryRequest) {
        runCatching {
            recordUseCase.createDiary(diary)
        }.onSuccess { response ->
            _diaries.value += response.diary
        }.onFailure {
            throw it
        }
    }

    suspend fun updateDiary(diary: UpdateDiaryRequest) {
        runCatching {
            recordUseCase.updateDiary(diary)
        }.onSuccess {
            getDiaries()
        }.onFailure {

        }
    }

    suspend fun getRecords() {
        runCatching {
            recordUseCase.getRecords()
        }.onSuccess { response ->
            _records.value = response.records
        }.onFailure {
            throw it
        }
    }

    suspend fun addRecord(record: AddRecordRequest) {
        runCatching {
            recordUseCase.addRecord(record)
        }.onSuccess { response ->
            _records.value += response.record
        }.onFailure {
            throw it
        }
    }
}
