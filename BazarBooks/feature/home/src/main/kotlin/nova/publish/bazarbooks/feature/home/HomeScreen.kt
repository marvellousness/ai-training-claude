package nova.publish.bazarbooks.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
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
import androidx.compose.ui.unit.Dp
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import nova.publish.bazarbooks.core.designsystem.component.BazarBookCard
import nova.publish.bazarbooks.core.designsystem.component.BazarEmptyState
import nova.publish.bazarbooks.core.designsystem.component.BazarErrorBanner
import nova.publish.bazarbooks.core.designsystem.component.BazarLoading
import nova.publish.bazarbooks.core.designsystem.component.BazarPrimaryButton
import nova.publish.bazarbooks.core.designsystem.component.BazarQuantityStepper
import nova.publish.bazarbooks.core.designsystem.component.BazarRatingRow
import nova.publish.bazarbooks.core.designsystem.component.BazarSearchField
import nova.publish.bazarbooks.core.designsystem.component.BazarSecondaryButton
import nova.publish.bazarbooks.core.designsystem.component.BazarTextField
import nova.publish.bazarbooks.core.designsystem.component.BazarTopBar
import nova.publish.bazarbooks.core.designsystem.component.BazarVendorCard
import nova.publish.bazarbooks.core.designsystem.theme.BazarDimensions
import nova.publish.bazarbooks.core.designsystem.theme.BazarPalette
import nova.publish.bazarbooks.core.designsystem.theme.BazarSpacing
import nova.publish.bazarbooks.feature.home.model.AuthorUiModel
import nova.publish.bazarbooks.feature.home.model.BookUiModel
import nova.publish.bazarbooks.feature.home.model.LocationUiModel
import nova.publish.bazarbooks.feature.home.model.VendorUiModel

@Composable
fun HomeScreen(
    state: HomeState,
    onIntent: (HomeIntent) -> Unit,
    onCart: () -> Unit,
    onProfile: () -> Unit,
    onNotifications: () -> Unit,
    onBook: (String) -> Unit,
    onVendors: () -> Unit,
    onAuthors: () -> Unit,
    onAuthor: (String) -> Unit,
    onSearch: () -> Unit,
    onLocation: () -> Unit,
    onBack: () -> Unit,
) {
    when (state.surface) {
        HomeSurface.Home -> HomeFeed(state, onIntent, onCart, onProfile, onNotifications, onBook, onVendors, onAuthors, onSearch, onLocation)
        HomeSurface.Category -> CategoryScreen(state, onIntent, onBook, onSearch)
        HomeSurface.Search -> SearchScreen(state, onIntent, onBook, onBack)
        HomeSurface.Vendors -> VendorListScreen(state, onIntent, onBack)
        HomeSurface.Authors -> AuthorListScreen(state, onIntent, onAuthor, onBack)
        HomeSurface.AuthorDetail -> AuthorDetailScreen(state, onBook, onBack)
        HomeSurface.BookDetail -> BookDetailScreen(state, onIntent, onBack)
        HomeSurface.LocationDetail -> LocationDetailScreen(state, onIntent, onBack)
        HomeSurface.LocationForm -> LocationFormScreen(state, onIntent, onBack)
    }
}

