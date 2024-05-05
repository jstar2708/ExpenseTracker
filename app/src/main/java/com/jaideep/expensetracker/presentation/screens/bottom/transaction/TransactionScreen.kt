package com.jaideep.expensetracker.presentation.screens.bottom.transaction

import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.jaideep.expensetracker.R
import com.jaideep.expensetracker.common.DetailScreen
import com.jaideep.expensetracker.presentation.screens.bottom.home.AccountSelectionSpinner
import com.jaideep.expensetracker.presentation.theme.AppTheme
import com.jaideep.expensetracker.presentation.theme.md_theme_light_primary
import com.jaideep.expensetracker.presentation.utility.SimpleText
import com.jaideep.expensetracker.presentation.utility.SimpleTextBold

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun TransactionScreenPreview() {
    AppTheme {
        TransactionScreen(NavController(Application()))
    }
}
//@Preview(showSystemUi = true, showBackground = true)
@Composable
fun TransactionScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TransactionAppBar {
                navController.navigate(DetailScreen.ADD_TRANSACTION)
            }
        }
    ) {
        val list = listOf("All", "Income", "Expense")
        var selectedTab by remember {
            mutableIntStateOf(0)
        }
        Column (
            Modifier
                .padding(it)
                .fillMaxWidth()){
            AccountSelectionSpinner()
            Row (Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically){
                TabRow(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(.7f),
                    selectedTabIndex = selectedTab,
                    divider = {},
                    indicator = {},
                    contentColor = Color.Black
                ) {
                    list.forEachIndexed { index, title ->
                        Tab(
                            selected = index == selectedTab,
                            modifier = if (index == selectedTab) Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .background(md_theme_light_primary)

                                .wrapContentWidth() else Modifier.clip(RoundedCornerShape(10.dp)),
                            onClick = {
                                selectedTab = index
                            },
                            selectedContentColor = Color.White,
                            unselectedContentColor = Color.Black
                        ) {
                            SimpleText(
                                text = title,
                                modifier = Modifier.padding(8.dp),
                                color = Color.Unspecified
                            )
                        }
                    }
                }

                val interactionSource = remember { MutableInteractionSource() }
                    Row (
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable(
                            interactionSource,
                            null
                        ) {}
                    ) {
                        Icon(painter = painterResource(id = R.drawable.filter), contentDescription = "Filter",
                            modifier = Modifier.size(30.dp), tint = Color.Unspecified)
                        SimpleText(text = "Filter", modifier = Modifier.padding(8.dp))
                }

            }

            val scrollState = rememberScrollState()
            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(scrollState)
                    .wrapContentHeight()
            ) {
                for (i in 0 until 10) TransactionItem()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionAppBar(onAddButtonClick : ()-> Unit) {
    TopAppBar(title = {
        SimpleTextBold(
            modifier = Modifier.padding(start = 4.dp, end = 4.dp),
            text = "Transactions"
        )
    },
        navigationIcon = {
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back button",
                    modifier = Modifier.size(40.dp)
                )
            }
        },
        actions = {
            IconButton(onClick = {
                onAddButtonClick()
            }) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add Transaction",
                    modifier = Modifier.size(30.dp)
                )
            }
        }
    )
}

@Composable
fun TransactionItem() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.food),
                contentDescription = "Food",
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp)
                    .size(30.dp),
                tint = Color.Unspecified
            )

            Column(Modifier.weight(1f)) {
                SimpleTextBold(
                    text = "Food",
                    modifier = Modifier
                        .padding(4.dp),
                    overflow = TextOverflow.Ellipsis
                )

                SimpleText(
                    text = "Burger at Burger Singh",
                    modifier = Modifier
                        .padding(4.dp),
                    overflow = TextOverflow.Ellipsis,
                    color = Color.DarkGray
                )
            }

            SimpleTextBold(
                text = "$49",
                modifier = Modifier
                    .padding(4.dp),
                overflow = TextOverflow.Ellipsis,
                color = Color.Red
            )
        }
    }
}