package nova.publish.bazarbooks.feature.auth.forgotpassword

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import nova.publish.bazarbooks.core.designsystem.component.BazarAuthPrimaryButton
import nova.publish.bazarbooks.core.designsystem.component.BazarAuthTextField
import nova.publish.bazarbooks.core.designsystem.component.BazarBackButton
import nova.publish.bazarbooks.core.designsystem.component.BazarInlineTextAction
import nova.publish.bazarbooks.core.designsystem.component.BazarOtpField
import nova.publish.bazarbooks.core.designsystem.component.BazarSuccessMark
import nova.publish.bazarbooks.core.designsystem.theme.BazarDimensions
import nova.publish.bazarbooks.core.designsystem.theme.BazarSpacing
import nova.publish.bazarbooks.core.designsystem.theme.BazarTextStyles

@Composable
fun ForgotPasswordScreen(
    state: ForgotPasswordState,
    onIntent: (ForgotPasswordIntent) -> Unit,
    onBack: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        BazarBackButton(
            onClick = onBack,
            modifier = Modifier
                .padding(
                    start = BazarSpacing.Lg,
                    top = BazarDimensions.BottomNavHeight - BazarSpacing.Md,
                ),
        )

        when (state.step) {
            ForgotPasswordStep.MethodSelection -> MethodSelectionStep(state, onIntent)
            ForgotPasswordStep.EmailInput -> ResetInputStep(
                title = "Reset Password",
                body = "Please enter your email, we will send verification code to your email.",
                label = "Email",
                placeholder = "example@email.com",
                value = state.email,
                error = state.emailError,
                keyboardType = KeyboardType.Email,
                onValueChange = { onIntent(ForgotPasswordIntent.EmailChanged(it)) },
                onSubmit = { onIntent(ForgotPasswordIntent.SubmitEmail) },
                enabled = !state.isLoading,
            )
            ForgotPasswordStep.PhoneInput -> ResetInputStep(
                title = "Reset Password",
                body = "Please enter your phone number, we will send a verification code to your phone number.",
                label = "Phone Number",
                placeholder = "(+965) 123 435 7565",
                value = state.phone,
                error = state.phoneError,
                keyboardType = KeyboardType.Phone,
                onValueChange = { onIntent(ForgotPasswordIntent.PhoneChanged(it)) },
                onSubmit = { onIntent(ForgotPasswordIntent.SubmitPhone) },
                enabled = !state.isLoading,
            )
            ForgotPasswordStep.EmailVerification -> VerificationStep(
                body = "Please enter the code we just sent to email ${state.email}",
                otp = state.otp,
                error = state.otpError,
                onOtpChange = { onIntent(ForgotPasswordIntent.OtpChanged(it)) },
                onContinue = { onIntent(ForgotPasswordIntent.SubmitOtp) },
                onResend = { onIntent(ForgotPasswordIntent.ResendOtp) },
            )
            ForgotPasswordStep.PhoneVerification -> VerificationStep(
                body = "Please enter the code we just sent to phone number  ${state.phone}",
                otp = state.otp,
                error = state.otpError,
                onOtpChange = { onIntent(ForgotPasswordIntent.OtpChanged(it)) },
                onContinue = { onIntent(ForgotPasswordIntent.SubmitOtp) },
                onResend = { onIntent(ForgotPasswordIntent.ResendOtp) },
            )
            ForgotPasswordStep.NewPassword -> NewPasswordStep(state, onIntent)
            ForgotPasswordStep.Success -> SuccessStep(
                onLogin = { onIntent(ForgotPasswordIntent.Login) },
            )
        }
    }
}

