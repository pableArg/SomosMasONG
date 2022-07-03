package com.melvin.ongandroid.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.melvin.ongandroid.data.ApiClient
import com.melvin.ongandroid.model.entities.we.Member
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsViewModel @Inject constructor(
    private val dataProvider: ApiClient
): ViewModel() {

    private val _status = MutableLiveData<ApiStatus>()
    private val _membersList = MutableLiveData<List<Member>>()

    fun observeMembersList(): MutableLiveData<List<Member>> {
        return this._membersList
    }

    fun getMembers() {
        viewModelScope.launch {
            val response = dataProvider.getMembers()
            if (response.success) {
                _membersList.value = response.data
                _status.value = ApiStatus.SUCCESS
            } else {
                _membersList.value = emptyList()
                _status.value = ApiStatus.FAILURE
            }
        }
    }
}