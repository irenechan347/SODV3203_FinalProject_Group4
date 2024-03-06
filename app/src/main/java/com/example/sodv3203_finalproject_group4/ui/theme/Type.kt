package com.example.sodv3203_finalproject_group4.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.sodv3203_finalproject_group4.R


val Abel = FontFamily(
    Font(R.font.abel_regular)
)

val AmaticSC = FontFamily(
    Font(R.font.amatic_sc_regular),
    Font(R.font.amatic_sc_bold, FontWeight.Bold)
)

val Lato = FontFamily(
    Font(R.font.lato_regular),
)

// Set of Material typography styles to start with
val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = AmaticSC,
        fontWeight = FontWeight.Normal,
        fontSize = 36.sp
    ),
    displayMedium = TextStyle(
        fontFamily = AmaticSC,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    ),
    labelSmall = TextStyle(
        fontFamily = Abel,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = Abel,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    titleSmall = TextStyle(
        fontFamily = Lato,
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp
    )
)