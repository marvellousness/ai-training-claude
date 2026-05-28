package nova.publish.bazarbooks.feature.home

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import nova.publish.bazarbooks.core.designsystem.component.BazarErrorBanner
import nova.publish.bazarbooks.core.designsystem.component.BazarLoading
import nova.publish.bazarbooks.core.designsystem.theme.BazarDimensions
import nova.publish.bazarbooks.core.designsystem.theme.BazarPalette
import nova.publish.bazarbooks.core.designsystem.theme.BazarSpacing
import nova.publish.bazarbooks.feature.home.model.AuthorUiModel
import nova.publish.bazarbooks.feature.home.model.BookUiModel
import nova.publish.bazarbooks.feature.home.model.VendorUiModel

@Composable
internal fun HomeFeed(
    state: HomeState,
    navigation: HomeNavigationCallbacks,
) {
    LazyColumn(
        contentPadding = PaddingValues(
            start = BazarDimensions.HorizontalPadding,
            top = BazarSpacing.Lg,
            end = BazarDimensions.HorizontalPadding,
            bottom = BazarSpacing.Xl,
        ),
        verticalArrangement = Arrangement.spacedBy(BazarSpacing.Xl),
        modifier = Modifier.fillMaxSize().background(BazarPalette.White),
    ) {
        item {
            HomeHeader(navigation.notifications, navigation.search)
        }
        item {
            OfferBanner(state)
        }
        item {
            BookSection("Top of Week", state.topOfWeek, navigation.openBook)
        }
        item {
            VendorPreview(
                vendors = state.vendors,
                onSeeAll = navigation.openVendors,
                onVendor = { navigation.openVendors() },
            )
        }
        item {
            AuthorPreview(
                authors = state.authors,
                onSeeAll = navigation.openAuthors,
                onAuthor = navigation.openAuthor,
            )
        }
        state.errorMessage?.let {
            item { BazarErrorBanner(message = it) }
        }
        if (state.isLoading) {
            item { BazarLoading() }
        }
    }
}

@Composable
private fun HomeHeader(
    onNotifications: () -> Unit,
    onSearch: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().height(48.dp),
    ) {
        IconButton(onClick = onSearch, modifier = Modifier.size(40.dp)) {
            Icon(Icons.Filled.Search, contentDescription = "Search", tint = BazarPalette.Gray900)
        }
        Text(
            text = "Home",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = BazarPalette.Gray900,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f),
        )
        Box(contentAlignment = Alignment.TopEnd, modifier = Modifier.size(40.dp)) {
            IconButton(onClick = onNotifications, modifier = Modifier.size(40.dp)) {
                Icon(Icons.Filled.Notifications, contentDescription = "Notifications", tint = BazarPalette.Gray900)
            }
            Box(
                modifier = Modifier
                    .padding(top = 9.dp, end = 9.dp)
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(BazarPalette.Red),
            )
        }
    }
}

@Composable
private fun OfferBanner(state: HomeState) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(BazarSpacing.Sm)) {
        Card(
            colors = CardDefaults.cardColors(containerColor = BazarPalette.Primary50),
            shape = RoundedCornerShape(BazarDimensions.CardRadius),
            modifier = Modifier.fillMaxWidth().height(146.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxSize().padding(start = 23.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(BazarSpacing.Sm), modifier = Modifier.weight(1f)) {
                    Column {
                        Text(
                            state.offer.title,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = BazarPalette.Gray900,
                        )
                        Text(
                            state.offer.subtitle,
                            style = MaterialTheme.typography.bodyMedium,
                            color = BazarPalette.Gray900,
                        )
                    }
                    Text(
                        text = state.offer.actionLabel,
                        color = BazarPalette.White,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .width(118.dp)
                            .height(36.dp)
                            .clip(RoundedCornerShape(40.dp))
                            .background(BazarPalette.Primary500)
                            .padding(top = 8.dp),
                    )
                }
                PromoCover(modifier = Modifier.padding(end = BazarSpacing.Xl))
            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(BazarSpacing.Xs), verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.size(8.dp).clip(CircleShape).background(BazarPalette.Primary500))
            Box(Modifier.size(4.dp).clip(CircleShape).background(BazarPalette.Primary100))
            Box(Modifier.size(4.dp).clip(CircleShape).background(BazarPalette.Primary100))
        }
    }
}

@Composable
private fun PromoCover(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .width(99.dp)
            .height(145.dp)
            .clip(RoundedCornerShape(3.dp))
            .background(Color(0xFF244C34)),
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(BazarSpacing.Xs)) {
            Text("RICK RIORDAN", style = MaterialTheme.typography.labelSmall, color = Color(0xFFFFD25A))
            Text("APOLLO", style = MaterialTheme.typography.titleLarge, color = Color(0xFFFFD25A), fontWeight = FontWeight.Bold)
            Box(Modifier.size(44.dp).clip(CircleShape).background(Color(0xFFB7D49B)))
            Text("THE BURNING MAZE", style = MaterialTheme.typography.labelSmall, color = BazarPalette.White)
        }
    }
}

