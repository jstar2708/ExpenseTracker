package com.jaideep.expensetracker.presentation.screens.bottom.home


import android.app.Application
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.jaideep.expensetracker.R
import com.jaideep.expensetracker.common.DetailScreen
import com.jaideep.expensetracker.presentation.screens.bottom.transaction.TransactionItem
import com.jaideep.expensetracker.presentation.theme.AppTheme
import com.jaideep.expensetracker.presentation.theme.md_theme_light_surface
import com.jaideep.expensetracker.presentation.utility.HeadingTextBold
import com.jaideep.expensetracker.presentation.utility.SimpleText
import com.jaideep.expensetracker.presentation.utility.SimpleTextBold

@Preview
@Composable
private fun HomeScreenPreview() {
    AppTheme {
        HomeScreen(NavController(Application()))
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {

        Scaffold(
            modifier = Modifier
                .fillMaxWidth(),
            topBar = {
                AppBar()
            }
        ) {

                Column (
                    Modifier
                        .fillMaxSize()
                        .padding(it),
                    ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        BigBlueButton("Add\nAccount", {
                            navController.navigate(DetailScreen.ADD_ACCOUNT)
                        }, Modifier.weight(1f))
                        BigBlueButton("Add\nTransaction", {
                            navController.navigate(DetailScreen.ADD_TRANSACTION)
                        }, Modifier.weight(1f))
                    }
                    AccountSelectionSpinner()
                    SummaryCard()
                    TransactionSummary()
                }
        }
    }
}

//@Preview(showSystemUi = true, showBackground = true)
@Composable
fun SpentAccordingToDuration() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Spent today",
            fontSize = 20.sp,
            fontWeight = FontWeight.ExtraBold,
        )
        Text(
            text = "$157",
            fontSize = 30.sp,
            fontWeight = FontWeight.ExtraBold,
        )
    }
}

//@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TransactionSummary() {
    SimpleTextBold(
        modifier = Modifier.padding(8.dp),
        text = "Last Transactions"
    )

    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
            .wrapContentHeight()
    ) {

        for (i in 0 until 5) TransactionItem()
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar() {
    TopAppBar(
        title = {
            SimpleTextBold(
                modifier = Modifier.padding(start = 4.dp, end = 4.dp),
                text = "Hello, Jaideep"
            )
        },
        navigationIcon = {
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Outlined.AccountCircle,
                    contentDescription = "User icon",
                    modifier = Modifier.size(40.dp)
                )
            }
        },
        actions = {
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Filled.Notifications,
                    contentDescription = "User icon",
                    modifier = Modifier.size(30.dp)
                )
            }
        }
    )
}

@Composable
fun BigBlueButton(name: String, onClick: () -> Unit, modifier: Modifier) {
    Button(
        onClick = {
            onClick()
        },
        modifier = modifier.padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonColors(
            MaterialTheme.colorScheme.primary,
            MaterialTheme.colorScheme.onPrimary,
            MaterialTheme.colorScheme.primary,
            MaterialTheme.colorScheme.onPrimary
        )
    ) {
        SimpleText(
            text = name,
            textAlignment = TextAlign.Center,
            modifier = Modifier.padding(8.dp),
            color = MaterialTheme.colorScheme.onPrimary
        )
        Spacer(
            modifier = Modifier.size(5.dp)
        )
        Icon(
            imageVector = Icons.Filled.KeyboardArrowRight,
            contentDescription = "Arrow icon",
            modifier = Modifier.padding(8.dp)
        )
    }
}

//@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SummaryCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = CardDefaults.elevatedShape,
        border = BorderStroke(1.dp, Color.Black),
        colors = CardDefaults.cardColors(
            MaterialTheme.colorScheme.surface
        )
    ) {
        Column {
            Spacer(modifier = Modifier.height(20.dp))
            Row (
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = 10.dp)
            ){
                Column {
                    HeadingTextBold(
                        text = "Balance",
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(8.dp)
                    )
                    SimpleText(
                        text = "Monthly Limit \$6000",
                        modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
                    )
                }
                HeadingTextBold(
                    text = "$4500",
                    modifier = Modifier.weight(1f),
                    textAlignment = TextAlign.Center
                )
            }
            CategoryCard()
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

//@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CategoryCard() {
    Column(
        modifier = Modifier
            .wrapContentWidth()
            .padding(8.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Icon(
                painter = painterResource(id = R.drawable.food),
                contentDescription = "Food",
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp)
                    .size(30.dp),
                tint = Color.Unspecified
            )

            SimpleText(
                text = "Food",
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp),
                overflow = TextOverflow.Ellipsis
            )

            SimpleText(
                text = "$0 / $1000",
                modifier = Modifier
                    .padding(8.dp),
                textAlignment = TextAlign.End
            )
        }

        LinearProgressIndicator(
            progress = { 0.8f },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .height(10.dp)
                .clip(RoundedCornerShape(8.dp)),
            color = Color.Yellow
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
//@Preview(showBackground = true)
@Composable
fun AccountSelectionSpinner() {
    var isExpanded by remember {
        mutableStateOf(false)
    }

    var selectedAccount by remember {
        mutableStateOf("All Accounts")
    }

    ExposedDropdownMenuBox(expanded = isExpanded, onExpandedChange = { isExpanded = it}) {
        OutlinedTextField(
            value = selectedAccount,
            onValueChange = {},
            readOnly = true,
            colors = ExposedDropdownMenuDefaults.textFieldColors(
                focusedContainerColor = md_theme_light_surface,
                unfocusedContainerColor = md_theme_light_surface
            ),
            modifier = Modifier
                .menuAnchor()
                .padding(8.dp),
            trailingIcon = {
                Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = "Down arrow")
            }
        )

        ExposedDropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false }
        ) {
            DropdownMenuItem(text = {
                SimpleText(text = "All Accounts")
            }, onClick = {
                selectedAccount = "All Accounts"
                isExpanded = false
            })
            DropdownMenuItem(text = {
                                    SimpleText(text = "Cash")
            }, onClick = {
                selectedAccount = "Cash"
                isExpanded = false
            })
        }
    }
}


