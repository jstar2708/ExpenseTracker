package com.jaideep.expensetracker.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toLowerCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaideep.expensetracker.common.EtDispatcher
import com.jaideep.expensetracker.common.Resource
import com.jaideep.expensetracker.data.local.entities.Category
import com.jaideep.expensetracker.domain.repository.CategoryRepository
import com.jaideep.expensetracker.domain.usecase.GetAllCategoriesUseCase
import com.jaideep.expensetracker.model.TextFieldWithIconAndErrorPopUpState
import com.jaideep.expensetracker.model.dto.CategoryDto
import com.jaideep.expensetracker.presentation.utility.Utility.getCategoryIconName
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddCategoryViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase
) : ViewModel() {

    private val _categories: MutableStateFlow<List<CategoryDto>> = MutableStateFlow(ArrayList())
    private val _categoryDto: MutableStateFlow<CategoryDto?> = MutableStateFlow(null)
    private val _categoryId: MutableStateFlow<Int> = MutableStateFlow(-1)

    val categoryState = mutableStateOf(
        TextFieldWithIconAndErrorPopUpState(
            text = "",
            isError = false,
            showError = false,
            onValueChange = { updateCategoryTextState(it) },
            onErrorIconClick = { updateCategoryErrorState() },
            errorMessage = ""
        )
    )
    var screenTitle = mutableStateOf("Add Category")
    var screenDetails = mutableStateOf("Please provide category details")
    var buttonText = mutableStateOf("Save")
    var isCategoryLoading = mutableStateOf(false)
    var categoryRetrievalError = mutableStateOf(false)
    var categoryName = mutableStateOf(TextFieldValue(""))
        private set
    var isCategorySaved = mutableStateOf(false)
        private set
    var exitScreen = mutableStateOf(false)
        private set

    fun initData(categoryId: Int) {
        getAllCategories()
        if (categoryId != -1) {
            _categoryId.value = categoryId
        }
    }

    private fun getAllCategories() = viewModelScope.launch(EtDispatcher.io) {
        getAllCategoriesUseCase().collect {
            when (it) {
                is Resource.Error -> {
                    isCategoryLoading.value = false
                    categoryRetrievalError.value = true
                }

                is Resource.Success -> {
                    _categories.value = it.data
                    if (_categoryId.value != -1) {
                        try {
                            _categoryDto.value = it.data.asFlow()
                                .first { categoryDto -> categoryDto.id == _categoryId.value }
                            fillCategoryData()
                        } catch (ex: NoSuchElementException) {
                            categoryRetrievalError.value = true
                        }
                    }
                    isCategoryLoading.value = false
                    categoryRetrievalError.value = false
                }

                is Resource.Loading -> {
                    isCategoryLoading.value = true
                    categoryRetrievalError.value = false
                }
            }
        }
    }

    private fun fillCategoryData() {
        _categoryDto.value?.name?.let {
            screenTitle.value = "Update Category"
            screenDetails.value = "Please update category details"
            buttonText.value = "Update"
            categoryState.value = TextFieldWithIconAndErrorPopUpState(
                text = it,
                isError = false,
                showError = false,
                onValueChange = categoryState.value.onValueChange,
                onErrorIconClick = categoryState.value.onErrorIconClick,
                errorMessage = ""
            )
        }
    }

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
            if (_categoryId.value == -1) saveCategory(categoryState.value.text) else updateCategory()
        }
    }

    private fun updateCategory() = viewModelScope.launch(EtDispatcher.io) {
        try {
            _categoryDto.value?.let { categoryDto ->
                categoryRepository.updateCategory(
                    Category(
                        categoryDto.id,
                        categoryName.value.text,
                        getCategoryIconName(categoryDto.iconId)
                    )
                )
                exitScreen.value = true
                isCategorySaved.value = true
            }
        } catch (ex: Exception) {
            isCategorySaved.value = false
            categoryState.value = TextFieldWithIconAndErrorPopUpState(
                text = categoryState.value.text,
                isError = true,
                showError = true,
                onValueChange = categoryState.value.onValueChange,
                onErrorIconClick = categoryState.value.onErrorIconClick,
                errorMessage = "Unable to update category"
            )
        }
    }

    private fun checkCategoryError(): Boolean {
        val isCategoryBlank = categoryState.value.text.isBlank()
        val duplicateCategory = _categories.value.stream().anyMatch {
            it.name.toLowerCase(Locale.current) == categoryState.value.text.toLowerCase(
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
            } else if (screenTitle.value.startsWith("A")) {
                categoryRepository.saveCategory(
                    Category(
                        0, categoryName, "Other"
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