@Composable
private fun MethodSelectionStep(
    state: ForgotPasswordState,
    onIntent: (ForgotPasswordIntent) -> Unit,
) {
    ForgotPasswordContent {
        ForgotPasswordHeader(
            title = "Forgot Password",
            body = "Select which contact details should we use to reset your password",
        )
        Spacer(modifier = Modifier.height(BazarSpacing.Xl))
        Row(
            horizontalArrangement = Arrangement.spacedBy(BazarSpacing.Lg),
            modifier = Modifier.fillMaxWidth(),
        ) {
            ResetMethodCard(
                title = "Email",
                body = "Send to your email",
                icon = Icons.Filled.Email,
                selected = state.selectedMethod == ForgotPasswordMethod.Email,
                onClick = { onIntent(ForgotPasswordIntent.MethodSelected(ForgotPasswordMethod.Email)) },
                modifier = Modifier.weight(1f),
            )
            ResetMethodCard(
                title = "Phone Number",
                body = "Send to your phone",
                icon = Icons.Filled.Phone,
                selected = state.selectedMethod == ForgotPasswordMethod.Phone,
                onClick = { onIntent(ForgotPasswordIntent.MethodSelected(ForgotPasswordMethod.Phone)) },
                modifier = Modifier.weight(1f),
            )
        }
        state.methodError?.let { error ->
            Spacer(modifier = Modifier.height(BazarSpacing.Sm))
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                style = BazarTextStyles.Body12Regular,
            )
        }
        Spacer(modifier = Modifier.height(BazarSpacing.Xxxl + BazarSpacing.Xs))
        BazarAuthPrimaryButton(
            text = "Continue",
            onClick = { onIntent(ForgotPasswordIntent.SubmitMethod) },
        )
    }
}

@Composable
private fun ResetMethodCard(
    title: String,
    body: String,
    icon: ImageVector,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val border = if (selected) {
        BorderStroke(BazarSpacing.Xxs, MaterialTheme.colorScheme.primary)
    } else {
        null
    }

    Surface(
        shape = RoundedCornerShape(BazarDimensions.CardRadius),
        color = MaterialTheme.colorScheme.surfaceVariant,
        border = border,
        modifier = modifier
            .height(BazarDimensions.BookCoverHeight - BazarSpacing.Xs)
            .clickable(onClick = onClick),
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(BazarSpacing.Lg),
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(BazarDimensions.FieldHeight)
                    .background(
                        color = if (selected) {
                            MaterialTheme.colorScheme.primaryContainer
                        } else {
                            MaterialTheme.colorScheme.surface
                        },
                        shape = RoundedCornerShape(BazarDimensions.AvatarSize),
                    ),
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = if (selected) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.outline
                    },
                    modifier = Modifier.size(BazarDimensions.IconSize),
                )
            }
            Spacer(modifier = Modifier.height(BazarSpacing.Lg))
            Text(
                text = title,
                color = MaterialTheme.colorScheme.onBackground,
                style = BazarTextStyles.Body14Medium,
            )
            Text(
                text = body,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = BazarTextStyles.Body14Regular,
            )
        }
    }
}

@Composable
private fun ResetInputStep(
    title: String,
    body: String,
    label: String,
    placeholder: String,
    value: String,
    error: String?,
    keyboardType: KeyboardType,
    onValueChange: (String) -> Unit,
    onSubmit: () -> Unit,
    enabled: Boolean,
) {
    ForgotPasswordContent {
        ForgotPasswordHeader(title = title, body = body)
        Spacer(modifier = Modifier.height(BazarSpacing.Xl))
        BazarAuthTextField(
            value = value,
            onValueChange = onValueChange,
            label = label,
            placeholder = placeholder,
            keyboardType = keyboardType,
            error = error,
        )
        Spacer(modifier = Modifier.height(BazarSpacing.Xl))
        BazarAuthPrimaryButton(
            text = "Send",
            onClick = onSubmit,
            enabled = enabled,
        )
    }
}

