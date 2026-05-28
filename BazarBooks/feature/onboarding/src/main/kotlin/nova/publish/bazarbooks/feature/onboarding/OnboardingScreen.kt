package nova.publish.bazarbooks.feature.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshotFlow
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.distinctUntilChanged
import nova.publish.bazarbooks.core.designsystem.component.BazarPrimaryButton
import nova.publish.bazarbooks.core.designsystem.component.BazarSecondaryButton
import nova.publish.bazarbooks.core.designsystem.theme.BazarDimensions
import nova.publish.bazarbooks.core.designsystem.theme.BazarPalette
import nova.publish.bazarbooks.core.designsystem.theme.BazarSpacing
import nova.publish.bazarbooks.core.designsystem.theme.BazarTextStyles
import nova.publish.bazarbooks.feature.onboarding.components.OnboardingPage
import nova.publish.bazarbooks.feature.onboarding.components.OnboardingPages

@Composable
fun OnboardingScreen(
    modifier: Modifier = Modifier,
    pageIndex: Int = 0,
    onPageChanged: (Int) -> Unit = {},
    onGetStarted: () -> Unit,
) {
    var showSplash by rememberSaveable { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        delay(100)
        showSplash = false
    }

    if (showSplash) {
        SplashScreen(modifier = modifier)
    } else {
        OnboardingPager(
            pageIndex = pageIndex.coerceIn(OnboardingPages.indices),
            onPageChanged = onPageChanged,
            onGetStarted = onGetStarted,
            modifier = modifier,
        )
    }
}

@Composable
private fun OnboardingPager(
    pageIndex: Int,
    onPageChanged: (Int) -> Unit,
    onGetStarted: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val pagerState = rememberPagerState(initialPage = pageIndex) {
        OnboardingPages.size
    }
    val activePageIndex = pagerState.currentPage.coerceIn(OnboardingPages.indices)

    OnboardingPagerEffects(
        pagerState = pagerState,
        selectedPageIndex = pageIndex,
        pageCount = OnboardingPages.size,
        onPageChanged = onPageChanged,
    )

    OnboardingPagerLayout(
        pagerState = pagerState,
        activePageIndex = activePageIndex,
        onSkip = onGetStarted,
        onPrimaryAction = {
            if (activePageIndex < OnboardingPages.lastIndex) {
                onPageChanged(activePageIndex + 1)
            } else {
                onGetStarted()
            }
        },
        onSignIn = onGetStarted,
        modifier = modifier,
    )
}

@Composable
private fun OnboardingPagerEffects(
    pagerState: PagerState,
    selectedPageIndex: Int,
    pageCount: Int,
    onPageChanged: (Int) -> Unit,
) {
    val latestOnPageChanged by rememberUpdatedState(onPageChanged)

    LaunchedEffect(selectedPageIndex) {
        if (pagerState.currentPage != selectedPageIndex) {
            pagerState.animateScrollToPage(selectedPageIndex)
        }
    }

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }
            .distinctUntilChanged()
            .collect { page -> latestOnPageChanged(page) }
    }

    LaunchedEffect(pagerState) {
        while (true) {
            delay(OnboardingAutoAdvanceMillis)
            val nextPage = (pagerState.currentPage + 1) % pageCount
            pagerState.animateScrollToPage(nextPage)
        }
    }
}

@Composable
private fun OnboardingPagerLayout(
    pagerState: PagerState,
    activePageIndex: Int,
    onSkip: () -> Unit,
    onPrimaryAction: () -> Unit,
    onSignIn: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(
                start = BazarDimensions.HorizontalPadding,
                top = BazarSpacing.Xxxl,
                end = BazarDimensions.HorizontalPadding,
                bottom = BazarSpacing.Xxl,
            ),
    ) {
        OnboardingSkipRow(onSkip = onSkip)

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalAlignment = Alignment.Top,
        ) { pagerPage ->
            OnboardingPageContent(page = OnboardingPages[pagerPage])
        }

        OnboardingPageIndicator(
            current = activePageIndex,
            total = OnboardingPages.size,
        )

        Spacer(Modifier.height(BazarSpacing.Xxl))

        OnboardingActionColumn(
            primaryText = OnboardingPages[activePageIndex].primaryAction,
            onPrimaryAction = onPrimaryAction,
            onSignIn = onSignIn,
        )
    }
}

