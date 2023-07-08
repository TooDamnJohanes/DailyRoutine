@file:Suppress("OPT_IN_IS_NOT_ENABLED")

package com.dailyroutineapp.impl.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dailyroutineapp.core.R
import com.dailyroutineapp.core.dimensions.LocalSpacing
import com.dailyroutineapp.core.navigation.DailyRoutineScreens
import com.dailyroutineapp.core.util.UiEvent
import com.dailyroutineapp.impl.ui.components.AuthenticationAlertDialog
import com.dailyroutineapp.impl.ui.components.DailyRoutineLogo
import com.dailyroutineapp.impl.ui.components.EmailInput
import com.dailyroutineapp.impl.ui.components.PasswordInput
import com.dailyroutineapp.impl.ui.viewmodels.AuthenticationScreenViewModel

@Composable
fun AuthenticationScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    authenticationScreenViewModel: AuthenticationScreenViewModel = hiltViewModel()
) {
    val spacing = LocalSpacing.current
    val focusRequester = remember {
        FocusRequester.Default
    }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    val showErrorDialogState = authenticationScreenViewModel.showErrorDialog.value
    val isCreatingAccountState = authenticationScreenViewModel.isCreatingAccount.value
    val emailFieldState = authenticationScreenViewModel.emailFieldInput.value
    val passwordFieldState = authenticationScreenViewModel.passwordFieldInput.value
    val passwordVisibility = authenticationScreenViewModel.passwordVisibility.value
    val isFieldsValid = authenticationScreenViewModel.isFieldsValid.value

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            DailyRoutineLogo(logoText = stringResource(id = R.string.app_name))
            UserForm(emailState = emailFieldState,
                passwordState = passwordFieldState,
                isFieldsValid = isFieldsValid,
                isLoading = false,
                isCreateAccount = isCreatingAccountState,
                passwordVisibility = passwordVisibility,
                focusRequester = focusRequester,
                onValueChanged = { value, type ->
                    authenticationScreenViewModel.changeInputFieldsValue(
                        value = value, fieldType = type
                    )
                },
                onDone = {
                    authenticationScreenViewModel.authenticateUserWithEmailAndPassword { authResult ->
                        if (authResult) {
                            onNavigate(UiEvent.Navigate(DailyRoutineScreens.Route.HABITS_HOME))
                        }
                    }
                },
                onPasswordVisibilityChanged = {
                    authenticationScreenViewModel.onPasswordVisibilityChanged()
                })
        }
        AuthenticationErrorDialog(
            showErrorDialogState,
            isCreatingAccountState,
        ) {
            authenticationScreenViewModel.onAuthenticationAlertDialogDismiss()
        }
        Spacer(modifier = Modifier.height(spacing.spaceMedium))
        Row(
            modifier = Modifier.padding(spacing.spaceMedium),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = IsNewUserLabelText(isCreatingAccountState))
            Text(
                text = SingUpLabelText(isCreatingAccountState),
                modifier = Modifier
                    .padding(spacing.spaceExtraSmall)
                    .clickable {
                        authenticationScreenViewModel.changeFormType()
                    },
                style = TextStyle(
                    color = MaterialTheme.colors.secondaryVariant,
                ),
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun AuthenticationErrorDialog(
    showErrorDialogState: Boolean,
    isCreatingAccountState: Boolean,
    onDialogConfirmButton: () -> Unit
) {
    if (showErrorDialogState) {
        AuthenticationAlertDialog(
            isCreatingAccount = isCreatingAccountState,
            onDialogConfirmButton = onDialogConfirmButton
        )
    }
}

@Composable
@OptIn(ExperimentalComposeUiApi::class)
fun UserForm(
    emailState: String,
    passwordState: String,
    isFieldsValid: Boolean,
    isLoading: Boolean,
    isCreateAccount: Boolean,
    passwordVisibility: Boolean,
    focusRequester: FocusRequester,
    onValueChanged: (String, FieldType) -> Unit,
    onPasswordVisibilityChanged: () -> Unit,
    onDone: () -> Unit,
) {

    val keyboardController = LocalSoftwareKeyboardController.current

    val modifier = Modifier
        .height(250.dp)
        .background(MaterialTheme.colors.background)
        .verticalScroll(rememberScrollState())

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        if (isCreateAccount) {
            ShowCreateAccountText()
        }

        EmailInput(
            emailState = emailState,
            onValueChanged = onValueChanged,
            labelId = stringResource(R.string.authenticationScreen_email_label),
            enabled = !isLoading,
        )

        PasswordInput(modifier = Modifier.focusRequester(focusRequester),
            passwordState = passwordState,
            onValueChanged = onValueChanged,
            labelId = stringResource(R.string.authenticationScreen_password_label),
            enabled = !isLoading,
            passwordVisibility = passwordVisibility,
            onPasswordVisibilityChanged = onPasswordVisibilityChanged,
            onAction = KeyboardActions {
                if (isFieldsValid) {
                    onDone()
                    keyboardController?.hide()
                } else {
                    return@KeyboardActions
                }
            })

        SubmitButton(textId = if (isCreateAccount) {
            stringResource(R.string.authenticationScreen_createAccount_label)
        } else {
            stringResource(R.string.authenticationScreen_login_label)
        }, isLoading = isLoading, validInputs = isFieldsValid, onClick = {
            onDone()
            keyboardController?.hide()
        })
    }
}

@Composable
private fun ShowCreateAccountText() {
    Text(
        modifier = Modifier.padding(start = LocalSpacing.current.spaceSmall),
        text = stringResource(id = R.string.authenticationScreen_createAccountInfo_label),
        fontWeight = FontWeight.SemiBold,
        style = TextStyle(
            fontStyle = FontStyle.Italic
        )
    )
}

@Composable
fun SubmitButton(
    textId: String, isLoading: Boolean, validInputs: Boolean, onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .padding(LocalSpacing.current.spaceExtraSmall)
            .fillMaxWidth(),
        enabled = !isLoading && validInputs,
        shape = CircleShape
    ) {
        if (isLoading) {
            CircularProgressIndicator()
        } else {
            Text(
                text = textId, modifier = Modifier.padding(LocalSpacing.current.spaceExtraSmall)
            )
        }
    }
}

@Composable
private fun IsNewUserLabelText(isCreatingAccount: Boolean): String {
    return if (isCreatingAccount) {
        stringResource(id = R.string.authenticationScreen_alreadyHaveAnAccount_label)
    } else {
        stringResource(id = R.string.authenticationScreen_newUser_label)
    }
}

@Composable
private fun SingUpLabelText(isCreatingAccount: Boolean): String {
    return if (isCreatingAccount) {
        stringResource(id = R.string.authenticationScreen_login_label)
    } else {
        stringResource(R.string.authenticationScreen_singUp_label)
    }
}

@Preview(showBackground = true)
@Composable
fun AuthenticationScreenPreview() {
    AuthenticationScreen(onNavigate = { })
}

@Preview
@Composable
fun UserFormPreview() {
    val isVisible = true
    UserForm(
        emailState = "",
        passwordState = "",
        passwordVisibility = isVisible,
        focusRequester = FocusRequester.Default,
        isFieldsValid = false,
        onValueChanged = { _, _ ->

        },
        onDone = {

        },
        onPasswordVisibilityChanged = {

        },
        isCreateAccount = true,
        isLoading = false
    )
}

enum class FieldType {
    EMAIL, PASSWORD
}
