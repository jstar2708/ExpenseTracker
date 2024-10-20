package com.jaideep.expensetracker.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaideep.expensetracker.common.EtDispatcher
import com.jaideep.expensetracker.common.Resource
import com.jaideep.expensetracker.data.local.entities.Notification
import com.jaideep.expensetracker.domain.repository.NotificationRepository
import com.jaideep.expensetracker.domain.usecase.GetAllNotificationUseCase
import com.jaideep.expensetracker.model.SnackBarState
import com.jaideep.expensetracker.model.TextFieldWithIconAndErrorPopUpState
import com.jaideep.expensetracker.model.dto.NotificationDto
import com.jaideep.expensetracker.presentation.utility.Utility.getDateInMillis
import com.jaideep.expensetracker.presentation.utility.Utility.stringDateToMillis
import com.jaideep.expensetracker.presentation.utility.checkIfDateIsEqualOrBeforeToday
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val notificationRepository: NotificationRepository,
    private val getAllNotificationUseCase: GetAllNotificationUseCase
) : ViewModel() {
    val tabList: ImmutableList<String> = persistentListOf("Upcoming", "Completed")
    private val _notifications: MutableStateFlow<List<NotificationDto>> =
        MutableStateFlow(emptyList())
    var completedNotifications = _notifications.map { notificationDtoList ->
        notificationDtoList.filter { notificationDto ->
            checkIfDateIsEqualOrBeforeToday(notificationDto.date)
        }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())
        private set

    var upcomingNotifications = _notifications.map { notificationDtoList ->
        notificationDtoList.filter { notificationDto ->
            !checkIfDateIsEqualOrBeforeToday(notificationDto.date)
        }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())
        private set

    var notificationRetrievalError by mutableStateOf(false)
        private set
    var isNotificationLoading by mutableStateOf(true)
        private set
    var snackBarState: SnackBarState by mutableStateOf(SnackBarState(false, ""))
        private set

    var messageState by mutableStateOf(
        TextFieldWithIconAndErrorPopUpState(
            text = "",
            isError = false,
            showError = false,
            onValueChange = { updateMessageTextState(it) },
            onErrorIconClick = { updateMessageErrorState() },
            errorMessage = ""
        )
    )
        private set

    var dateState by mutableStateOf(
        TextFieldWithIconAndErrorPopUpState(
            text = "",
            isError = false,
            showError = false,
            onValueChange = { updateDateTextState(it) },
            onErrorIconClick = { updateDateErrorState() },
            errorMessage = ""
        )
    )
        private set

    var selectedTab by mutableIntStateOf(0)
        private set

    fun updateSelectedTab(pos: Int) {
        selectedTab = pos
    }

    fun initData() {
        getAllNotifications()
    }

    private fun updateDateErrorState() {
        dateState = TextFieldWithIconAndErrorPopUpState(
            text = dateState.text,
            isError = dateState.isError,
            showError = !dateState.showError,
            onValueChange = dateState.onValueChange,
            onErrorIconClick = dateState.onErrorIconClick,
            errorMessage = dateState.errorMessage
        )
    }

    private fun updateDateTextState(value: String) {
        dateState = TextFieldWithIconAndErrorPopUpState(
            text = value,
            isError = dateState.isError,
            showError = dateState.showError,
            onValueChange = dateState.onValueChange,
            onErrorIconClick = dateState.onErrorIconClick,
            errorMessage = dateState.errorMessage
        )
    }

    private fun updateMessageErrorState() {
        messageState = TextFieldWithIconAndErrorPopUpState(
            text = messageState.text,
            isError = messageState.isError,
            showError = !messageState.showError,
            onValueChange = messageState.onValueChange,
            onErrorIconClick = messageState.onErrorIconClick,
            errorMessage = messageState.errorMessage
        )
    }

    private fun updateMessageTextState(value: String) {
        messageState = TextFieldWithIconAndErrorPopUpState(
            text = value,
            isError = messageState.isError,
            showError = messageState.showError,
            onValueChange = messageState.onValueChange,
            onErrorIconClick = messageState.onErrorIconClick,
            errorMessage = messageState.errorMessage
        )
    }

    private fun getAllNotifications() = viewModelScope.launch(EtDispatcher.io) {
        getAllNotificationUseCase().collect {
            when (it) {
                is Resource.Error -> {
                    isNotificationLoading = false
                    notificationRetrievalError = true
                }

                is Resource.Loading -> {
                    isNotificationLoading = true
                    notificationRetrievalError = false
                }

                is Resource.Success -> {
                    _notifications.value = it.data
                    isNotificationLoading = false
                    notificationRetrievalError = false
                }
            }
        }
    }

    fun validateAndSaveNotification() {
        if (isNotificationValid()) {
            saveNotification()
        }
    }

    private fun saveNotification() = viewModelScope.launch(EtDispatcher.io) {
        notificationRepository.saveNotification(
            Notification(
                0, messageState.text, stringDateToMillis(dateState.text)
            )
        )
        snackBarState = SnackBarState(true, "Notification saved successfully")
    }

    private fun isNotificationValid(): Boolean {
        if (messageState.text.isBlank()) {
            messageState = TextFieldWithIconAndErrorPopUpState(
                text = messageState.text,
                isError = true,
                showError = true,
                errorMessage = "Message cannot be blank",
                onValueChange = messageState.onValueChange,
                onErrorIconClick = messageState.onErrorIconClick
            )
            return false
        }
        if (dateState.text.isBlank() || checkIfDateIsEqualOrBeforeToday(LocalDate.parse(dateState.text))) {
            dateState = TextFieldWithIconAndErrorPopUpState(
                text = dateState.text,
                isError = true,
                showError = true,
                errorMessage = if (dateState.text.isBlank()) "Date cannot be blank" else "Select a date in future",
                onValueChange = dateState.onValueChange,
                onErrorIconClick = dateState.onErrorIconClick
            )
            return false
        }
        return true
    }

    fun onNotificationDelete(notificationDto: NotificationDto) =
        viewModelScope.launch(EtDispatcher.io) {
            notificationRepository.deleteNotification(
                Notification(
                    notificationDto.id,
                    notificationDto.message,
                    getDateInMillis(notificationDto.date)
                )
            )
            snackBarState = SnackBarState(true, "Notification deleted successfully")
        }

    fun disableSnackBarState() {
        snackBarState = SnackBarState(false, "")
    }
}