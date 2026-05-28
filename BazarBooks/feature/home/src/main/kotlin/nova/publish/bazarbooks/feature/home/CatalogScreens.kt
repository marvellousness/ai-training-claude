package nova.publish.bazarbooks.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.AssistChip
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import nova.publish.bazarbooks.core.designsystem.component.BazarBookCard
import nova.publish.bazarbooks.core.designsystem.component.BazarEmptyState
import nova.publish.bazarbooks.core.designsystem.component.BazarPrimaryButton
import nova.publish.bazarbooks.core.designsystem.component.BazarQuantityStepper
import nova.publish.bazarbooks.core.designsystem.component.BazarRatingRow
import nova.publish.bazarbooks.core.designsystem.component.BazarSearchField
import nova.publish.bazarbooks.core.designsystem.component.BazarSecondaryButton
import nova.publish.bazarbooks.core.designsystem.component.BazarTextField
import nova.publish.bazarbooks.core.designsystem.component.BazarTopBar
import nova.publish.bazarbooks.core.designsystem.theme.BazarDimensions
import nova.publish.bazarbooks.core.designsystem.theme.BazarSpacing

@Composable
internal fun CategoryScreen(state: HomeState, onIntent: (HomeIntent) -> Unit, onBook: (String) -> Unit, onSearch: () -> Unit) {
    CatalogListScaffold(title = "Category", onBack = null) {
        item {
            BazarSearchField(value = "", onValueChange = {}, placeholder = "Search", modifier = Modifier.fillMaxWidth())
            TextButton(onClick = onSearch) { Text("Search") }
        }
        item {
            ChipRow(FigmaCategoryTabs, state.selectedCategory) { onIntent(HomeIntent.CategorySelected(it)) }
        }
        items(state.categoryBooks) { book ->
            BazarBookCard(book.title, book.author, book.price, book.imageUrl, onClick = { onBook(book.id) })
        }
        if (state.categoryBooks.isEmpty()) {
            item { BazarEmptyState(title = "No books found", message = "Try another category.") }
        }
    }
}

