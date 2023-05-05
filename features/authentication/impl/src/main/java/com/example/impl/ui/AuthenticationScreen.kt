@file:Suppress("OPT_IN_IS_NOT_ENABLED")

package com.example.impl.ui

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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
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
import com.example.apublic.domain.models.AuthenticationType.LOGIN
import com.example.apublic.domain.models.AuthenticationType.SINGUP
import com.example.core.R
import com.example.core.dimensions.LocalSpacing
import com.example.core.navigation.DailyRoutineScreens
import com.example.core.util.UiEvent
import com.example.impl.ui.components.AlertDialog
import com.example.impl.ui.components.DailyRoutineLogo
import com.example.impl.ui.components.EmailInput
import com.example.impl.ui.components.PasswordInput

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
    val teste = authenticationScreenViewModel.showErrorDialog.value

    val isCreatingAccountState = authenticationScreenViewModel.isCreatingAccount
    val emailFieldState = authenticationScreenViewModel.emailFieldInput.value
    val passwordFieldState = authenticationScreenViewModel.passwordFieldInput.value
    val passwordVisibility = authenticationScreenViewModel.passwordVisibility
    val isFieldsValid = authenticationScreenViewModel.isFieldsValid.value

    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            DailyRoutineLogo(logoText = stringResource(id = R.string.app_name))
            if (isCreatingAccountState.value) {
                UserForm(
                    emailState = emailFieldState,
                    passwordState = passwordFieldState,
                    isFieldsValid = isFieldsValid,
                    isLoading = false,
                    isCreateAccount = isCreatingAccountState.value,
                    passwordVisibility = passwordVisibility,
                    focusRequester = focusRequester,
                    onValueChanged = { value, type ->
                        authenticationScreenViewModel.changeInputFieldsValue(
                            value = value,
                            fieldType = type
                        )
                    },
                    onDone = {
                        authenticationScreenViewModel.authenticateUserWithEmailAndPassword(SINGUP) { authResult ->
                            if (authResult) {
                                onNavigate(UiEvent.Navigate(DailyRoutineScreens.Route.HOME))
                            }
                        }
                    }
                )
            } else {
                UserForm(
                    emailState = emailFieldState,
                    passwordState = passwordFieldState,
                    isFieldsValid = isFieldsValid,
                    isLoading = false,
                    isCreateAccount = isCreatingAccountState.value,
                    passwordVisibility = passwordVisibility,
                    focusRequester = focusRequester,
                    onValueChanged = { value, type ->
                        authenticationScreenViewModel.changeInputFieldsValue(
                            value = value,
                            fieldType = type
                        )
                    },
                    onDone = {
                        authenticationScreenViewModel.authenticateUserWithEmailAndPassword(LOGIN) { authResult ->
                            if (authResult) {
                                onNavigate(UiEvent.Navigate(DailyRoutineScreens.Route.HOME))
                            }

                        }
                    }
                )
            }
        }
        if (teste) {
            AlertDialog()
        }
        Spacer(modifier = Modifier.height(spacing.spaceMedium))
        Row(
            modifier = Modifier
                .padding(spacing.spaceMedium),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            val isNewUserLabelText = if (isCreatingAccountState.value) {
                stringResource(R.string.authenticationScreen_alreadyHaveAnAccount_label)
            } else {
                stringResource(R.string.authenticationScreen_newUser_label)
            }

            val singUpLabelText = if (isCreatingAccountState.value) {
                stringResource(id = R.string.authenticationScreen_login_label)
            } else {
                stringResource(R.string.authenticationScreen_singUp_label)
            }

            Text(text = isNewUserLabelText)
            Text(
                text = singUpLabelText,
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
@OptIn(ExperimentalComposeUiApi::class)
fun UserForm(
    emailState: String,
    passwordState: String,
    isFieldsValid: Boolean,
    isLoading: Boolean,
    isCreateAccount: Boolean,
    passwordVisibility: MutableState<Boolean>,
    focusRequester: FocusRequester,
    onValueChanged: (String, FieldType) -> Unit,
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

        PasswordInput(
            modifier = Modifier.focusRequester(focusRequester),
            passwordState = passwordState,
            onValueChanged = onValueChanged,
            labelId = stringResource(R.string.authenticationScreen_password_label),
            enabled = !isLoading,
            passwordVisibility = passwordVisibility,
            onAction = KeyboardActions {
                if (isFieldsValid) {
                    onDone()
                    keyboardController?.hide()
                } else {
                    return@KeyboardActions
                }
            }
        )

        SubmitButton(
            textId = if (isCreateAccount) {
                stringResource(R.string.authenticationScreen_createAccount_label)
            } else {
                stringResource(R.string.authenticationScreen_login_label)
            },
            isLoading = isLoading,
            validInputs = isFieldsValid,
            onClick = {
                onDone()
                keyboardController?.hide()
            }
        )
    }
}

@Composable
private fun ShowCreateAccountText() {
    Text(
        modifier = Modifier
            .padding(start = LocalSpacing.current.spaceSmall),
        text = stringResource(id = R.string.create_acct),
        fontWeight = FontWeight.SemiBold,
        style = TextStyle(
            fontStyle = FontStyle.Italic
        )
    )
}

@Composable
fun SubmitButton(
    textId: String,
    isLoading: Boolean,
    validInputs: Boolean,
    onClick: () -> Unit
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
                text = textId,
                modifier = Modifier.padding(LocalSpacing.current.spaceExtraSmall)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AuthenticationScreenPreview() {
    AuthenticationScreen(
        onNavigate = { }
    )
}

@Preview
@Composable
fun UserFormPreview() {
    val isVisible = remember {
        mutableStateOf(true)
    }
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
        isCreateAccount = true,
        isLoading = false
    )
}

enum class FieldType {
    EMAIL,
    PASSWORD
}
