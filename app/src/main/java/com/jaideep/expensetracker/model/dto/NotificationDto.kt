package com.jaideep.expensetracker.model.dto

import java.time.LocalDate

data class NotificationDto(
    val id: Int, val message: String, val date: LocalDate
)