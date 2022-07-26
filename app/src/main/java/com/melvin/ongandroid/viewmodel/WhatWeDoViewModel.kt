package com.melvin.ongandroid.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.melvin.ongandroid.data.ApiClient
import com.melvin.ongandroid.model.entities.whatWeDo.WhatWeDo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WhatWeDoViewModel @Inject constructor(
    private val repository: ApiClient
) : ViewModel() {

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status

    private val _whatWeDoList = MutableLiveData<List<WhatWeDo>>()
    val whatWeDoList: LiveData<List<WhatWeDo>>
        get() = _whatWeDoList


    fun setWhatWeDo() {
        _status.value = ApiStatus.LOADING
        viewModelScope.launch {
            val response = repository.getActivities()
            if (response.success) {
                _whatWeDoList.value = response.whatWeDoList
                _status.value = ApiStatus.SUCCESS
            } else {
                _whatWeDoList.value = emptyList()
                _status.value = ApiStatus.FAILURE
            }
        }
    }


}