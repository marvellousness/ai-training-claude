# Phase 01: Design System and Figma Primitives

Status: Pending  
Priority: P1  
Effort: 10h

## Overview

Build reusable Compose primitives that match the Bazar Books Figma visual language before rewriting feature screens. This phase prevents each feature from re-implementing buttons, fields, cards, app bars, empty states, and checkout rows independently.

## Figma Anchors

- Styleguide page tokens already extracted.
- Components page button/input/card patterns.
- Design page repeated structures: top app bar, bottom nav, search field, book cards, vendor/author cards, checkout summary, empty states.

## Implementation Steps

1. Finalize Material 3 light and dark color schemes using Figma tokens, centered on primary `#54408c`.
2. Keep native Material 3 semantics while matching Figma spacing, rounded shapes, typography scale, and visual hierarchy.
3. Add reusable components:
   - `BazarTopAppBar`
   - `BazarBottomBar`
   - `BazarPrimaryButton`
   - `BazarSecondaryButton`
   - `BazarTextField`
   - `BazarPasswordField`
   - `BazarOtpField`
   - `BazarSearchField`
   - `BazarBookCard`
   - `BazarVendorCard`
   - `BazarAuthorCard`
   - `BazarRatingRow`
   - `BazarPriceText`
   - `BazarQuantityStepper`
   - `BazarCheckoutSummary`
   - `BazarEmptyState`
   - `BazarConfirmDialog`
4. Use Material icons for navigation, search, cart, profile, favorite, notification, back, filter, plus/minus, visibility, logout.
5. Add previews for light/dark and compact/expanded widths.
6. Add accessibility labels for icon-only buttons.

## Acceptance Criteria

- Feature screens can compose common UI from design system primitives.
- Light and dark themes compile and preview.
- Figma repeated UI patterns are represented as reusable components.
- Components support long English/Vietnamese text without clipping in common cases.

## Validation

```powershell
.\gradlew.bat :core:designsystem:compileDebugKotlin
.\gradlew.bat :app:assembleDebug
```
