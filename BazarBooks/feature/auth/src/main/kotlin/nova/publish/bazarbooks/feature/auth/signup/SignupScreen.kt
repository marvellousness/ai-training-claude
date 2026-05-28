package nova.publish.bazarbooks.feature.auth.signup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import nova.publish.bazarbooks.core.designsystem.component.BazarAuthPrimaryButton
import nova.publish.bazarbooks.core.designsystem.component.BazarAuthTextField
import nova.publish.bazarbooks.core.designsystem.component.BazarInlinePrompt
import nova.publish.bazarbooks.core.designsystem.component.BazarInlineTextAction
import nova.publish.bazarbooks.core.designsystem.component.BazarOtpField
import nova.publish.bazarbooks.core.designsystem.component.BazarPasswordRequirement
import nova.publish.bazarbooks.core.designsystem.component.BazarPasswordRequirementList
import nova.publish.bazarbooks.core.designsystem.component.BazarSuccessMark
import nova.publish.bazarbooks.core.designsystem.theme.BazarDimensions
import nova.publish.bazarbooks.core.designsystem.theme.BazarSpacing
import nova.publish.bazarbooks.core.designsystem.theme.BazarTextStyles

@Composable
fun SignupScreen(
    state: SignupState,
    onIntent: (SignupIntent) -> Unit,
    onSignIn: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.TopCenter,
    ) {
        when (state.step) {
            SignupStep.Form -> SignUpFormStep(
                state = state,
                onIntent = onIntent,
                onSignIn = onSignIn,
            )
            SignupStep.EmailVerification -> VerificationStep(
                title = "Verification Email",
                message = "Please enter the code we just sent to email ${state.email}",
                code = state.emailOtp,
                error = state.emailOtpError,
                onCodeChange = { onIntent(SignupIntent.EmailOtpChanged(it)) },
                onContinue = { onIntent(SignupIntent.SubmitEmailOtp) },
                onResend = { onIntent(SignupIntent.ResendEmailOtp) },
            )
            SignupStep.PhoneInput -> PhoneInputStep(
                state = state,
                onIntent = onIntent,
            )
            SignupStep.PhoneVerification -> VerificationStep(
                title = "Verification Phone",
                message = "Please enter the code we just sent to phone number ${state.phone}",
                code = state.phoneOtp,
                error = state.phoneOtpError,
                onCodeChange = { onIntent(SignupIntent.PhoneOtpChanged(it)) },
                onContinue = { onIntent(SignupIntent.SubmitPhoneOtp) },
                onResend = { onIntent(SignupIntent.ResendPhoneOtp) },
            )
            SignupStep.Success -> SignUpSuccessStep(
                onGetStarted = { onIntent(SignupIntent.GetStarted) },
            )
        }
    }
}

@Composable
private fun SignUpFormStep(
    state: SignupState,
    onIntent: (SignupIntent) -> Unit,
    onSignIn: () -> Unit,
) {
    Column(
        modifier = Modifier
            .widthIn(max = BazarDimensions.MobileScreenWidth)
            .fillMaxSize()
            .padding(horizontal = BazarDimensions.HorizontalPadding)
            .padding(top = SignUpContentTopPadding),
    ) {
        SignUpHeader(
            title = "Sign Up",
            body = "Create account and choose favorite menu",
            textAlign = TextAlign.Left,
        )
        Spacer(modifier = Modifier.height(SignUpHeaderToFormSpacing))
        SignUpFields(
            state = state,
            onIntent = onIntent,
        )
        Spacer(modifier = Modifier.height(if (state.password.isBlank()) SignUpFormToActionsSpacing else SignUpChecklistToActionsSpacing))
        SignUpActions(
            state = state,
            onIntent = onIntent,
            onSignIn = onSignIn,
        )
        Spacer(modifier = Modifier.weight(1f))
        SignUpTerms()
        Spacer(modifier = Modifier.height(SignUpBottomPadding))
    }
}

