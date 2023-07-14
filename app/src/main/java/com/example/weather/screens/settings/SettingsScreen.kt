package com.example.weather.screens.settings

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.weather.ui.theme.GrayWhite

@ExperimentalMaterial3Api
@Composable
fun OptionText(
    label: String,
    placeholder: String,
    iconId: Int,
    optionTextState: MutableState<String>
) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        shape = RoundedCornerShape(10.dp),
        value = optionTextState.value,
        onValueChange = { newText ->
            optionTextState.value = newText
        },
        placeholder = {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = placeholder, color = GrayWhite)
            }
        },
        trailingIcon = {
            Icon(
                painter = painterResource(iconId),
                contentDescription = "Settings trailing Icon",
                tint = Color.White
            )
        },
        label = {
            Text(label)
        },
        singleLine = true,
        colors = TextFieldDefaults
            .outlinedTextFieldColors(
                focusedBorderColor = Color.White,
                unfocusedBorderColor = GrayWhite,
                focusedLabelColor = Color.White,
                unfocusedLabelColor = GrayWhite,
                textColor = Color.White
            )
    )
}

@Composable
fun OptionButton(
    optionText: String,
    onClick: () -> Unit
){
    OutlinedButton(
        modifier = Modifier
            .padding(5.dp),
        onClick = onClick,
        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
        border = BorderStroke(1.dp, Color.White)
    ) {
        Text(text = optionText)
    }
}