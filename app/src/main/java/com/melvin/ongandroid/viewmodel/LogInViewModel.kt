package com.melvin.ongandroid.viewmodel

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult.*
import androidx.core.util.PatternsCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.melvin.ongandroid.R
import com.melvin.ongandroid.data.AppData
import com.melvin.ongandroid.domain.use_case.LogInUseCase
import com.melvin.ongandroid.model.entities.LoginRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(
    private val appData: AppData,
    private val logInUseCase: LogInUseCase,
) : ViewModel() {

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus> = _status
    val logInUserCharging = MutableLiveData(false)

    fun validateEmail(Email: String?): Boolean {
        return if (Email != null) {
            Email.isNotEmpty() && PatternsCompat.EMAIL_ADDRESS.matcher(Email).matches()
        } else {
            false
        }
    }

    fun validatePassword(password: String?): Boolean {
        val passwordRegex = Pattern.compile(
            "^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    "(?=.*[a-z])" +        //at least 1 lower case letter
                    "(?=.*[A-Z])" +        //at least 1 upper case letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{4,}" +               //at least 4 characters
                    "$"
        )
        return if (password != null) {
            (password.isNotEmpty() && passwordRegex.matcher(password).matches())
        } else {
            false
        }
    }

    fun logInUser(logIn: LoginRequest) {
        logInUserCharging.postValue(true)
        viewModelScope.launch {
            val apiLogIn = logInUseCase.logInUser(logIn)
            logInUserCharging.postValue(false)
            if (apiLogIn.success) {
                _status.value = ApiStatus.SUCCESS
                appData.saveKey(apiLogIn.data.token)
            } else {
                _status.value = ApiStatus.FAILURE
            }
        }
    }

    fun loginWithGoogle(status: String): Boolean {
        when(status){
            "LOGIN_ACTION" -> {
                logInUserCharging.postValue(true)
                return true
            }
            "LOGGED_IN" -> {
                logInUserCharging.postValue(false)
                return true

            }
        }
        return true
    }
}