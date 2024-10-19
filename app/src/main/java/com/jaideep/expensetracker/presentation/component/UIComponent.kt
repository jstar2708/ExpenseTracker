package com.jaideep.expensetracker.presentation.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jaideep.expensetracker.presentation.theme.OpenSansFont

@Preview(showBackground = true)
@Composable
private fun MediumBoldTextPreview() {
    MediumText(text = "This is a line.", modifier = Modifier.padding(0.dp))
}

@Composable
fun MediumBoldText(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = Color.Black,
    textAlignment: TextAlign = TextAlign.Start,
    overflow: TextOverflow = TextOverflow.Clip,
    maxLines: Int = Int.MAX_VALUE
) {
    Text(
        modifier = modifier,
        text = text,
        fontSize = 18.sp,
        fontFamily = OpenSansFont.openSans,
        fontWeight = FontWeight.Bold,
        color = color,
        textAlign = textAlignment,
        overflow = overflow,
        maxLines = maxLines
    )
}

@Preview(showBackground = true)
@Composable
private fun MediumTextPreview() {
    MediumText(text = "This is a line.", modifier = Modifier.padding(0.dp))
}

@Composable
fun MediumText(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = Color.Black,
    textAlignment: TextAlign = TextAlign.Start,
    overflow: TextOverflow = TextOverflow.Clip,
    maxLines: Int = Int.MAX_VALUE
) {
    Text(
        modifier = modifier,
        text = text,
        fontSize = 18.sp,
        fontFamily = OpenSansFont.openSans,
        color = color,
        textAlign = textAlignment,
        overflow = overflow,
        maxLines = maxLines
    )
}

@Preview(showBackground = true)
@Composable
private fun HeadingTextBoldPreview() {
    HeadingTextBold(text = "Add Account")
}

@Composable
fun HeadingTextBold(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = Color.Black,
    textAlignment: TextAlign = TextAlign.Start,
    maxLines: Int = Int.MAX_VALUE
) {
    Text(
        modifier = modifier,
        textAlign = textAlignment,
        text = text,
        fontSize = 24.sp,
        fontWeight = FontWeight.ExtraBold,
        fontFamily = OpenSansFont.openSans,
        color = color,
        overflow = TextOverflow.Ellipsis,
        maxLines = maxLines
    )
}

@Preview(showBackground = true)
@Composable
private fun HeadingTextPreview() {
    HeadingText(text = "Cash Account")
}

@Composable
fun HeadingText(
    modifier: Modifier = Modifier,
    textAlignment: TextAlign = TextAlign.Start,
    text: String,
    color: Color = Color.Black,
    maxLines: Int = Int.MAX_VALUE
) {
    Text(
        modifier = modifier,
        text = text,
        fontSize = 24.sp,
        textAlign = textAlignment,
        fontFamily = OpenSansFont.openSans,
        color = color,
        maxLines = maxLines
    )
}

@Preview(showBackground = true)
@Composable
private fun SimpleTextBoldPreview() {
    SimpleTextBold(text = "This is a line.", textAlignment = TextAlign.End)
}

@Composable
fun SimpleTextBold(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = Color.Black,
    overflow: TextOverflow = TextOverflow.Clip,
    maxLines: Int = Int.MAX_VALUE,
    textAlignment: TextAlign = TextAlign.Start
) {
    Text(
        modifier = modifier,
        text = text,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = OpenSansFont.openSans,
        color = color,
        overflow = overflow,
        maxLines = maxLines
    )
}

@Preview(showBackground = true)
@Composable
private fun SimpleTextPreview() {
    SimpleText(text = "This is a line.", modifier = Modifier.padding(0.dp))
}

@Composable
fun SimpleText(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = Color.Black,
    textAlignment: TextAlign = TextAlign.Start,
    overflow: TextOverflow = TextOverflow.Clip,
    maxLines: Int = Int.MAX_VALUE
) {
    Text(
        modifier = modifier,
        text = text,
        fontSize = 16.sp,
        fontFamily = OpenSansFont.openSans,
        color = color,
        textAlign = textAlignment,
        overflow = overflow,
        maxLines = maxLines
    )
}

@Preview(showBackground = true)
@Composable
private fun SimpleSmallTextPreview() {
    SimpleSmallText(text = "This is small text")
}

@Composable
fun SimpleSmallText(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = Color.Black,
    maxLines: Int = Int.MAX_VALUE,
    textAlignment: TextAlign = TextAlign.Start
) {
    Text(
        modifier = modifier,
        text = text,
        textAlign = textAlignment,
        fontSize = 14.sp,
        fontFamily = OpenSansFont.openSans,
        color = color,
        maxLines = maxLines
    )
}
