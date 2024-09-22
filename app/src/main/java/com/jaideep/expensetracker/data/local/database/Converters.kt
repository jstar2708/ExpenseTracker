package com.jaideep.expensetracker.data.local.database

import androidx.room.TypeConverter
import com.jaideep.expensetracker.data.local.entities.Category
import com.jaideep.expensetracker.model.dto.CategoryDto
import com.jaideep.expensetracker.presentation.utility.Utility.getCategoryIconId
import com.jaideep.expensetracker.presentation.utility.Utility.getCategoryIconName
import java.time.LocalDate
import java.time.ZoneId

class Converters {
    @TypeConverter
    fun toLong(date: LocalDate): Long {
        return date.atStartOfDay(ZoneId.of("Asia/Kolkata")).toEpochSecond() * 1000
    }

    @TypeConverter
    fun toLocalDate(date: Long): LocalDate {
        return LocalDate.ofEpochDay(date / 86_400_000L)
    }

    @TypeConverter
    fun toBoolean(value: Int): Boolean {
        return value == 1
    }

    @TypeConverter
    fun toInt(value: Boolean): Int {
        return if (value) 1 else 0
    }

    @TypeConverter
    fun toCategoryDto(category: Category): CategoryDto {
        return CategoryDto(
            category.id, category.name, getCategoryIconId(category.iconName)
        )
    }

    @TypeConverter
    fun toCategory(categoryDto: CategoryDto): Category {
        return Category(categoryDto.id, categoryDto.name, getCategoryIconName(categoryDto.iconId))
    }

}