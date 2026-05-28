package nova.publish.bazarbooks.core.designsystem.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import nova.publish.bazarbooks.core.designsystem.theme.BazarDimensions
import nova.publish.bazarbooks.core.designsystem.theme.BazarPalette
import nova.publish.bazarbooks.core.designsystem.theme.BazarSpacing
import nova.publish.bazarbooks.core.designsystem.theme.BazarTextStyles

@Composable
fun BazarBackButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(BazarAuthBackButtonSize)
            .clickable(onClick = onClick),
    ) {
        Text(
            text = "←",
            color = MaterialTheme.colorScheme.onBackground,
            style = BazarTextStyles.Heading5,
        )
    }
}

@Composable
fun BazarAuthTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text,
    error: String? = null,
    password: Boolean = false,
) {
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    val visualTransformation = when {
        !password -> VisualTransformation.None
        passwordVisible -> VisualTransformation.None
        else -> PasswordVisualTransformation()
    }

    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = label,
            color = MaterialTheme.colorScheme.onBackground,
            style = BazarTextStyles.Body14Medium,
        )
        Spacer(modifier = Modifier.height(BazarSpacing.Sm))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(BazarDimensions.FieldHeight)
                .clip(RoundedCornerShape(BazarDimensions.FieldRadius))
                .background(BazarPalette.Gray50)
                .padding(start = BazarSpacing.Lg, end = BazarSpacing.Sm),
        ) {
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                singleLine = true,
                textStyle = BazarTextStyles.Body16Medium.merge(
                    TextStyle(color = MaterialTheme.colorScheme.onBackground),
                ),
                visualTransformation = visualTransformation,
                keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
                cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
                modifier = Modifier.weight(1f),
                decorationBox = { innerTextField ->
                    if (value.isEmpty()) {
                        Text(
                            text = placeholder,
                            color = BazarPalette.Gray400,
                            style = BazarTextStyles.Body16Regular,
                        )
                    }
                    innerTextField()
                },
            )
            if (password) {
                IconButton(
                    onClick = { passwordVisible = !passwordVisible },
                    modifier = Modifier.size(BazarAuthPasswordToggleSize),
                ) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                        contentDescription = if (passwordVisible) "Hide password" else "Show password",
                        tint = BazarPalette.Gray400,
                    )
                }
            }
        }
        error?.let {
            Spacer(modifier = Modifier.height(BazarSpacing.Xs))
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                style = BazarTextStyles.Body12Regular,
            )
        }
    }
}

@Composable
fun BazarAuthPrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(BazarAuthButtonRadius),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
        ),
        modifier = modifier
            .fillMaxWidth()
            .height(BazarDimensions.FieldHeight),
    ) {
        Text(
            text = text,
            style = BazarTextStyles.Body16Semibold,
        )
    }
}

@Composable
fun BazarInlineTextAction(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TextButton(
        onClick = onClick,
        contentPadding = PaddingValues(0.dp),
        modifier = modifier,
    ) {
        Text(
            text = text,
            color = MaterialTheme.colorScheme.primary,
            style = BazarTextStyles.Body14Medium,
        )
    }
}

@Composable
fun BazarInlinePrompt(
    leadingText: String,
    actionText: String,
    onAction: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = leadingText,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = BazarTextStyles.Body16Regular,
        )
        TextButton(onClick = onAction) {
            Text(
                text = actionText,
                color = MaterialTheme.colorScheme.primary,
                style = BazarTextStyles.Body16Semibold,
            )
        }
    }
}

@Composable
fun BazarDividerLabel(
    text: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(BazarSpacing.Sm),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        HorizontalDivider(
            modifier = Modifier.weight(1f),
            color = BazarPalette.Gray200,
        )
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = BazarTextStyles.Body14Regular,
            textAlign = TextAlign.Center,
        )
        HorizontalDivider(
            modifier = Modifier.weight(1f),
            color = BazarPalette.Gray200,
        )
    }
}

@Composable
fun BazarSocialSignInButton(
    text: String,
    iconText: String,
    iconColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    OutlinedButton(
        onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(BazarAuthButtonRadius),
        border = BorderStroke(1.dp, BazarPalette.Gray200),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
        ),
        modifier = modifier
            .fillMaxWidth()
            .height(BazarDimensions.FieldHeight),
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = iconText,
                color = iconColor,
                style = BazarTextStyles.Body16Semibold,
                textAlign = TextAlign.Center,
                modifier = Modifier.width(BazarAuthSocialIconWidth),
            )
            Spacer(modifier = Modifier.width(BazarSpacing.Md))
            Text(
                text = text,
                color = MaterialTheme.colorScheme.onSurface,
                style = BazarTextStyles.Body16Regular,
            )
        }
    }
}

@Composable
fun BazarPasswordRequirementList(
    requirements: List<BazarPasswordRequirement>,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(BazarSpacing.Sm),
        modifier = modifier,
    ) {
        requirements.forEach { requirement ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(BazarSpacing.Md),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = if (requirement.satisfied) "✓" else "×",
                    color = if (requirement.satisfied) BazarPalette.Primary300 else BazarPalette.Red,
                    style = BazarTextStyles.Body14Semibold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.width(BazarAuthRequirementIconWidth),
                )
                Text(
                    text = requirement.text,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = BazarTextStyles.Body14Regular,
                )
            }
        }
    }
}

data class BazarPasswordRequirement(
    val text: String,
    val satisfied: Boolean,
)

@Composable
fun BazarSuccessMark(
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(BazarAuthSuccessMarkSize)
            .background(BazarPalette.Primary50, RoundedCornerShape(BazarAuthSuccessMarkRadius)),
    ) {
        Text(
            text = "✓",
            color = MaterialTheme.colorScheme.primary,
            style = BazarTextStyles.Heading1,
        )
    }
}

private val BazarAuthBackButtonSize = 40.dp
private val BazarAuthButtonRadius = 24.dp
private val BazarAuthPasswordToggleSize = 40.dp
private val BazarAuthSocialIconWidth = 24.dp
private val BazarAuthRequirementIconWidth = 16.dp
private val BazarAuthSuccessMarkSize = 90.dp
private val BazarAuthSuccessMarkRadius = 45.dp
