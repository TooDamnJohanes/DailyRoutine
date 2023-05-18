package com.dailyroutineapp.impl.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dailyroutineapp.apublic.domain.models.AuthenticationType
import com.dailyroutineapp.apublic.domain.usecases.AuthenticateUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AuthenticationScreenViewModel @Inject constructor(
    private val authenticateUserUseCase: AuthenticateUserUseCase
) : ViewModel() {

    private val _isCreatingAccount: MutableState<Boolean> = mutableStateOf(false)
    val isCreatingAccount: State<Boolean> = _isCreatingAccount

    private val _emailFieldInput: MutableState<String> = mutableStateOf("")
    val emailFieldInput: State<String> = _emailFieldInput

    private val _passwordFieldInput: MutableState<String> = mutableStateOf("")
    val passwordFieldInput: State<String> = _passwordFieldInput

    private val _isFieldsValid: MutableState<Boolean> = mutableStateOf(false)
    val isFieldsValid: State<Boolean> = _isFieldsValid

    val passwordVisibility: MutableState<Boolean> = mutableStateOf(false)
    val showErrorDialog: MutableState<Boolean> = mutableStateOf(false)

    fun changeFormType() {
        _isCreatingAccount.value = !isCreatingAccount.value
    }

    fun changeInputFieldsValue(value: String, fieldType: FieldType) {
        when (fieldType) {
            FieldType.EMAIL -> {
                changeEmailFieldValue(value)
            }

            FieldType.PASSWORD -> {
                changePasswordFieldValue(value)
            }
        }
        _isFieldsValid.value = checkIfFieldsAreValid()
    }

    fun authenticateUserWithEmailAndPassword(
        authenticationType: AuthenticationType,
        onAuthenticationSucceed: (Boolean) -> Unit
    ) {
        viewModelScope.launch {
            val isLoginValidScope =
                withContext(Dispatchers.IO) {
                    authenticateUserUseCase(
                        authenticationType = authenticationType,
                        email = _emailFieldInput.value,
                        password = _passwordFieldInput.value
                    )
                }
            showErrorDialog.value = !isLoginValidScope
            onAuthenticationSucceed(isLoginValidScope)
        }
    }

    fun onAuthenticationAlertDialogDismiss() {
        showErrorDialog.value = false
    }

    private fun changeEmailFieldValue(newValue: String) {
        _emailFieldInput.value = newValue
    }

    private fun changePasswordFieldValue(newValue: String) {
        _passwordFieldInput.value = newValue
    }

    private fun checkIfFieldsAreValid(): Boolean {
        val isEmailValid = checkIfEmailInputFieldIsValid()
        val isPasswordValid = checkIfPasswordInputFieldIsValid()
        return isEmailValid && isPasswordValid
    }

    private fun checkIfEmailInputFieldIsValid(): Boolean {
        val emailRegex = Regex(EMAIL_REGEX)
        val emailFieldInputMatchesRegex = emailRegex.matches(_emailFieldInput.value)
        return _emailFieldInput.value.isNotEmpty() && emailFieldInputMatchesRegex
    }

    private fun checkIfPasswordInputFieldIsValid(): Boolean {
        return _passwordFieldInput.value.isNotEmpty() && _passwordFieldInput.value.length >= PASSWORD_MIN_LENGTH
    }
    companion object {
        const val PASSWORD_MIN_LENGTH = 6
        const val EMAIL_REGEX = "^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*(\\.\\w{2,3})+$"
    }
}
