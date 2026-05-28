package nova.publish.bazarbooks.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import nova.publish.bazarbooks.core.designsystem.component.BazarChipTabRow
import nova.publish.bazarbooks.core.designsystem.theme.BazarDimensions
import nova.publish.bazarbooks.core.designsystem.theme.BazarPalette
import nova.publish.bazarbooks.core.designsystem.theme.BazarSpacing
import nova.publish.bazarbooks.feature.home.model.AuthorUiModel

@Composable
internal fun AuthorListScreen(state: HomeState, onIntent: (HomeIntent) -> Unit, onAuthor: (String) -> Unit, onBack: () -> Unit) {
    val authors = state.authors.filterAuthorsByTab(state.selectedAuthorTab)
    LazyColumn(
        contentPadding = PaddingValues(start = BazarSpacing.Lg, end = BazarSpacing.Lg, bottom = BazarSpacing.Xl),
        verticalArrangement = Arrangement.spacedBy(BazarSpacing.Lg),
        modifier = Modifier.fillMaxSize().background(BazarPalette.White),
    ) {
        item {
            AuthorTopBar(onBack = onBack)
        }
        item {
            AuthorIntroBlock()
        }
        item {
            AuthorTabRow(selected = state.selectedAuthorTab, onSelected = { onIntent(HomeIntent.AuthorTabSelected(it)) })
        }
        items(authors) { author ->
            AuthorListItem(
                author = author,
                onClick = { onAuthor(author.id) },
                modifier = Modifier.padding(horizontal = BazarSpacing.Xs),
            )
        }
    }
}

@Composable
private fun AuthorTopBar(onBack: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().height(56.dp),
    ) {
        IconButton(onClick = onBack, modifier = Modifier.size(40.dp)) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = BazarPalette.Gray900)
        }
        Text(
            text = "Authors",
            style = MaterialTheme.typography.titleLarge,
            color = BazarPalette.Gray900,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f),
        )
        IconButton(onClick = {}, modifier = Modifier.size(40.dp)) {
            Icon(Icons.Filled.Search, contentDescription = "Search", tint = BazarPalette.Gray900)
        }
    }
}

@Composable
private fun AuthorIntroBlock() {
    Column(
        verticalArrangement = Arrangement.spacedBy(BazarSpacing.Xs),
        modifier = Modifier.padding(start = BazarSpacing.Sm, top = BazarSpacing.Md),
    ) {
        Text(
            text = "Check the authors",
            style = MaterialTheme.typography.bodyLarge,
            color = BazarPalette.Gray500,
        )
        Text(
            text = "Authors",
            style = MaterialTheme.typography.titleLarge,
            color = BazarPalette.Primary500,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
private fun AuthorTabRow(selected: String, onSelected: (String) -> Unit) {
    BazarChipTabRow(tabs = FigmaAuthorTabs, selected = selected, onSelected = onSelected)
}

@Composable
private fun AuthorListItem(author: AuthorUiModel, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(BazarSpacing.Md),
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(BazarDimensions.CardRadius))
            .clickable(onClick = onClick)
            .padding(vertical = BazarSpacing.Xs),
    ) {
        AuthorAvatar(author = author)
        Column(verticalArrangement = Arrangement.spacedBy(BazarSpacing.Xs), modifier = Modifier.weight(1f)) {
            Text(
                text = author.name,
                style = MaterialTheme.typography.titleMedium,
                color = BazarPalette.Gray900,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = author.bio,
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF66707A),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Composable
private fun AuthorAvatar(author: AuthorUiModel) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(68.dp)
            .clip(CircleShape)
            .background(authorColor(author.id)),
    ) {
        Text(
            text = authorInitials(author.name),
            style = MaterialTheme.typography.titleMedium,
            color = BazarPalette.White,
            fontWeight = FontWeight.Bold,
        )
    }
}

private fun List<AuthorUiModel>.filterAuthorsByTab(tab: String) = when (tab) {
    "All" -> this
    "Poets" -> filter { it.role.contains("Poet", ignoreCase = true) }
    "Playwrights" -> filter { it.role.contains("Playwright", ignoreCase = true) }
    "Novelists" -> filter { it.role.contains("Novelist", ignoreCase = true) }
    "Journalists" -> filter { it.role.contains("Journalist", ignoreCase = true) }
    else -> this
}

private fun authorInitials(name: String): String =
    name.split(" ")
        .mapNotNull { it.firstOrNull()?.uppercaseChar()?.toString() }
        .take(2)
        .joinToString("")
