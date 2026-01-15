package com.example.theeisenhowermatrixapp.auth.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.captionBarPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.theeisenhowermatrixapp.ui.theme.AccentBlue

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = hiltViewModel(),
    onRegisterSuccess: () -> Unit,
    onBackToLogin: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    Box(
        modifier = Modifier.fillMaxSize().navigationBarsPadding()
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .align(Alignment.Center),
            shape = RoundedCornerShape(20.dp),
            color = Color.Transparent,
            border = BorderStroke(1.dp, Color.Black)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Регистрация",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = AccentBlue
                )

                OutlinedTextField(
                    value = uiState.nickname,
                    onValueChange = viewModel::onNicknameChange,
                    shape = RoundedCornerShape(20),

                    label = { Text("Никнейм") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = uiState.email,
                    onValueChange = viewModel::onEmailChange,
                    shape = RoundedCornerShape(20),
                    label = { Text("Эл. почта") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = uiState.password,
                    onValueChange = viewModel::onPasswordChange,
                    label = { Text("Пароль") },
                    shape = RoundedCornerShape(20),
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = uiState.confirmPassword,
                    onValueChange = viewModel::onConfirmPasswordChange,
                    label = { Text("Повторите пароль") },
                    shape = RoundedCornerShape(20),
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )

                uiState.error?.let {
                    Text(it, color = Color.Red, fontSize = 14.sp)
                }

                Button(
                    onClick = { viewModel.register(onRegisterSuccess) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    enabled = !uiState.isLoading,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black
                    ),
                    border = BorderStroke(1.dp, Color.Black)
                ) {
                    Text("Зарегистрироваться")
                }
            }
        }

        TextButton(
            modifier = Modifier.align(Alignment.BottomCenter), onClick = onBackToLogin
        ) {
            Text(
                text = "Уже есть аккаунт? Войти", style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
