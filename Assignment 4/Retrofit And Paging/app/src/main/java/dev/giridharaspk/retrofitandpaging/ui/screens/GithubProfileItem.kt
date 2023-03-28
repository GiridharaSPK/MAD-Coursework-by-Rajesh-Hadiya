package dev.giridharaspk.retrofitandpaging.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import dev.giridharaspk.retrofitandpaging.data.model.SearchResultItem

@Composable
fun GithubProfileItem(user: SearchResultItem, clickListener: (String) -> Unit) {
    Card(modifier = Modifier.background(color = Color.White)) {
        Row(
            modifier = Modifier
                .padding(6.dp)
                .fillMaxWidth()
                .clickable {
                    clickListener(user.login)
                }
        ) {
            AsyncImage(
                model = user.avatar_url,
                contentDescription = user.login,
                modifier = Modifier
                    .padding(4.dp)
                    .size(50.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    user.login ?: "",
                    fontSize = 16.sp,
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    user.html_url ?: "",
                    fontSize = 14.sp,
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }
    }
}
