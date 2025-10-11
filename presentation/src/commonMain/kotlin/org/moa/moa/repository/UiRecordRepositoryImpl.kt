package org.moa.moa.repository

import com.moa.domain.model.RecordRequest
import com.moa.domain.usecase.record.RecordUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.moa.moa.presentation.home.model.Emotion
import org.moa.moa.presentation.record.model.Record

class UiRecordRepositoryImpl(
    private val recordUseCase: RecordUseCase,
) {
    private val _records = MutableStateFlow(emptyList<Record>())
    val records = _records.asStateFlow()

    suspend fun getRecords(date: String) {
        recordUseCase.getRecords.invoke(date)
            .onSuccess { records ->
                _records.value = records.map { Emotion.stringToEmotion(it) }
            }
            .onFailure {

            }
    }

    suspend fun addRecord(record: RecordRequest) = recordUseCase.addRecord(record)
}