@Composable
private fun HomeFeed(
    state: HomeState,
    onIntent: (HomeIntent) -> Unit,
    onCart: () -> Unit,
    onProfile: () -> Unit,
    onNotifications: () -> Unit,
    onBook: (String) -> Unit,
    onVendors: () -> Unit,
    onAuthors: () -> Unit,
    onSearch: () -> Unit,
    onLocation: () -> Unit,
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
            HomeHeader(onNotifications, onSearch)
        }
        item {
            OfferBanner(state)
        }
        item {
            BookSection("Top of Week", state.topOfWeek, onBook)
        }
        item {
            VendorPreview(
                vendors = state.vendors,
                onSeeAll = { onIntent(HomeIntent.ShowVendors) },
                onVendor = { onIntent(HomeIntent.ShowVendors) },
            )
        }
        item {
            AuthorPreview(
                authors = state.authors,
                onSeeAll = { onIntent(HomeIntent.ShowAuthors) },
                onAuthor = { authorId -> onIntent(HomeIntent.OpenAuthor(authorId)) },
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
private fun CategoryScreen(state: HomeState, onIntent: (HomeIntent) -> Unit, onBook: (String) -> Unit, onSearch: () -> Unit) {
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
private fun SearchScreen(state: HomeState, onIntent: (HomeIntent) -> Unit, onBook: (String) -> Unit, onBack: () -> Unit) {
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
private fun VendorListScreen(state: HomeState, onIntent: (HomeIntent) -> Unit, onBack: () -> Unit) {
    val vendors = state.vendors.filterVendorsByTab(state.selectedVendorTab)
    LazyColumn(
        contentPadding = PaddingValues(start = BazarSpacing.Lg, end = BazarSpacing.Lg, bottom = BazarSpacing.Xl),
        verticalArrangement = Arrangement.spacedBy(BazarSpacing.Lg),
        modifier = Modifier.fillMaxSize().background(BazarPalette.White),
    ) {
        item {
            VendorTopBar(onBack = onBack)
        }
        item {
            Column(
                verticalArrangement = Arrangement.spacedBy(BazarSpacing.Xs),
                modifier = Modifier.padding(start = BazarSpacing.Sm, top = BazarSpacing.Md),
            ) {
                Text(
                    text = "Our Vendors",
                    style = MaterialTheme.typography.bodyLarge,
                    color = BazarPalette.Gray500,
                )
                Text(
                    text = "Vendors",
                    style = MaterialTheme.typography.titleLarge,
                    color = BazarPalette.Primary500,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
        item {
            VendorTabRow(selected = state.selectedVendorTab, onSelected = { onIntent(HomeIntent.VendorTabSelected(it)) })
        }
        items(vendors.chunked(3)) { rowVendors ->
            Row(horizontalArrangement = Arrangement.spacedBy(BazarSpacing.Md), modifier = Modifier.fillMaxWidth()) {
                rowVendors.forEach { vendor ->
                    VendorGridItem(vendor = vendor, modifier = Modifier.weight(1f))
                }
                repeat(3 - rowVendors.size) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
private fun VendorTopBar(onBack: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().height(56.dp),
    ) {
        IconButton(onClick = onBack, modifier = Modifier.size(40.dp)) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = BazarPalette.Gray900)
        }
        Text(
            text = "Vendors",
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
private fun VendorTabRow(selected: String, onSelected: (String) -> Unit) {
    Column {
        Row(
            horizontalArrangement = Arrangement.spacedBy(BazarSpacing.Xl),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = BazarSpacing.Sm, vertical = BazarSpacing.Lg),
        ) {
            FigmaVendorTabs.forEach { tab ->
                Text(
                    text = tab,
                    style = if (selected == tab) MaterialTheme.typography.titleMedium else MaterialTheme.typography.bodyLarge,
                    color = if (selected == tab) BazarPalette.Gray900 else BazarPalette.Gray500,
                    fontWeight = if (selected == tab) FontWeight.Bold else FontWeight.Normal,
                    maxLines = 1,
                    modifier = Modifier
                        .clip(RoundedCornerShape(BazarSpacing.Xs))
                        .clickable { onSelected(tab) }
                        .padding(end = BazarSpacing.Xs),
                )
            }
        }
        Box(
            modifier = Modifier
                .padding(start = selectedVendorUnderlineStart(selected))
                .width(24.dp)
                .height(2.dp)
                .clip(RoundedCornerShape(2.dp))
                .background(BazarPalette.Primary500),
        )
    }
}

@Composable
private fun VendorGridItem(vendor: VendorUiModel, modifier: Modifier = Modifier) {
    Column(verticalArrangement = Arrangement.spacedBy(BazarSpacing.Sm), modifier = modifier.width(101.dp)) {
        Card(
            colors = CardDefaults.cardColors(containerColor = BazarPalette.Gray50),
            shape = RoundedCornerShape(7.dp),
            modifier = Modifier.fillMaxWidth().height(101.dp),
        ) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize().padding(BazarSpacing.Sm)) {
                VendorLogoMark(vendor.name)
            }
        }
        Text(
            text = vendor.name,
            style = MaterialTheme.typography.bodyLarge,
            color = BazarPalette.Gray900,
            fontWeight = FontWeight.Medium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        VendorRating(vendorName = vendor.name)
    }
}

@Composable
private fun VendorLogoMark(name: String) {
    when (name) {
        "Kuromi" -> Text("Kuromi", color = Color(0xFFDB87B9), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        "GooDay" -> Text("GooDay", color = Color(0xFFE95220), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        "Crane & Co" -> Text("CRANE & CO.", color = BazarPalette.Gray600, style = MaterialTheme.typography.labelLarge, textAlign = TextAlign.Center)
        "Jstor" -> Text("JSTOR", color = Color(0xFF990000), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        "Peloton" -> Text("Peloton", color = Color(0xFFFE3A2B), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        "Haymarket" -> Text("H", color = Color(0xFF103B66), style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        "Wattpad" -> Text("Wattpad", color = Color(0xFFE95220), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        else -> Text(name, color = vendorLogoColor(name), style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
    }
}

@Composable
private fun VendorRating(vendorName: String) {
    val rating = vendorStarRating(vendorName)
    Row(horizontalArrangement = Arrangement.spacedBy(4.dp), verticalAlignment = Alignment.CenterVertically) {
        repeat(5) { index ->
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = null,
                tint = if (index < rating) BazarPalette.Yellow else BazarPalette.Gray900,
                modifier = Modifier.size(16.dp),
            )
        }
    }
}

@Composable
private fun AuthorListScreen(state: HomeState, onIntent: (HomeIntent) -> Unit, onAuthor: (String) -> Unit, onBack: () -> Unit) {
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
    Column {
        Row(
            horizontalArrangement = Arrangement.spacedBy(BazarSpacing.Xl),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = BazarSpacing.Sm, vertical = BazarSpacing.Lg),
        ) {
            FigmaAuthorTabs.forEach { tab ->
                Text(
                    text = tab,
                    style = if (selected == tab) MaterialTheme.typography.titleMedium else MaterialTheme.typography.bodyLarge,
                    color = if (selected == tab) BazarPalette.Gray900 else BazarPalette.Gray500,
                    fontWeight = if (selected == tab) FontWeight.Bold else FontWeight.Normal,
                    maxLines = 1,
                    modifier = Modifier
                        .clip(RoundedCornerShape(BazarSpacing.Xs))
                        .clickable { onSelected(tab) }
                        .padding(end = BazarSpacing.Xs),
                )
            }
        }
        Box(
            modifier = Modifier
                .padding(start = selectedAuthorUnderlineStart(selected))
                .width(24.dp)
                .height(2.dp)
                .clip(RoundedCornerShape(2.dp))
                .background(BazarPalette.Primary500),
        )
    }
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

@Composable
private fun AuthorDetailScreen(state: HomeState, onBook: (String) -> Unit, onBack: () -> Unit) {
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
private fun BookDetailScreen(state: HomeState, onIntent: (HomeIntent) -> Unit, onBack: () -> Unit) {
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
private fun LocationDetailScreen(state: HomeState, onIntent: (HomeIntent) -> Unit, onBack: () -> Unit) {
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
private fun LocationFormScreen(state: HomeState, onIntent: (HomeIntent) -> Unit, onBack: () -> Unit) {
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
private fun SectionTitle(title: String, action: String, onAction: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
        Text(title, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
        TextButton(onClick = onAction) { Text(action) }
    }
}

@Composable
private fun ChipRow(items: List<String>, selected: String, onSelected: (String) -> Unit) {
    Row(horizontalArrangement = Arrangement.spacedBy(BazarSpacing.Sm), modifier = Modifier.horizontalScroll(rememberScrollState())) {
        items.forEach { item ->
            FilterChip(selected = selected == item, onClick = { onSelected(item) }, label = { Text(item) })
        }
    }
}

private fun List<VendorUiModel>.filterVendorsByTab(tab: String) = if (tab == "All" || tab == "Our Vendors") this else filter { it.category == tab }

private fun List<AuthorUiModel>.filterAuthorsByTab(tab: String) = when (tab) {
    "All" -> this
    "Poets" -> filter { it.role.contains("Poet", ignoreCase = true) }
    "Playwrights" -> filter { it.role.contains("Playwright", ignoreCase = true) }
    "Novelists" -> filter { it.role.contains("Novelist", ignoreCase = true) }
    "Journalists" -> filter { it.role.contains("Journalist", ignoreCase = true) }
    else -> this
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

private fun vendorLogoText(name: String): String = when (name) {
    "Warehouse" -> "WS\nwarehouse"
    "Kuromi" -> "Kuromi"
    "GooDay" -> "GooDay"
    "Crane & Co" -> "CRANE & CO."
    else -> name
}

private fun vendorLogoColor(name: String): Color = when (name) {
    "Kuromi" -> Color(0xFFE91E63)
    "GooDay" -> Color(0xFFFF6D2D)
    "Crane & Co" -> BazarPalette.Gray600
    else -> BazarPalette.Gray900
}

private fun vendorStarRating(name: String): Int = when (name) {
    "Wattpad" -> 3
    "Kuromi" -> 5
    "Crane & Co" -> 5
    "GooDay" -> 4
    "Warehouse" -> 4
    else -> 4
}

private fun selectedVendorUnderlineStart(tab: String): Dp = when (tab) {
    "All" -> 7.dp
    "Books" -> 54.dp
    "Poems" -> 123.dp
    "Special for you" -> 197.dp
    "Stationary" -> 327.dp
    "Our Vendors" -> 7.dp
    else -> 7.dp
}

private fun selectedAuthorUnderlineStart(tab: String): Dp = when (tab) {
    "All" -> 7.dp
    "Poets" -> 56.dp
    "Playwrights" -> 128.dp
    "Novelists" -> 235.dp
    "Journalists" -> 330.dp
    else -> 7.dp
}

private fun authorInitials(name: String): String =
    name.split(" ")
        .mapNotNull { it.firstOrNull()?.uppercaseChar()?.toString() }
        .take(2)
        .joinToString("")

private fun authorColor(id: String): Color = when (id) {
    "john-freeman" -> Color(0xFF6E4A37)
    "tess-gunty" -> Color(0xFF2F5F94)
    "richard-perston" -> BazarPalette.Gray600
    "adam-dalva" -> Color(0xFF374151)
    else -> BazarPalette.Primary400
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
