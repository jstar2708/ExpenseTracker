package com.jaideep.expensetracker

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaideep.expensetracker.common.EtDispatcher
import com.jaideep.expensetracker.data.local.entities.Category
import com.jaideep.expensetracker.domain.repository.CategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DefaultViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository
) : ViewModel() {
    private val _isFirstAppInitialization = MutableLiveData<Boolean>()
    var isFirstAppInitialization = _isFirstAppInitialization
    fun addDefaultCategories() {
        viewModelScope.launch(EtDispatcher.io) {
            categoryRepository.saveAllCategories(
                listOf(
                    Category(1, "Food", "Food"),
                    Category(2, "Fuel", "Fuel"),
                    Category(3, "Entertainment", "Entertainment"),
                    Category(4, "Shopping", "Shopping"),
                    Category(5, "Travel", "Travel")
                )
            )
        }
    }

    fun checkFirstAppInitialization() {
        viewModelScope.launch(EtDispatcher.io) {
            _isFirstAppInitialization.postValue(categoryRepository.getAllCategoriesCount() == 0)
        }
    }
}