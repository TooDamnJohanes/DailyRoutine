package com.example.impl.ui.components

import androidx.compose.material.AlertDialog
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.sp
import com.example.core.dimensions.LocalSpacing
import com.example.impl.ui.FieldType

@Composable
fun DailyRoutineLogo(
    logoText: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = logoText,
        modifier = modifier
            .padding(LocalSpacing.current.spaceSmall),
        style = MaterialTheme.typography.h3,
        color = Color.Red.copy(
            alpha = 0.5f
        )
    )
}

@Composable
fun EmailInput(
    modifier: Modifier = Modifier,
    emailState: String,
    onValueChanged: (String, FieldType) -> Unit,
    labelId: String,
    enabled: Boolean = true,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default,
) {
    InputField(
        modifier = modifier,
        valueState = emailState,
        onFieldChanged = onValueChanged,
        labelId = labelId,
        enabled = enabled,
        keyboardType = KeyboardType.Email,
        imeAction = imeAction,
        onAction = onAction
    )
}

@Composable
fun PasswordInput(
    modifier: Modifier = Modifier,
    passwordState: String,
    onValueChanged: (String, FieldType) -> Unit,
    labelId: String,
    enabled: Boolean = true,
    passwordVisibility: MutableState<Boolean>,
    imeAction: ImeAction = ImeAction.Done,
    onAction: KeyboardActions = KeyboardActions.Default,
) {
    val visualTransformation = if (passwordVisibility.value) {
        VisualTransformation.None
    } else {
        PasswordVisualTransformation()
    }
    OutlinedTextField(
        value = passwordState,
        onValueChange = { onValueChanged(it, FieldType.PASSWORD) },
        label = { Text(text = labelId) },
        singleLine = true,
        textStyle = TextStyle(
            fontSize = 18.sp,
            color = MaterialTheme.colors.onBackground,
        ),
        modifier = modifier
            .padding(
                bottom = LocalSpacing.current.spaceSmall,
                start = LocalSpacing.current.spaceSmall,
                end = LocalSpacing.current.spaceSmall
            )
            .fillMaxWidth(),
        enabled = enabled,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = imeAction
        ),
        visualTransformation = visualTransformation,
        trailingIcon = { PasswordVisibility(passwordVisibility = passwordVisibility) },
        keyboardActions = onAction
    )
}

@Composable
fun PasswordVisibility(passwordVisibility: MutableState<Boolean>) {
    val isPasswordVisible = passwordVisibility.value
    IconButton(onClick = { passwordVisibility.value = !isPasswordVisible }) {
        Icons.Default.Close
    }
}

@Composable
fun InputField(
    modifier: Modifier = Modifier,
    valueState: String,
    onFieldChanged: (String, FieldType) -> Unit,
    labelId: String,
    enabled: Boolean,
    isSingleLine: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default
) {
    OutlinedTextField(
        value = valueState,
        onValueChange = { onFieldChanged(it, FieldType.EMAIL) },
        label = { Text(text = labelId) },
        singleLine = isSingleLine,
        textStyle = TextStyle(
            fontSize = 18.sp,
            color = MaterialTheme.colors.onBackground
        ),
        modifier = modifier
            .padding(
                bottom = LocalSpacing.current.spaceSmall,
                start = LocalSpacing.current.spaceSmall,
                end = LocalSpacing.current.spaceSmall
            )
            .fillMaxWidth(),
        enabled = enabled,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        keyboardActions = onAction
    )
}

@Composable
fun AlertDialog() {
    val context = LocalContext.current
    val openDialog = remember {
        mutableStateOf(true)
    }
    if(openDialog.value) {
        AlertDialog(
            onDismissRequest = { openDialog.value = false },
            title = {
                Text(
                    text = "Test Alert Dialog",
                    color = MaterialTheme.colors.onPrimary
                )
            },
            text = {
                Text(
                    text = "this is our Dialog",
                    color = MaterialTheme.colors.onBackground
                )
            },
            confirmButton = {
                TextButton(
                    onClick = { openDialog.value = false }
                ) {
                    Text(
                        text = "CONFIRM BUTTON",
                        color = MaterialTheme.colors.onPrimary
                    )
                }
            },
            dismissButton =  {
                TextButton(
                    onClick = { openDialog.value = false }
                ) {
                    Text(
                        text = "DISMISS BUTTON",
                        color = MaterialTheme.colors.onError
                    )
                }
            },
            backgroundColor = Color.Green,
            contentColor = Color.White
        )
    }
}