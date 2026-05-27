package nova.publish.bazarbooks.feature.home.components

import androidx.compose.runtime.Composable
import nova.publish.bazarbooks.core.designsystem.component.BazarTextField

@Composable
fun SearchBar(value: String, onValueChange: (String) -> Unit) {
    BazarTextField(value, onValueChange, "Search books")
}
