package com.jaideep.expensetracker.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toLowerCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaideep.expensetracker.data.local.entities.Category
import com.jaideep.expensetracker.domain.repository.CategoryRepository
import com.jaideep.expensetracker.model.TextFieldWithIconAndErrorPopUpState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddCategoryViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    private val _categories: MutableStateFlow<List<Category>> = MutableStateFlow(ArrayList())

    val categoryState = mutableStateOf(
        TextFieldWithIconAndErrorPopUpState(text = "",
            isError = false,
            showError = false,
            onValueChange = { updateCategoryTextState(it) },
            onErrorIconClick = { updateCategoryErrorState() },
            errorMessage = "")
    )
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

    private fun updateCategoryErrorState() {
        categoryState.value = TextFieldWithIconAndErrorPopUpState(
            text = categoryState.value.text,
            isError = categoryState.value.isError,
            showError = !categoryState.value.showError,
            onValueChange = categoryState.value.onValueChange,
            onErrorIconClick = categoryState.value.onErrorIconClick,
            errorMessage = categoryState.value.errorMessage
        )
    }

    private fun updateCategoryTextState(value: String) {
        categoryState.value = TextFieldWithIconAndErrorPopUpState(
            text = value,
            isError = categoryState.value.isError,
            showError = categoryState.value.showError,
            onValueChange = categoryState.value.onValueChange,
            onErrorIconClick = categoryState.value.onErrorIconClick,
            errorMessage = categoryState.value.errorMessage
        )
    }

    fun validateAndSaveCategory() {
        if (checkCategoryError()) {
            saveCategory(categoryState.value.text)
        }
    }

    private fun checkCategoryError(): Boolean {
        val isCategoryBlank = categoryState.value.text.isBlank()
        val duplicateCategory = _categories.value.stream().anyMatch {
            it.categoryName.toLowerCase(Locale.current) == categoryState.value.text.toLowerCase(
                Locale.current
            )
        }
        if (isCategoryBlank || duplicateCategory) {
            categoryState.value = TextFieldWithIconAndErrorPopUpState(
                text = categoryState.value.text,
                isError = true,
                showError = categoryState.value.showError,
                onValueChange = categoryState.value.onValueChange,
                onErrorIconClick = categoryState.value.onErrorIconClick,
                errorMessage = if (isCategoryBlank) "Category cannot be blank" else "Category already exists"
            )
            return false
        }
        return true
    }

    private fun saveCategory(categoryName: String) = viewModelScope.launch {
        try {
            if (categoryName.isBlank()) {
                isCategorySaved.value = false
            } else {
                categoryRepository.saveCategory(
                    Category(
                        0, categoryName, iconName = "category_icon"
                    )
                )
                isCategorySaved.value = true
                exitScreen.value = true
            }
        } catch (ex: Exception) {
            isCategorySaved.value = false
        }
    }
}