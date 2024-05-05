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
class AddTransactionViewModel @Inject constructor(): ViewModel() {
    private val _isCardHidden: MutableLiveData<Boolean> = MutableLiveData(false);
    var isCardHidden: LiveData<Boolean> = _isCardHidden;

    public fun startTime() {
        viewModelScope.launch {
            delay(200)
            _isCardHidden.value = true
        }
    }
}