package nova.publish.bazarbooks.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import nova.publish.bazarbooks.core.designsystem.component.BazarBookCard

@Composable
fun HomeScreen(onCart: () -> Unit, onProfile: () -> Unit, onNotifications: () -> Unit) {
    val books = listOf(
        Triple("Clean Kotlin", "A. Reader", "$18.99"),
        Triple("Compose Craft", "N. Publisher", "$21.99"),
        Triple("Bookshop Days", "M. Stone", "$14.99"),
    )
    LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxSize()) {
        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Bazar Books")
                Button(onClick = onCart) { Text("Cart") }
                Button(onClick = onProfile) { Text("Profile") }
                Button(onClick = onNotifications) { Text("Notifications") }
            }
        }
        items(books.size) { index ->
            val book = books[index]
            BazarBookCard(title = book.first, author = book.second, price = book.third, imageUrl = "", onClick = {})
        }
    }
}