@Composable
internal fun SearchScreen(state: HomeState, onIntent: (HomeIntent) -> Unit, onBook: (String) -> Unit, onBack: () -> Unit) {
    CatalogListScaffold(title = "Search", onBack = onBack) {
        item {
            BazarSearchField(value = state.searchQuery, onValueChange = { onIntent(HomeIntent.SearchQueryChanged(it)) }, placeholder = "Search")
            BazarPrimaryButton(text = "Search", onClick = { onIntent(HomeIntent.SubmitSearch) }, modifier = Modifier.padding(top = BazarSpacing.Md))
        }
        item {
            Text("Recent Searches", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
            Row(horizontalArrangement = Arrangement.spacedBy(BazarSpacing.Sm), modifier = Modifier.horizontalScroll(rememberScrollState())) {
                state.recentSearches.forEach { query ->
                    AssistChip(onClick = { onIntent(HomeIntent.SearchQueryChanged(query)); onIntent(HomeIntent.SubmitSearch) }, label = { Text(query) })
                }
            }
        }
        items(state.searchResults) { book ->
            BazarBookCard(book.title, book.author, book.price, book.imageUrl, onClick = { onBook(book.id) })
        }
        if (state.searchResults.isEmpty()) {
            item { BazarEmptyState(title = "No results", message = "Try another search.") }
        }
    }
}

@Composable
internal fun AuthorDetailScreen(state: HomeState, onBook: (String) -> Unit, onBack: () -> Unit) {
    val author = state.selectedAuthor
    CatalogListScaffold(title = "Authors", onBack = onBack) {
        if (author != null) {
            item {
                Column(verticalArrangement = Arrangement.spacedBy(BazarSpacing.Sm)) {
                    Text(author.name, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                    Text(author.role, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    BazarRatingRow(rating = "4.0")
                    Text("About", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                    Text(author.bio)
                    Text("Products", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                }
            }
            items(state.authorProducts) { book ->
                BazarBookCard(book.title, book.author, book.price, book.imageUrl, onClick = { onBook(book.id) })
            }
        }
    }
}

@Composable
internal fun BookDetailScreen(state: HomeState, onIntent: (HomeIntent) -> Unit, onBack: () -> Unit) {
    val book = state.selectedBook
    CatalogListScaffold(title = book?.title ?: "Book Detail", onBack = onBack) {
        if (book != null) {
            item {
                Column(verticalArrangement = Arrangement.spacedBy(BazarSpacing.Md)) {
                    BazarBookCard(book.title, book.author, book.price, book.imageUrl, onClick = {})
                    Text(book.description)
                    Text("Review", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                    BazarRatingRow(rating = book.rating)
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                        BazarQuantityStepper(
                            quantity = state.detailQuantity,
                            onDecrease = { onIntent(HomeIntent.DecreaseQuantity) },
                            onIncrease = { onIntent(HomeIntent.IncreaseQuantity) },
                        )
                        Spacer(Modifier.weight(1f))
                        IconButton(onClick = { onIntent(HomeIntent.ToggleWishlist) }) {
                            Icon(if (book.isWishlisted) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder, contentDescription = "Wishlist")
                        }
                    }
                    BazarSecondaryButton(text = "Continue shopping", onClick = { onIntent(HomeIntent.ContinueShopping) })
                    BazarPrimaryButton(text = "View cart", onClick = { onIntent(HomeIntent.AddToCart); onIntent(HomeIntent.ViewCart) })
                }
            }
        }
    }
}

@Composable
internal fun LocationDetailScreen(state: HomeState, onIntent: (HomeIntent) -> Unit, onBack: () -> Unit) {
    CatalogListScaffold(title = "Location", onBack = onBack) {
        item {
            val location = state.selectedLocation
            Column(verticalArrangement = Arrangement.spacedBy(BazarSpacing.Md)) {
                Text("Detail Address", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                Text(location?.line1?.ifBlank { "Utama Street No.20" } ?: "Utama Street No.20")
                Text(location?.fullAddress?.ifBlank { "Dumbo Street No.20, Dumbo, New York 10001, United States of America" } ?: "Dumbo Street No.20, Dumbo, New York 10001, United States of America")
                Text("Save Address As", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                Row(horizontalArrangement = Arrangement.spacedBy(BazarSpacing.Sm)) {
                    AssistChip(onClick = {}, label = { Text("Home") })
                    AssistChip(onClick = {}, label = { Text("Offices") })
                }
                BazarPrimaryButton(text = "Confirmation", onClick = { onIntent(HomeIntent.ShowLocationForm) })
            }
        }
    }
}

@Composable
internal fun LocationFormScreen(state: HomeState, onIntent: (HomeIntent) -> Unit, onBack: () -> Unit) {
    val form = state.locationForm
    CatalogListScaffold(title = "Location", onBack = onBack) {
        item { LocationField("Name", "name", form.name, state, onIntent) }
        item { LocationField("Phone", "phone", form.phone, state, onIntent) }
        item { LocationField("Governorate", "governorate", form.governorate, state, onIntent) }
        item { LocationField("City", "city", form.city, state, onIntent) }
        item { LocationField("Block", "block", form.block, state, onIntent) }
        item { LocationField("Street name /number", "street", form.street, state, onIntent) }
        item { LocationField("Building name/number", "building", form.building, state, onIntent) }
        item { LocationField("Floor (option)", "floor", form.floor, state, onIntent) }
        item { LocationField("Flat(option)", "flat", form.flat, state, onIntent) }
        item { LocationField("Avenue (option)", "avenue", form.avenue, state, onIntent) }
        item { BazarPrimaryButton(text = "Confirmation", onClick = { onIntent(HomeIntent.ConfirmLocation) }) }
    }
}

@Composable
private fun LocationField(label: String, field: String, value: String, state: HomeState, onIntent: (HomeIntent) -> Unit) {
    BazarTextField(
        value = value,
        onValueChange = { onIntent(HomeIntent.LocationFieldChanged(field, it)) },
        label = label,
        error = state.locationErrors[field],
    )
}

@Composable
private fun ChipRow(items: List<String>, selected: String, onSelected: (String) -> Unit) {
    Row(horizontalArrangement = Arrangement.spacedBy(BazarSpacing.Sm), modifier = Modifier.horizontalScroll(rememberScrollState())) {
        items.forEach { item ->
            FilterChip(selected = selected == item, onClick = { onSelected(item) }, label = { Text(item) })
        }
    }
}

@Composable
private fun CatalogListScaffold(
    title: String,
    onBack: (() -> Unit)?,
    content: androidx.compose.foundation.lazy.LazyListScope.() -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        BazarTopBar(title = title, onBack = onBack)
        LazyColumn(
            contentPadding = PaddingValues(horizontal = BazarDimensions.ContentPadding, vertical = BazarSpacing.Lg),
            verticalArrangement = Arrangement.spacedBy(BazarSpacing.Md),
            modifier = Modifier.fillMaxSize(),
            content = content,
        )
    }
}
