package com.moa.data.network

import com.moa.domain.model.request.AddRecordRequest
import com.moa.domain.model.request.AddTodoRequest
import com.moa.domain.model.request.DeleteTodoRequest
import com.moa.domain.model.request.UpdateTodoRequest
import com.moa.domain.model.request.UserRequest
import com.moa.domain.model.response.AddRecordResponse
import com.moa.domain.model.response.GetDiariesResponse
import com.moa.domain.model.response.GetRecordsResponse
import com.moa.domain.model.response.ResponseMessage
import com.moa.domain.model.response.TodosResponse
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.Settings
import com.russhwolf.settings.coroutines.toSuspendSettings
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType

class ApiService {

    private val token = ""

    suspend fun getUserInfo(token: String): UserResponse {
        return ApiClient.httpClient.post(ApiConstants.GET_USER_INFO) {
            contentType(ContentType.Application.Json)
            setBody(token)
        }.body()
    }

    suspend fun googleSignUp(user: UserRequest): String {
//        val token = "" //getToken()
        return ApiClient.httpClient.post(ApiConstants.GOOGLE_SIGN_UP) {
            contentType(ContentType.Application.Json)
            header(HttpHeaders.Authorization, "Bearer $token")
            setBody(user)
        }.body()
    }

    suspend fun getDiaries(): GetDiariesResponse {
        val token = getToken()
        return ApiClient.httpClient.get(ApiConstants.GET_DIARY_LIST) {
            contentType(ContentType.Application.Json)
            header(HttpHeaders.Authorization, "Bearer $token")
        }.body()
    }

    suspend fun getRecords(): GetRecordsResponse {
        val token = getToken()
        return ApiClient.httpClient.get(ApiConstants.GET_RECORD_LIST) {
            contentType(ContentType.Application.Json)
            header(HttpHeaders.Authorization, "Bearer $token")
        }.body()
    }

    suspend fun addRecord(record: AddRecordRequest): AddRecordResponse {
        val token = getToken()
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
        ) {
            header(HttpHeaders.Authorization, "Bearer $token")
        }.body()
    }

    suspend fun updateRecord(): String {
        val token = getToken()
        return ApiClient.httpClient.patch(ApiConstants.UPDATE_RECORD) {
            contentType(ContentType.Application.Json)
            header(HttpHeaders.Authorization, "Bearer $token")
//            setBody()
        }.body()
    }

    suspend fun deleteRecord(): String {
        val token = getToken()
        return ApiClient.httpClient.delete(ApiConstants.DELETE_RECORD) {
            contentType(ContentType.Application.Json)
            header(HttpHeaders.Authorization, "Bearer $token")
//            setBody()
        }.body()
    }

    suspend fun getTodos(): TodosResponse {
        val token = getToken()
        return ApiClient.httpClient.get(ApiConstants.GET_TODOS) {
            contentType(ContentType.Application.Json)
            header(HttpHeaders.Authorization, "Bearer $token")
        }.body()
    }

    suspend fun addTodo(todo: AddTodoRequest): ResponseMessage {
        val token = getToken()
        return ApiClient.httpClient.post(ApiConstants.ADD_TODO) {
            contentType(ContentType.Application.Json)
            header(HttpHeaders.Authorization, "Bearer $token")
            setBody(todo)
        }.body()
    }

    suspend fun updateTodo(todo: UpdateTodoRequest): ResponseMessage {
        val token = getToken()
        return ApiClient.httpClient.patch(ApiConstants.UPDATE_TODO) {
            contentType(ContentType.Application.Json)
            header(HttpHeaders.Authorization, "Bearer $token")
            setBody(todo)
        }.body()
    }

    suspend fun deleteTodo(todo: DeleteTodoRequest): ResponseMessage {
        val token = getToken()
        return ApiClient.httpClient.delete(ApiConstants.DELETE_TODO) {
            contentType(ContentType.Application.Json)
            header(HttpHeaders.Authorization, "Bearer $token")
            setBody(todo)
        }.body()
    }
}

@OptIn(ExperimentalSettingsApi::class)
suspend fun getToken(): String? {
    val settings = Settings()
    val suspendSettings = settings.toSuspendSettings()
    return suspendSettings.getStringOrNull("moa_accessToken_token")
}