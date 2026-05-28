package nova.publish.bazarbooks.feature.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import nova.publish.bazarbooks.core.designsystem.component.BazarChipTabRow

@Composable
internal fun HomeChipTabRow(
    tabs: List<String>,
    selected: String,
    onSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    BazarChipTabRow(
        tabs = tabs,
        selected = selected,
        onSelected = onSelected,
        modifier = modifier,
    )
}
