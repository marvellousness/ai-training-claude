package nova.publish.bazarbooks.core.designsystem.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import nova.publish.bazarbooks.core.designsystem.theme.BazarDimensions

@Composable
fun BazarButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) = BazarPrimaryButton(
    text = text,
    onClick = onClick,
    modifier = modifier,
    enabled = enabled,
)

@Composable
fun BazarPrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier.fillMaxWidth().heightIn(min = BazarDimensions.ButtonHeight),
    ) {
        Text(text)
    }
}

@Composable
fun BazarSecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    OutlinedButton(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier.fillMaxWidth().heightIn(min = BazarDimensions.ButtonHeight),
    ) {
        Text(text)
    }
}
