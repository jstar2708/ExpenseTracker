package com.jaideep.expensetracker.presentation.screens.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jaideep.expensetracker.R
import com.jaideep.expensetracker.presentation.theme.md_theme_light_primary
import com.jaideep.expensetracker.presentation.theme.md_theme_light_surface
import com.jaideep.expensetracker.presentation.utility.SimpleText
import com.jaideep.expensetracker.presentation.utility.SimpleTextBold


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseTrackerAppBar(
    title: String,
    navigationIcon: ImageVector,
    navigationDescription: String,
    onNavigationIconClick: () -> Unit,
    actionIcon: ImageVector,
    actionDescription: String,
    onActionIconClick: () -> Unit,

    ) {
    TopAppBar(title = {
        SimpleTextBold(
            modifier = Modifier.padding(start = 4.dp, end = 4.dp), text = title
        )
    }, navigationIcon = {
        IconButton(onClick = { onNavigationIconClick() }) {
            Icon(
                imageVector = navigationIcon,
                contentDescription = navigationDescription,
                modifier = Modifier.size(40.dp)
            )
        }
    }, actions = {
        IconButton(onClick = { onActionIconClick() }) {
            Icon(
                imageVector = actionIcon,
                contentDescription = actionDescription,
                modifier = Modifier.size(30.dp)
            )
        }
    })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseTrackerSpinner(
    modifier: Modifier = Modifier,
    values: List<String>,
    onValueChanged: () -> Unit,

    ) {
    var isExpanded by remember {
        mutableStateOf(false)
    }

    var selectedAccount by remember {
        mutableStateOf(values[0])
    }

    ExposedDropdownMenuBox(modifier = modifier,
        expanded = isExpanded,
        onExpandedChange = { isExpanded = it }) {
        OutlinedTextField(value = selectedAccount,
            onValueChange = { onValueChanged() },
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
            })

        ExposedDropdownMenu(expanded = isExpanded, onDismissRequest = { isExpanded = false }) {
            values.forEach {
                DropdownMenuItem(text = {
                    SimpleText(text = it)
                }, onClick = {
                    selectedAccount = it
                    isExpanded = false
                })
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ExpenseTrackerCategoryCardPreview() {
    ExpenseTrackerCategoryCard(
        iconId = R.drawable.food,
        iconDescription = "Food icon",
        categoryName = "Food",
        spendValue = "$0 / $1000",
        progressValue = 0.8f,
        trackColor = Color.Yellow
    )
}

@Composable
fun ExpenseTrackerCategoryCard(
    iconId: Int,
    iconDescription: String,
    categoryName: String,
    spendValue: String,
    progressValue: Float,
    trackColor: Color
) {
    Column(
        modifier = Modifier
            .wrapContentWidth()
            .padding(8.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Icon(
                painter = painterResource(id = iconId),
                contentDescription = iconDescription,
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp)
                    .size(30.dp),
                tint = Color.Unspecified
            )

            SimpleText(
                text = categoryName,
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp),
                overflow = TextOverflow.Ellipsis
            )

            SimpleText(
                text = spendValue, modifier = Modifier.padding(8.dp), textAlignment = TextAlign.End
            )
        }

        LinearProgressIndicator(
            progress = { progressValue },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .height(10.dp)
                .clip(RoundedCornerShape(8.dp)),
            color = trackColor
        )
    }
}

@Composable
fun ExpenseTrackerBlueButton(name: String, onClick: () -> Unit, modifier: Modifier) {
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
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = "Arrow icon",
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun ExpenseTrackerTransactionCardItemPreview() {
    ExpenseTrackerTransactionCardItem(
        iconId = R.drawable.fuel,
        iconDescription = "Fuel icon",
        categoryName = "Fuel",
        transactionDescription = "Petrol in scooter",
        amount = "$49"
    )
}
@Composable
fun ExpenseTrackerTransactionCardItem(
    iconId: Int,
    iconDescription: String,
    categoryName: String,
    transactionDescription: String,
    amount: String
) {
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
                painter = painterResource(id = iconId),
                contentDescription = iconDescription,
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp)
                    .size(30.dp),
                tint = Color.Unspecified
            )

            Column(Modifier.weight(1f)) {
                SimpleTextBold(
                    text = categoryName,
                    modifier = Modifier
                        .padding(4.dp),
                    overflow = TextOverflow.Ellipsis
                )

                SimpleText(
                    text = transactionDescription,
                    modifier = Modifier
                        .padding(4.dp),
                    overflow = TextOverflow.Ellipsis,
                    color = Color.DarkGray
                )
            }

            SimpleTextBold(
                text = amount,
                modifier = Modifier
                    .padding(4.dp),
                overflow = TextOverflow.Ellipsis,
                color = Color.Red
            )
        }
    }
}

@Composable
fun ExpenseTrackerTabLayout(
    modifier: Modifier = Modifier,
    values: Array<String>,
    onClick: () -> Unit
) {
    var selectedTab by remember {
        mutableIntStateOf(0)
    }
    TabRow(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth(.8f),
        selectedTabIndex = selectedTab,
        divider = {},
        indicator = {},
        contentColor = Color.Black
    ) {
        values.forEachIndexed { index, title ->
            Tab(
                selected = index == selectedTab,
                modifier = if (index == selectedTab) Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(md_theme_light_primary)
                    .wrapContentWidth() else Modifier.clip(RoundedCornerShape(10.dp)),
                onClick = {
                    selectedTab = index
                    onClick()
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
}

