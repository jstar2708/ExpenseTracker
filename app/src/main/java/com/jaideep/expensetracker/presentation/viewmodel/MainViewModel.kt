package com.jaideep.expensetracker.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(): ViewModel() {
    private val _isScreenVisible: MutableLiveData<Boolean> = MutableLiveData(false);
    var isScreenVisible: LiveData<Boolean> = _isScreenVisible;

    fun startTime() {
        viewModelScope.launch {
            delay(500)
            _isScreenVisible.value = true;
        }
    }
}