@Composable
private fun SignUpHeader(
    title: String,
    body: String,
    textAlign: TextAlign,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = if (textAlign == TextAlign.Center) Alignment.CenterHorizontally else Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(BazarSpacing.Sm),
        modifier = modifier.fillMaxWidth(),
    ) {
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

@Composable
private fun SignUpFields(
    state: SignupState,
    onIntent: (SignupIntent) -> Unit,
) {
    Column(verticalArrangement = Arrangement.spacedBy(SignUpFieldSpacing)) {
        BazarAuthTextField(
            value = state.fullName,
            onValueChange = { onIntent(SignupIntent.FullNameChanged(it)) },
            label = "Name",
            placeholder = "Your name",
            keyboardType = KeyboardType.Text,
            error = state.fullNameError,
        )
        BazarAuthTextField(
            value = state.email,
            onValueChange = { onIntent(SignupIntent.EmailChanged(it)) },
            label = "Email",
            placeholder = "Your email",
            keyboardType = KeyboardType.Email,
            error = state.emailError,
        )
        BazarAuthTextField(
            value = state.password,
            onValueChange = { onIntent(SignupIntent.PasswordChanged(it)) },
            label = "Password",
            placeholder = "Your password",
            keyboardType = KeyboardType.Password,
            error = state.passwordError,
            password = true,
        )
        if (state.password.isNotBlank()) {
            BazarPasswordRequirementList(
                requirements = listOf(
                    BazarPasswordRequirement("Minimum 8 characters", state.passwordRequirements.minLength),
                    BazarPasswordRequirement("Atleast 1 number (1-9)", state.passwordRequirements.hasNumber),
                    BazarPasswordRequirement(
                        "Atleast lowercase or uppercase letters",
                        state.passwordRequirements.hasLetter,
                    ),
                ),
                modifier = Modifier.padding(top = BazarSpacing.Md),
            )
        }
    }
}

@Composable
private fun SignUpActions(
    state: SignupState,
    onIntent: (SignupIntent) -> Unit,
    onSignIn: () -> Unit,
) {
    Column(verticalArrangement = Arrangement.spacedBy(SignUpPromptTopSpacing)) {
        BazarAuthPrimaryButton(
            text = "Register",
            onClick = { onIntent(SignupIntent.Submit) },
            enabled = !state.isLoading,
        )
        BazarInlinePrompt(
            leadingText = "Have an account?",
            actionText = "Sign In",
            onAction = onSignIn,
        )
    }
}

@Composable
private fun VerificationStep(
    title: String,
    message: String,
    code: String,
    error: String?,
    onCodeChange: (String) -> Unit,
    onContinue: () -> Unit,
    onResend: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .widthIn(max = BazarDimensions.MobileScreenWidth)
            .fillMaxSize()
            .padding(horizontal = BazarDimensions.HorizontalPadding)
            .padding(top = SignUpContentTopPadding),
    ) {
        SignUpHeader(
            title = title,
            body = message,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(VerificationHeaderToOtpSpacing))
        BazarOtpField(
            code = code,
            onCodeChange = onCodeChange,
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
        Spacer(modifier = Modifier.height(VerificationOtpToResendSpacing))
        BazarInlineTextAction(
            text = "If you didn’t receive a code? Resend",
            onClick = onResend,
        )
        Spacer(modifier = Modifier.height(VerificationResendToButtonSpacing))
        BazarAuthPrimaryButton(
            text = "Continue",
            onClick = onContinue,
        )
    }
}

@Composable
private fun PhoneInputStep(
    state: SignupState,
    onIntent: (SignupIntent) -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .widthIn(max = BazarDimensions.MobileScreenWidth)
            .fillMaxSize()
            .padding(horizontal = BazarDimensions.HorizontalPadding)
            .padding(top = SignUpContentTopPadding),
    ) {
        SignUpHeader(
            title = "Phone Number",
            body = "Please enter your phone number, so we can more easily deliver your order",
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(PhoneHeaderToFieldSpacing))
        BazarAuthTextField(
            value = state.phone,
            onValueChange = { onIntent(SignupIntent.PhoneChanged(it)) },
            label = "Phone Number",
            placeholder = "(+965) 123 435 7565",
            keyboardType = KeyboardType.Phone,
            error = state.phoneError,
        )
        Spacer(modifier = Modifier.height(PhoneFieldToButtonSpacing))
        BazarAuthPrimaryButton(
            text = "Continue",
            onClick = { onIntent(SignupIntent.SubmitPhone) },
        )
    }
}

@Composable
private fun SignUpSuccessStep(
    onGetStarted: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .widthIn(max = BazarDimensions.MobileScreenWidth)
            .fillMaxSize()
            .padding(horizontal = BazarDimensions.HorizontalPadding),
    ) {
        Spacer(modifier = Modifier.height(SuccessTopSpacing))
        BazarSuccessMark()
        Spacer(modifier = Modifier.height(SuccessMarkToTextSpacing))
        SignUpHeader(
            title = "Congratulation!",
            body = "your account is complete, please enjoy the best menu from us.",
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(SuccessTextToButtonSpacing))
        BazarAuthPrimaryButton(
            text = "Get Started",
            onClick = onGetStarted,
        )
    }
}

@Composable
private fun SignUpTerms() {
    Text(
        text = "By clicking Register, you agree to our Terms and Data Policy.",
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        style = BazarTextStyles.Body14Medium,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = SignUpTermsHorizontalPadding),
    )
}

private val SignUpContentTopPadding = 72.dp
private val SignUpHeaderToFormSpacing = 24.dp
private val SignUpFieldSpacing = 16.dp
private val SignUpFormToActionsSpacing = 24.dp
private val SignUpChecklistToActionsSpacing = 20.dp
private val SignUpPromptTopSpacing = 18.dp
private val SignUpBottomPadding = 56.dp
private val SignUpTermsHorizontalPadding = 42.dp
private val VerificationHeaderToOtpSpacing = 40.dp
private val VerificationOtpToResendSpacing = 24.dp
private val VerificationResendToButtonSpacing = 43.dp
private val PhoneHeaderToFieldSpacing = 24.dp
private val PhoneFieldToButtonSpacing = 81.dp
private val SuccessTopSpacing = 253.dp
private val SuccessMarkToTextSpacing = 40.dp
private val SuccessTextToButtonSpacing = 40.dp
