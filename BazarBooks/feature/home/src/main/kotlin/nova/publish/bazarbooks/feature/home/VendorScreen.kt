package nova.publish.bazarbooks.feature.home

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import nova.publish.bazarbooks.feature.home.model.VendorUiModel

@Composable
internal fun VendorListScreen(state: HomeState, onIntent: (HomeIntent) -> Unit, onBack: () -> Unit) {
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
    BazarChipTabRow(tabs = FigmaVendorTabs, selected = selected, onSelected = onSelected)
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

private fun List<VendorUiModel>.filterVendorsByTab(tab: String) = if (tab == "All" || tab == "Our Vendors") this else filter { it.category == tab }

private fun vendorStarRating(name: String): Int = when (name) {
    "Wattpad" -> 3
    "Kuromi" -> 5
    "Crane & Co" -> 5
    "GooDay" -> 4
    "Warehouse" -> 4
    else -> 4
}
