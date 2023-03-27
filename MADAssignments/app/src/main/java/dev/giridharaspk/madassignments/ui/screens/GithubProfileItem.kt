package dev.giridharaspk.madassignments.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import dev.giridharaspk.madassignments.data.model.GithubUser
import dev.giridharaspk.madassignments.data.model.SearchResultItem

@Composable
fun GithubProfileItem(user: SearchResultItem, clickListener: (String) -> Unit) {
    Card() {
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
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    user.login ?: "",
                    fontSize = 16.sp,
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    user.url ?: "",
                    fontSize = 14.sp,
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }
    }
}
