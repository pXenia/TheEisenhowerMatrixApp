package com.example.theeisenhowermatrixapp.stats

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.theeisenhowermatrixapp.ui.theme.AccentBlue
import com.example.theeisenhowermatrixapp.ui.theme.GrayTextSecondary
import com.example.theeisenhowermatrixapp.ui.theme.QuadrantRed
import com.example.theeisenhowermatrixapp.ui.theme.QuadrantRedText

@Composable
fun UserHeader(
    username: String,
    email: String,
    onChangePasswordClick: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(20.dp),
        color = Color.Transparent,
        border = BorderStroke(1.dp, Color.Black)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = null,
                modifier = Modifier.size(80.dp),
                tint = AccentBlue
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(username, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(email, color = GrayTextSecondary)
            }
        }
    }

    Button(
        modifier = Modifier.fillMaxWidth().height(52.dp),
        onClick = onChangePasswordClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Black,
            contentColor = Color.White
        ),
        border = BorderStroke(1.dp, Color.Black)
    ) {
        Text("Сменить пароль")
    }

    Button(
        modifier = Modifier.fillMaxWidth().height(52.dp),
        onClick = onChangePasswordClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = QuadrantRed,
            contentColor = QuadrantRedText
        ),
        border = BorderStroke(1.dp, Color.Black)
    ) {
        Text("Выйти из аккаунта")
    }
}

