# Phase 04: Onboarding, Auth, Forgot Password

Status: Pending  
Priority: P1  
Effort: 10h

## Overview

Implement the Figma onboarding and email auth paths with StateFlow MVI. Social auth and phone verification shown in Figma are intentionally deferred.

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
- `2.8 Sign Up - Success Verification`
- `3. Forgot Password`
- `3.1 Forgot Password - With Email`
- `3.3 Forgot Password - Verification Code Email`
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
   - name, email, password
   - password requirement indicators
   - terms/data policy text
   - success state
6. Implement forgot-password email flow:
   - email method selection/input
   - code verification
   - create new password
   - success
7. Show disabled or unavailable UI for social/phone paths only if needed visually; do not implement their business logic in v1.
8. Add English/Vietnamese strings.

## Acceptance Criteria

- First launch goes through splash/onboarding.
- Returning user skips onboarding.
- User can continue as guest.
- User can sign in with simulated email/password.
- User can complete email forgot-password simulation.
- Phone/social auth is not accidentally wired.

## Validation

```powershell
.\gradlew.bat :feature:onboarding:testDebugUnitTest
.\gradlew.bat :feature:auth:testDebugUnitTest
.\gradlew.bat :app:assembleDebug
```
