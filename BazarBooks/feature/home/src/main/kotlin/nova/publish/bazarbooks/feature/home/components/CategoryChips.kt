package nova.publish.bazarbooks.feature.home.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun CategoryChips(categories: List<String>, selected: String?, onSelected: (String) -> Unit) {
    Row {
        categories.forEach { category ->
            FilterChip(selected = category == selected, onClick = { onSelected(category) }, label = { Text(category) })
        }
    }
}
