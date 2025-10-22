package org.moa.moa.repository

import com.moa.domain.model.request.RecordRequest
import com.moa.domain.usecase.record.RecordUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.moa.moa.presentation.home.home.model.Emotion
import org.moa.moa.presentation.record.model.Record

class UiRecordRepositoryImpl(
    private val recordUseCase: RecordUseCase,
) {

    private val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date

    private val _records = MutableStateFlow(emptyList<Record>())
    val records = _records.asStateFlow()

    val todayRecord = _records
        .map { records -> records.find { it.date == today.toString() } }
        .distinctUntilChanged()

    suspend fun getRecords(date: String) {
        runCatching {
            recordUseCase.getRecords(date)
        }.onSuccess { records ->
            _records.value = records.map { Emotion.stringToEmotion(it) }
        }.onFailure {
            throw it
        }
    }

    suspend fun addRecord(record: RecordRequest) {
        runCatching {
            recordUseCase.addRecord(record)
        }.onSuccess {
            getRecords(today.toString())
        }.onFailure {
            throw it
        }
    }
//    fun decideEmotion(emotion:String) = recordUseCase.decideEmotion(emotion)
}
