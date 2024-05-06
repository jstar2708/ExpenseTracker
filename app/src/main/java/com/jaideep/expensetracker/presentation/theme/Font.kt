package com.jaideep.expensetracker.presentation.theme

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.jaideep.expensetracker.R

object OpenSansFont {
    private val openSansRegular: Font = Font(R.font.open_sans_regular, FontWeight.Normal)
    private val openSansBold: Font = Font(R.font.open_sans_bold, FontWeight.Bold)
    private val openSansExtraBold: Font = Font(R.font.open_sans_extra_bold, FontWeight.ExtraBold)
    private val openSansMedium: Font = Font(R.font.open_sans_medium, FontWeight.Medium)
    val openSans = FontFamily(openSansRegular, openSansMedium, openSansBold, openSansExtraBold)
}