package com.jaideep.expensetracker.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaideep.expensetracker.data.local.entities.Category
import com.jaideep.expensetracker.domain.repository.CategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddCategoryViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository
): ViewModel() {

    var errorMessage = mutableStateOf("")
        private set
    var categoryName = mutableStateOf(TextFieldValue(""))
        private set
    var isCategoryNameIncorrect = mutableStateOf(false)
        private set
    var isCategorySaved = mutableStateOf(false)
        private set
    var exitScreen = mutableStateOf(false)
        private set


    fun saveCategory(categoryName: String) = viewModelScope.launch {
        try {
            if (categoryName.isBlank()) {
                isCategoryNameIncorrect.value = true
                errorMessage.value = "Category name cannot be blank"
            }
            else {
                categoryRepository.saveCategory(
                    Category(
                        0,
                        categoryName,
                        iconName = "category_icon"
                    )
                )
                isCategoryNameIncorrect.value = false
                isCategorySaved.value = true
                exitScreen.value = true
            }
        }
        catch (ex: Exception) {
            isCategorySaved.value = false
        }
    }
}