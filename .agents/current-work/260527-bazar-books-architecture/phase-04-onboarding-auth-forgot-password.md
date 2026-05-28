# Phase 04: Onboarding, Auth, Forgot Password

Status: Pending  
Priority: P1  
Effort: 10h

## Overview

Implement Figma onboarding, sign-in, multi-step sign-up, and forgot password with StateFlow MVI. Sign-up includes local simulated email/phone OTP and mandatory phone capture; Google/Apple and real provider integrations remain deferred.

## Figma Anchors

- `1. Spash Screen`
- `1.1 Onboarding 2`
- `1.2 Onboarding 3`
- `1.3 Onboarding 4`
- `2. Sign In - Empty State`
- `2.1 Sign In - Active State`
- `2.2 Sign Up - Empte State`
- `2.3 Sign Up - Active State Right Password`
- `2.4 Sign Up - Active State Wrong Password`
- `2.5 Sign Up - Verification Code Email`
- `2.6 Sign Up - Input Phone Number`
- `2.7 Sign Up - Verification Code Phone`
- `2.8 Sign Up - Success Verification`
- `3. Forgot Password`
- `3.1 Forgot Password - With Email`
- `3.2 Forgot Password - With Phone`
- `3.3 Forgot Password - Verification Code Email`
- `3.4 Forgot Password - Verification Code Phone`
- `3.5 Forgot Password - Create New Password`
- `3.6 Forgot Password - Success Create New Password`

## Implementation Steps

1. Preserve the existing Figma-aligned onboarding implementation.
2. Store onboarding completion in DataStore.
3. Implement session state:
   - unauthenticated
   - guest
   - authenticated
   - expired
4. Implement sign-in MVI:
   - email input
   - password input
   - forgot password action
   - guest action
   - login action
   - validation and loading/error states
5. Implement sign-up MVI:
   - explicit steps: `Form`, `EmailVerification`, `PhoneInput`, `PhoneVerification`, `Success`
   - name, email, password, phone, email OTP, phone OTP
   - password requirement checklist visible while typing
   - terms/data policy text
   - local simulated OTP generation and resend for email and phone
   - mandatory phone validation before account completion
   - success state and `Get Started` completion action
6. Implement forgot-password MVI:
   - explicit steps: `MethodSelection`, `EmailInput`, `PhoneInput`, `EmailVerification`, `PhoneVerification`, `NewPassword`, `Success`
   - email and phone reset method cards
   - local simulated OTP generation and resend for email and phone
   - new password and confirm password validation
   - success state and `Login` return action
7. Show disabled or unavailable UI for Google/Apple paths only if needed visually; do not implement real provider business logic in v1.
8. Add English/Vietnamese strings.

## Sign-Up Flow Contract

- `Register` validates name, email, and password, then generates a local simulated email OTP and moves to `EmailVerification`.
- Email OTP `Continue` validates the local code and moves to mandatory `PhoneInput`.
- Phone `Continue` validates a non-blank plausible numeric phone number, generates a local simulated phone OTP, and moves to `PhoneVerification`.
- Phone OTP `Continue` validates the local code and moves to `Success`.
- `Get Started` completes sign-up and navigates home.
- Resend actions regenerate the relevant local simulated OTP and clear the current OTP error.

## Forgot-Password Flow Contract

- Method selection requires email or phone before continuing.
- Email `Send` validates email, generates a local simulated email OTP, and moves to `EmailVerification`.
- Phone `Send` validates a non-blank plausible numeric phone number, generates a local simulated phone OTP, and moves to `PhoneVerification`.
- OTP `Continue` validates the local code and moves to `NewPassword`.
- Resend regenerates the relevant local simulated OTP and clears the OTP field/error.
- New password `Send` requires a valid password and matching confirm password, then moves to `Success`.
- Success `Login` returns to sign-in.

## Acceptance Criteria

- First launch goes through splash/onboarding.
- Returning user skips onboarding.
- User can continue as guest.
- User can sign in with simulated email/password.
- User can complete sign-up through form, email OTP, mandatory phone input, phone OTP, and success.
- Password checklist appears while the user is typing a password.
- User can complete email and phone forgot-password simulations.
- User cannot finish forgot password with mismatched new/confirm passwords.
- Google/Apple and real SMS/email provider integrations are not accidentally wired.

## Validation

```powershell
.\gradlew.bat :feature:onboarding:testDebugUnitTest
.\gradlew.bat :feature:auth:testDebugUnitTest
.\gradlew.bat :app:assembleDebug
```
