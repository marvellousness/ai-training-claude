package nova.publish.bazarbooks.core.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import nova.publish.bazarbooks.core.designsystem.theme.BazarSpacing

@Composable
fun BazarOtpField(
    code: String,
    onCodeChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    length: Int = 4,
) {
    Row(horizontalArrangement = Arrangement.spacedBy(BazarSpacing.Sm), modifier = modifier) {
        repeat(length) { index ->
            OutlinedTextField(
                value = code.getOrNull(index)?.toString().orEmpty(),
                onValueChange = { value ->
                    val digit = value.takeLast(1).filter(Char::isDigit)
                    val next = code.padEnd(length).toCharArray()
                    if (digit.isNotEmpty()) {
                        next[index] = digit.first()
                    }
                    onCodeChange(next.concatToString().trim().take(length))
                },
                textStyle = androidx.compose.ui.text.TextStyle(textAlign = TextAlign.Center),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                modifier = Modifier.width(56.dp),
            )
        }
    }
}
