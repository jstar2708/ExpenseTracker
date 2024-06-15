package com.jaideep.expensetracker.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaideep.expensetracker.data.local.entities.Category
import com.jaideep.expensetracker.domain.repository.CategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddCategoryViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository
): ViewModel() {

    var categoryName = mutableStateOf(TextFieldValue(""))
        private set

    fun saveCategory(categoryName: String) = viewModelScope.launch {
        try {
            categoryRepository.saveCategory(
                Category(
                    0,
                    categoryName,
                    iconName = "category_icon"
                )
            )
        }
        catch (ex: Exception) {
            TODO("Handle errors")
        }
    }
}