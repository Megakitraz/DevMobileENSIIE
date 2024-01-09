package com.manonpoulain.todo.user

import androidx.lifecycle.ViewModel
import com.manonpoulain.todo.data.Api
import okhttp3.MultipartBody

class UserViewModel : ViewModel() {

    suspend fun UpdateAvatar(uriToRequest : MultipartBody.Part) {
        Api.userWebService.updateAvatar(uriToRequest)
    }
}