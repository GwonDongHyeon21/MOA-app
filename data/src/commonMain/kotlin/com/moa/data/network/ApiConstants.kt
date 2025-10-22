package com.moa.data.network

object ApiConstants {
    const val GET_USER_INFO = "/auth/google/verify"
    const val GOOGLE_SIGN_UP = "/auth/google/signUp"

    const val GET_DIARY_LIST = "/diary/list"
    const val GET_RECORD_LIST = "/record/list"
    const val ADD_TEXT_IMAGE_RECORD = "/record/create"
    const val ADD_AUDIO_RECORD = "/record/audio"
    const val UPDATE_RECORD = "/record/update"
    const val DELETE_RECORD = "/record/delete"

    const val GET_TODOS = "/todo/list"
    const val ADD_TODO = "/todo/create"
    const val UPDATE_TODO = "/todo/update"
    const val DELETE_TODO = "/todo/delete"
}