package nova.publish.bazarbooks.feature.auth.signin

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
import androidx.compose.ui.unit.dp
import nova.publish.bazarbooks.core.designsystem.component.BazarAuthPrimaryButton
import nova.publish.bazarbooks.core.designsystem.component.BazarAuthTextField
import nova.publish.bazarbooks.core.designsystem.component.BazarBackButton
import nova.publish.bazarbooks.core.designsystem.component.BazarDividerLabel
import nova.publish.bazarbooks.core.designsystem.component.BazarErrorBanner
import nova.publish.bazarbooks.core.designsystem.component.BazarInlinePrompt
import nova.publish.bazarbooks.core.designsystem.component.BazarInlineTextAction
import nova.publish.bazarbooks.core.designsystem.component.BazarSocialSignInButton
import nova.publish.bazarbooks.core.designsystem.theme.BazarDimensions
import nova.publish.bazarbooks.core.designsystem.theme.BazarPalette
import nova.publish.bazarbooks.core.designsystem.theme.BazarSpacing
import nova.publish.bazarbooks.core.designsystem.theme.BazarTextStyles

@Composable
fun SigninScreen(
    state: SigninState,
    onIntent: (SigninIntent) -> Unit,
    onSignUp: () -> Unit,
    onForgotPassword: () -> Unit,
    onBack: () -> Unit = {},
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.TopCenter,
    ) {
        Column(
            modifier = Modifier
                .widthIn(max = BazarDimensions.MobileScreenWidth)
                .fillMaxWidth()
                .padding(horizontal = BazarDimensions.HorizontalPadding)
                .padding(top = SignInContentTopPadding),
        ) {
            BazarBackButton(onClick = onBack)
            Spacer(modifier = Modifier.height(SignInBackToHeaderSpacing))
            SignInHeader()
            Spacer(modifier = Modifier.height(SignInHeaderToFormSpacing))
            SignInCredentialFields(
                state = state,
                onIntent = onIntent,
                onForgotPassword = onForgotPassword,
            )
            Spacer(modifier = Modifier.height(SignInFormToButtonSpacing))
            SignInPrimaryActions(
                state = state,
                onIntent = onIntent,
                onSignUp = onSignUp,
            )
            Spacer(modifier = Modifier.height(SignInDividerTopSpacing))
            BazarDividerLabel(text = "Or with")
            Spacer(modifier = Modifier.height(SignInSocialTopSpacing))
            SignInSocialActions(enabled = !state.isLoading)
        }
    }
}

@Composable
private fun SignInHeader() {
    Column(verticalArrangement = Arrangement.spacedBy(BazarSpacing.Sm)) {
        Text(
            text = "Welcome Back 👋",
            color = MaterialTheme.colorScheme.onBackground,
            style = BazarTextStyles.Heading3,
        )
        Text(
            text = "Sign to your account",
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = BazarTextStyles.Body16Regular,
        )
    }
}

@Composable
private fun SignInCredentialFields(
    state: SigninState,
    onIntent: (SigninIntent) -> Unit,
    onForgotPassword: () -> Unit,
) {
    Column {
        BazarAuthTextField(
            value = state.email,
            onValueChange = { onIntent(SigninIntent.EmailChanged(it)) },
            label = "Email",
            placeholder = "Your email",
            keyboardType = KeyboardType.Email,
            error = state.emailError,
        )
        Spacer(modifier = Modifier.height(SignInFieldSpacing))
        BazarAuthTextField(
            value = state.password,
            onValueChange = { onIntent(SigninIntent.PasswordChanged(it)) },
            label = "Password",
            placeholder = "Your password",
            keyboardType = KeyboardType.Password,
            error = state.passwordError,
            password = true,
        )
        Spacer(modifier = Modifier.height(BazarSpacing.Sm))
        BazarInlineTextAction(
            text = "Forgot Password?",
            onClick = onForgotPassword,
            modifier = Modifier.height(ForgotPasswordHeight),
        )
        state.errorMessage?.let { message ->
            Spacer(modifier = Modifier.height(BazarSpacing.Md))
            BazarErrorBanner(message = message)
        }
    }
}

@Composable
private fun SignInPrimaryActions(
    state: SigninState,
    onIntent: (SigninIntent) -> Unit,
    onSignUp: () -> Unit,
) {
    Column(verticalArrangement = Arrangement.spacedBy(SignUpPromptTopSpacing)) {
        BazarAuthPrimaryButton(
            text = "Login",
            onClick = { onIntent(SigninIntent.Submit) },
            enabled = !state.isLoading,
        )
        SignUpPrompt(onSignUp = onSignUp)
    }
}

@Composable
private fun SignUpPrompt(onSignUp: () -> Unit) {
    BazarInlinePrompt(
        leadingText = "Don't have an account?",
        actionText = "Sign Up",
        onAction = onSignUp,
    )
}

@Composable
private fun SignInSocialActions(enabled: Boolean) {
    Column(verticalArrangement = Arrangement.spacedBy(BazarSpacing.Sm)) {
        BazarSocialSignInButton(
            text = "Sign in with Google",
            iconText = "G",
            iconColor = BazarPalette.Green,
            enabled = enabled,
            onClick = {},
        )
        BazarSocialSignInButton(
            text = "Sign in with Apple",
            iconText = "●",
            iconColor = MaterialTheme.colorScheme.onBackground,
            enabled = enabled,
            onClick = {},
        )
    }
}

private val SignInContentTopPadding = 16.dp
private val SignInBackToHeaderSpacing = 11.dp
private val SignInHeaderToFormSpacing = 30.dp
private val SignInFieldSpacing = 16.dp
private val SignInFormToButtonSpacing = 28.dp
private val SignUpPromptTopSpacing = 18.dp
private val SignInDividerTopSpacing = 18.dp
private val SignInSocialTopSpacing = 24.dp
private val ForgotPasswordHeight = 28.dp