@Composable
private fun BookSection(title: String, books: List<BookUiModel>, onBook: (String) -> Unit) {
    SectionTitle(title = title, action = "See all", onAction = {})
    LazyRow(horizontalArrangement = Arrangement.spacedBy(BazarSpacing.Md)) {
        items(books) { book ->
            HomeBookTile(book = book, onClick = { onBook(book.id) })
        }
    }
}

@Composable
private fun HomeBookTile(book: BookUiModel, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        modifier = Modifier.width(127.dp),
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(BazarSpacing.Sm), modifier = Modifier.background(BazarPalette.White)) {
            BookCover(book)
            Column(verticalArrangement = Arrangement.spacedBy(BazarSpacing.Xs)) {
                Text(
                    text = book.title,
                    style = MaterialTheme.typography.bodyMedium,
                    color = BazarPalette.Gray900,
                    fontWeight = FontWeight.Medium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = book.price,
                    style = MaterialTheme.typography.labelLarge,
                    color = BazarPalette.Primary500,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}

@Composable
private fun BookCover(book: BookUiModel) {
    val colors = coverColors(book.id)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .clip(RoundedCornerShape(BazarDimensions.CardRadius))
            .background(colors.first)
            .padding(BazarSpacing.Md),
    ) {
        Text(
            text = coverTitle(book),
            style = MaterialTheme.typography.titleMedium,
            color = colors.second,
            fontWeight = FontWeight.Bold,
            lineHeight = MaterialTheme.typography.titleMedium.lineHeight,
        )
    }
}

@Composable
private fun VendorPreview(vendors: List<VendorUiModel>, onSeeAll: () -> Unit, onVendor: (String) -> Unit) {
    SectionTitle(title = "Best Vendors", action = "See all", onAction = onSeeAll)
    LazyRow(horizontalArrangement = Arrangement.spacedBy(BazarSpacing.Sm)) {
        items(vendors.take(6)) { vendor ->
            HomeVendorLogo(vendor = vendor, onClick = { onVendor(vendor.id) })
        }
    }
}

@Composable
private fun HomeVendorLogo(vendor: VendorUiModel, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        colors = CardDefaults.cardColors(containerColor = BazarPalette.Gray50),
        shape = RoundedCornerShape(BazarDimensions.CardRadius),
        modifier = Modifier.size(80.dp),
    ) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize().padding(BazarSpacing.Sm)) {
            Text(
                text = vendorLogoText(vendor.name),
                style = MaterialTheme.typography.labelMedium,
                color = vendorLogoColor(vendor.name),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Composable
private fun AuthorPreview(authors: List<AuthorUiModel>, onSeeAll: () -> Unit, onAuthor: (String) -> Unit) {
    SectionTitle(title = "Authors", action = "See all", onAction = onSeeAll)
    LazyRow(horizontalArrangement = Arrangement.spacedBy(29.dp)) {
        items(authors.take(5)) { author ->
            HomeAuthorItem(author = author, onClick = { onAuthor(author.id) })
        }
    }
}

@Composable
private fun HomeAuthorItem(author: AuthorUiModel, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        modifier = Modifier.width(98.dp),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(BazarSpacing.Sm),
            modifier = Modifier.background(BazarPalette.White),
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(96.dp)
                    .clip(CircleShape)
                    .background(authorColor(author.id)),
            ) {
                Text(
                    text = author.name.split(" ").mapNotNull { it.firstOrNull()?.toString() }.take(2).joinToString(""),
                    style = MaterialTheme.typography.titleLarge,
                    color = BazarPalette.White,
                    fontWeight = FontWeight.Bold,
                )
            }
            Text(
                text = author.name,
                style = MaterialTheme.typography.bodyMedium,
                color = BazarPalette.Gray900,
                fontWeight = FontWeight.Medium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth(),
            )
            Text(
                text = author.role,
                style = MaterialTheme.typography.bodySmall,
                color = BazarPalette.Gray500,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Composable
private fun SectionTitle(title: String, action: String, onAction: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
        Text(title, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
        TextButton(onClick = onAction) { Text(action) }
    }
}

private fun coverTitle(book: BookUiModel): String = when (book.id) {
    "kite-runner" -> "The\nKite\nRunner"
    "da-vinci-code" -> "THE\nDA VINCI\nCODE"
    "good-sister" -> "THE\nGOOD\nSISTER"
    "the-waiting" -> "THE\nWAITING"
    "bright-young-women" -> "BRIGHT\nYOUNG\nWOMEN"
    else -> book.title
}

private fun coverColors(bookId: String): Pair<Color, Color> = when (bookId) {
    "kite-runner" -> Color(0xFFD9ECF4) to Color(0xFFB4363A)
    "da-vinci-code" -> Color(0xFFFF3B1F) to BazarPalette.Gray900
    "good-sister" -> Color(0xFF7C1F33) to BazarPalette.White
    "the-waiting" -> Color(0xFF292929) to BazarPalette.White
    "bright-young-women" -> Color(0xFFF6C5D4) to BazarPalette.Gray900
    else -> BazarPalette.Primary100 to BazarPalette.Primary700
}
