package nova.publish.bazarbooks.feature.cart_checkout.components

import androidx.compose.runtime.Composable
import nova.publish.bazarbooks.core.designsystem.component.BazarTextField

@Composable
fun AddressForm(value: String, onValueChange: (String) -> Unit) { BazarTextField(value, onValueChange, "Address") }
