package com.moa.data.network

import com.moa.domain.model.request.AddRecordRequest
import com.moa.domain.model.request.AddTodoRequest
import com.moa.domain.model.request.CreateDiaryRequest
import com.moa.domain.model.request.DeleteTodoRequest
import com.moa.domain.model.request.GetUserInfoRequest
import com.moa.domain.model.request.UpdateTodoRequest
import com.moa.domain.model.request.UserRequest
import com.moa.domain.model.response.AddRecordResponse
import com.moa.domain.model.response.CreateDiaryResponse
import com.moa.domain.model.response.GetDiariesResponse
import com.moa.domain.model.response.GetRecordsResponse
import com.moa.domain.model.response.ResponseMessage
import com.moa.domain.model.response.SignInResponse
import com.moa.domain.model.response.TodosResponse
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.get
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType

class ApiService {

    suspend fun getUserInfo(token: GetUserInfoRequest): SignInResponse {
        return ApiClient.httpClient.post(ApiConstants.GET_USER_INFO) {
            contentType(ContentType.Application.Json)
            setBody(token)
        }.body()
    }

    suspend fun googleSignUp(user: UserRequest): SignInResponse {
        return ApiClient.httpClient.post(ApiConstants.GOOGLE_SIGN_UP) {
            contentType(ContentType.Application.Json)
            setBody(user)
        }.body()
    }

    suspend fun getDiaries(): GetDiariesResponse {
        return ApiClient.httpClient.get(ApiConstants.GET_DIARY_LIST).body()
    }

    suspend fun createDiary(diary: CreateDiaryRequest): CreateDiaryResponse {
        return ApiClient.httpClient.post(ApiConstants.CREATE_DIARY) {
            contentType(ContentType.Application.Json)
            setBody(diary)
        }.body()
    }

    suspend fun getRecords(): GetRecordsResponse {
        return ApiClient.httpClient.get(ApiConstants.GET_RECORD_LIST).body()
    }

    suspend fun addRecord(record: AddRecordRequest): AddRecordResponse {
        return ApiClient.httpClient.submitFormWithBinaryData(
            url = ApiConstants.ADD_RECORD,
            formData = formData {
                record.content?.let {
                    append("context", it)
                }
                record.imageBytes?.let { imageBytes ->
                    append(
                        "file",
                        imageBytes,
                        Headers.build {
                            append(HttpHeaders.ContentType, "image/jpeg")
                            append(HttpHeaders.ContentDisposition, "filename=\"image.jpg\"")
                        }
                    )
                }
                record.audioBytes?.let { audio ->
                    append(
                        "file",
                        audio,
                        Headers.build {
                            append(HttpHeaders.ContentType, "audio/mpeg")
                            append(HttpHeaders.ContentDisposition, "filename=\"audio.mp3\"")
                        }
                    )
                }
            }
        ).body()
    }

    suspend fun updateRecord(): String {
        return ApiClient.httpClient.patch(ApiConstants.UPDATE_RECORD) {
            contentType(ContentType.Application.Json)
//            setBody()
        }.body()
    }

    suspend fun deleteRecord(): String {
        return ApiClient.httpClient.delete(ApiConstants.DELETE_RECORD) {
            contentType(ContentType.Application.Json)
//            setBody()
        }.body()
    }

    suspend fun getTodos(): TodosResponse {
        return ApiClient.httpClient.get(ApiConstants.GET_TODOS).body()
    }

    suspend fun addTodo(todo: AddTodoRequest): ResponseMessage {
        return ApiClient.httpClient.post(ApiConstants.ADD_TODO) {
            contentType(ContentType.Application.Json)
            setBody(todo)
        }.body()
    }

    suspend fun updateTodo(todo: UpdateTodoRequest): ResponseMessage {
        return ApiClient.httpClient.patch(ApiConstants.UPDATE_TODO) {
            contentType(ContentType.Application.Json)
            setBody(todo)
        }.body()
    }

    suspend fun deleteTodo(todo: DeleteTodoRequest): ResponseMessage {
        return ApiClient.httpClient.delete(ApiConstants.DELETE_TODO) {
            contentType(ContentType.Application.Json)
            setBody(todo)
        }.body()
    }
}