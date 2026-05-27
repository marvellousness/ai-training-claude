# Phase 08: Adaptive, Localization, Testing

Status: Pending  
Priority: P1  
Effort: 4h

## Overview

Finalize app-wide adaptive layouts, English/Vietnamese localization, accessibility, and Gradle verification so the Figma-stitched implementation is stable across device sizes.

## Figma Anchors

- All Design page screen groups.
- Bottom-tab shell on compact screens.
- Repeated top app bars, lists, cards, checkout summaries, and profile subpages.

## Implementation Steps

1. Add adaptive layout helpers for:
   - compact phone portrait
   - landscape
   - tablet
   - foldable/expanded width
2. Use list-detail layouts where useful:
   - catalog/book detail
   - order history/order detail
   - notifications/detail
3. Move all user-visible strings to resources.
4. Add Vietnamese translations.
5. Correct obvious Figma typos:
   - `Spash` -> `Splash`
   - `Empte` -> `Empty`
   - `Copons` -> `Coupons`
   - `Vendords` -> `Vendors`
   - similar typo-only fixes without changing intent
6. Add content descriptions for icon-only buttons and important images.
7. Verify large font scale behavior for auth, checkout, profile, and cards.
8. Add test coverage:
   - ViewModel/use case unit tests
   - repository tests
   - Room tests
   - Compose UI tests for critical flows
9. Document Gradle checks for PR and pre-release.

## Critical Compose UI Scenarios

- Onboarding to sign in/home.
- Email sign-in and guest flow.
- Forgot-password email flow.
- Home to book detail to cart.
- Category search with recent searches.
- Checkout with Kuwait-style address, KNET/COD selection, delivery slot, confirmation.
- Order received and rating feedback.
- Profile to account/address/favorites/offers/logout.

## Gradle Checks

```powershell
.\gradlew.bat :app:assembleDebug
.\gradlew.bat testDebugUnitTest
.\gradlew.bat lintDebug
.\gradlew.bat :core:data:testDebugUnitTest
.\gradlew.bat connectedDebugAndroidTest
```

## Acceptance Criteria

- Major screens work on phone portrait, landscape, tablet, and foldable widths.
- English and Vietnamese strings are resource-backed.
- Accessibility labels exist for icon-only actions.
- JVM tests cover core business logic.
- Compose UI tests cover the main Figma flow path.