@Composable
private fun OnboardingSkipRow(
    onSkip: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.Start,
        modifier = modifier.fillMaxWidth(),
    ) {
        TextButton(
            onClick = onSkip,
            shape = MaterialTheme.shapes.medium,
            colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.primary),
            modifier = Modifier
                .width(SkipButtonWidth)
                .height(SkipButtonHeight),
        ) {
            Text(
                text = "Skip",
                color = MaterialTheme.colorScheme.primary,
                style = BazarTextStyles.Body14Medium,
            )
        }
    }
}

@Composable
private fun OnboardingPageContent(
    page: OnboardingPage,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxWidth(),
    ) {
        Spacer(Modifier.height(BazarSpacing.Md))

        Image(
            painter = painterResource(page.imageRes),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier.size(OnboardingArtworkSize),
        )

        Spacer(Modifier.height(BazarSpacing.Md))

        Text(
            text = page.title,
            color = MaterialTheme.colorScheme.onBackground,
            style = BazarTextStyles.Heading3,
            textAlign = TextAlign.Center,
            modifier = Modifier.widthIn(max = OnboardingTextWidth),
        )

        Spacer(Modifier.height(BazarSpacing.Lg))

        Text(
            text = page.body,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = BazarTextStyles.Body16Regular,
            textAlign = TextAlign.Center,
            modifier = Modifier.widthIn(max = OnboardingTextWidth),
        )
    }
}

@Composable
private fun SplashScreen(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
    ) {
        OnboardingLogo(
            modifier = Modifier
                .width(SplashLogoWidth)
                .height(SplashLogoHeight),
        )
    }
}

@Composable
private fun OnboardingActionColumn(
    primaryText: String,
    onPrimaryAction: () -> Unit,
    onSignIn: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(BazarSpacing.Sm),
        modifier = modifier.fillMaxWidth(),
    ) {
        OnboardingButton(
            text = primaryText,
            primary = true,
            onClick = onPrimaryAction,
        )
        OnboardingButton(
            text = "Sign in",
            primary = false,
            onClick = onSignIn,
        )
    }
}

@Composable
private fun OnboardingButton(
    text: String,
    primary: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val buttonModifier = modifier
        .fillMaxWidth()
        .height(BazarDimensions.ButtonHeight)
    if (primary) {
        BazarPrimaryButton(
            text = text,
            onClick = onClick,
            modifier = buttonModifier,
        )
    } else {
        BazarSecondaryButton(
            text = text,
            onClick = onClick,
            modifier = buttonModifier,
        )
    }
}

@Composable
private fun OnboardingPageIndicator(
    current: Int,
    total: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(BazarSpacing.Xs),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.height(BazarSpacing.Sm),
    ) {
        repeat(total) { index ->
            val selected = index == current
            Box(
                modifier = Modifier
                    .size(
                        if (selected) {
                            BazarSpacing.Sm
                        } else {
                            BazarSpacing.Xs
                        },
                    )
                    .clip(CircleShape)
                    .background(if (selected) MaterialTheme.colorScheme.primary else BazarPalette.Gray200),
            )
        }
    }
}

@Composable
private fun OnboardingLogo(
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(BazarSpacing.Md),
        modifier = modifier,
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(SplashLogoMarkSize)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.onPrimary),
        ) {
            Text(
                text = "B",
                color = MaterialTheme.colorScheme.primary,
                style = BazarTextStyles.Heading3,
            )
        }
        Text(
            text = "Bazar.",
            color = MaterialTheme.colorScheme.onPrimary,
            style = BazarTextStyles.Heading2,
        )
    }
}

private val SkipButtonWidth = 60.dp
private val SkipButtonHeight = 36.dp
private val OnboardingArtworkSize = 320.dp
private val OnboardingTextWidth = 243.dp
private val SplashLogoWidth = 158.dp
private val SplashLogoHeight = 44.dp
private val SplashLogoMarkSize = 44.dp
private const val OnboardingAutoAdvanceMillis = 2_000L
