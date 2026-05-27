package nova.publish.bazarbooks.feature.onboarding

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import nova.publish.bazarbooks.core.designsystem.theme.BazarPalette
import nova.publish.bazarbooks.core.designsystem.theme.BazarTextStyles
import nova.publish.bazarbooks.feature.onboarding.components.FigmaOnboardingPages
import nova.publish.bazarbooks.feature.onboarding.components.PageIndicator
import kotlin.math.min

@Composable
fun OnboardingScreen(
    onGetStarted: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var showSplash by rememberSaveable { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        delay(900)
        showSplash = false
    }

    if (showSplash) {
        SplashScreen(modifier = modifier)
    } else {
        OnboardingPager(
            onGetStarted = onGetStarted,
            modifier = modifier,
        )
    }
}

@Composable
private fun OnboardingPager(
    onGetStarted: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var pageIndex by rememberSaveable { mutableIntStateOf(0) }
    val pages = FigmaOnboardingPages
    val page = pages[pageIndex]

    FigmaPhoneFrame(
        background = BazarPalette.White,
        modifier = modifier,
    ) { scale ->
        TextButton(
            onClick = onGetStarted,
            modifier = Modifier
                .offset(x = scaled(24.dp, scale), y = scaled(52.dp, scale))
                .height(scaled(36.dp, scale)),
            colors = ButtonDefaults.textButtonColors(contentColor = BazarPalette.Primary500),
        ) {
            Text(
                text = "Skip",
                style = BazarTextStyles.Body14Medium,
                fontSize = scaledSp(14f, scale).sp,
            )
        }

        Image(
            painter = painterResource(page.imageRes),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .offset(x = scaled(27.dp, scale), y = scaled(109.dp, scale))
                .size(scaled(320.dp, scale)),
        )

        Text(
            text = page.title,
            style = BazarTextStyles.Heading3,
            color = BazarPalette.Gray900,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .offset(x = scaled(41.dp, scale), y = scaled(443.dp, scale))
                .width(scaled(292.dp, scale)),
            fontSize = scaledSp(24f, scale).sp,
            lineHeight = scaledSp(32.4f, scale).sp,
        )

        Text(
            text = page.body.trim(),
            style = BazarTextStyles.Body16Regular,
            color = BazarPalette.Gray500,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .offset(x = scaled(41.dp, scale), y = scaled(519.dp, scale))
                .width(scaled(292.dp, scale)),
            fontSize = scaledSp(16f, scale).sp,
            lineHeight = scaledSp(24f, scale).sp,
        )

        PageIndicator(
            current = pageIndex,
            total = pages.size,
            modifier = Modifier.offset(x = scaled(176.dp, scale), y = scaled(619.dp, scale)),
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(scaled(8.dp, scale)),
            modifier = Modifier
                .offset(x = scaled(24.dp, scale), y = scaled(659.dp, scale))
                .width(scaled(327.dp, scale)),
        ) {
            FigmaButton(
                text = page.primaryAction,
                primary = true,
                scale = scale,
                onClick = {
                    if (pageIndex == 0) {
                        pageIndex = 1
                    } else {
                        onGetStarted()
                    }
                },
            )
            FigmaButton(
                text = "Sign in",
                primary = false,
                scale = scale,
                onClick = onGetStarted,
            )
        }
    }
}

@Composable
private fun SplashScreen(modifier: Modifier = Modifier) {
    FigmaPhoneFrame(
        background = BazarPalette.Primary500,
        modifier = modifier,
    ) { scale ->
        BazarLogo(
            modifier = Modifier.offset(x = scaled(80.dp, scale), y = scaled(376.dp, scale)),
            scale = scale,
        )
    }
}

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
private fun FigmaPhoneFrame(
    background: Color,
    modifier: Modifier = Modifier,
    content: @Composable (Float) -> Unit,
) {
    BoxWithConstraints(
        contentAlignment = Alignment.TopCenter,
        modifier = modifier
            .fillMaxSize()
            .background(background),
    ) {
        val scale = remember(maxWidth, maxHeight) {
            min(maxWidth.value / FigmaWidth.value, maxHeight.value / FigmaHeight.value)
        }
        Box(
            modifier = Modifier
                .size(width = scaled(FigmaWidth, scale), height = scaled(FigmaHeight, scale))
                .background(background),
        ) {
            content(scale)
        }
    }
}

@Composable
private fun FigmaButton(
    text: String,
    primary: Boolean,
    scale: Float,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(scaled(12.dp, scale)),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (primary) BazarPalette.Primary500 else BazarPalette.Primary50,
            contentColor = if (primary) BazarPalette.White else BazarPalette.Primary500,
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp
        ),
        modifier = Modifier
            .width(scaled(327.dp, scale))
            .height(scaled(56.dp, scale)),
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.Bold,
            letterSpacing = scaledSp(0.3f, scale).sp,
            fontSize = scaledSp(16f, scale).sp,
            lineHeight = scaledSp(24f, scale).sp,
        )
    }
}

@Composable
private fun BazarLogo(
    modifier: Modifier,
    scale: Float,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.height(scaled(44.dp, scale)),
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(scaled(44.dp, scale))
                .clip(CircleShape)
                .background(BazarPalette.White),
        ) {
            Text(
                text = "B",
                color = BazarPalette.Primary500,
                fontWeight = FontWeight.Bold,
                fontSize = scaledSp(24f, scale).sp,
            )
        }
        Spacer(Modifier.width(scaled(10.dp, scale)))
        Text(
            text = "Bazar.",
            color = BazarPalette.White,
            fontWeight = FontWeight.Bold,
            fontSize = scaledSp(31.5f, scale).sp,
            lineHeight = scaledSp(44f, scale).sp,
        )
    }
}

private val FigmaWidth = 375.dp
private val FigmaHeight = 812.dp

private fun scaled(value: Dp, scale: Float): Dp = value * scale

private fun scaledSp(value: Float, scale: Float): Float = value * scale