@Composable
private fun VerificationStep(
    body: String,
    otp: String,
    error: String?,
    onOtpChange: (String) -> Unit,
    onContinue: () -> Unit,
    onResend: () -> Unit,
) {
    ForgotPasswordContent(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ForgotPasswordHeader(
            title = "Verification Code",
            body = body,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(BazarSpacing.Xxl + BazarSpacing.Sm))
        BazarOtpField(
            code = otp,
            onCodeChange = onOtpChange,
        )
        error?.let {
            Spacer(modifier = Modifier.height(BazarSpacing.Sm))
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                style = BazarTextStyles.Body12Regular,
                textAlign = TextAlign.Center,
            )
        }
        Spacer(modifier = Modifier.height(BazarSpacing.Xl))
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = "If you didn’t receive a code?",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = BazarTextStyles.Body14Regular,
            )
            BazarInlineTextAction(
                text = "Resend",
                onClick = onResend,
            )
        }
        Spacer(modifier = Modifier.height(BazarSpacing.Xxl + BazarSpacing.Md))
        BazarAuthPrimaryButton(
            text = "Continue",
            onClick = onContinue,
        )
    }
}

@Composable
private fun NewPasswordStep(
    state: ForgotPasswordState,
    onIntent: (ForgotPasswordIntent) -> Unit,
) {
    ForgotPasswordContent {
        ForgotPasswordHeader(
            title = "New Password",
            body = "Create your new password, so you can login to your account.",
        )
        Spacer(modifier = Modifier.height(BazarSpacing.Xl))
        Column(verticalArrangement = Arrangement.spacedBy(BazarSpacing.Lg)) {
            BazarAuthTextField(
                value = state.newPassword,
                onValueChange = { onIntent(ForgotPasswordIntent.NewPasswordChanged(it)) },
                label = "New Password",
                placeholder = "Your password",
                keyboardType = KeyboardType.Password,
                error = state.passwordError,
                password = true,
            )
            BazarAuthTextField(
                value = state.confirmPassword,
                onValueChange = { onIntent(ForgotPasswordIntent.ConfirmPasswordChanged(it)) },
                label = "Confirm Password",
                placeholder = "Your password",
                keyboardType = KeyboardType.Password,
                error = state.confirmPasswordError,
                password = true,
            )
        }
        Spacer(modifier = Modifier.height(BazarSpacing.Xl))
        BazarAuthPrimaryButton(
            text = "Send",
            onClick = { onIntent(ForgotPasswordIntent.SubmitNewPassword) },
        )
    }
}

@Composable
private fun SuccessStep(
    onLogin: () -> Unit,
) {
    ForgotPasswordContent(
        topPadding = BazarDimensions.BookCoverHeight + BazarDimensions.BottomNavHeight + BazarSpacing.Xl,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        BazarSuccessMark()
        Spacer(modifier = Modifier.height(BazarSpacing.Xxl + BazarSpacing.Sm))
        Text(
            text = "Password Changed!",
            color = MaterialTheme.colorScheme.onBackground,
            style = BazarTextStyles.Heading3,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(BazarSpacing.Sm))
        Text(
            text = "Password changed successfully, you can login again with a new password",
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = BazarTextStyles.Body16Regular,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(BazarSpacing.Xxl + BazarSpacing.Sm))
        BazarAuthPrimaryButton(
            text = "Login",
            onClick = onLogin,
        )
    }
}

@Composable
private fun ForgotPasswordContent(
    modifier: Modifier = Modifier,
    topPadding: androidx.compose.ui.unit.Dp = BazarDimensions.BottomNavHeight + BazarSpacing.Xxl + BazarSpacing.Md,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        horizontalAlignment = horizontalAlignment,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = BazarDimensions.HorizontalPadding)
            .padding(top = topPadding),
        content = content,
    )
}

@Composable
private fun ForgotPasswordHeader(
    title: String,
    body: String,
    textAlign: TextAlign = TextAlign.Start,
) {
    Column(verticalArrangement = Arrangement.spacedBy(BazarSpacing.Sm)) {
        Text(
            text = title,
            color = MaterialTheme.colorScheme.onBackground,
            style = BazarTextStyles.Heading3,
            textAlign = textAlign,
            modifier = Modifier.fillMaxWidth(),
        )
        Text(
            text = body,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = BazarTextStyles.Body16Regular,
            textAlign = textAlign,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
