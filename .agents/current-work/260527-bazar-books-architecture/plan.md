---
title: "Bazar Books Figma-Stitched Android Plan"
description: "Implementation-ready phase plan stitched to the Bazar Books Figma Design page screen groups."
status: pending
priority: P1
effort: 84h
branch: feat/bazar-books-figma-stitched
tags: [feature, android, figma, compose, navigation, design-system, domain, networking, persistence, room, datastore, hilt, l10n, a11y]
created: 2026-05-27
---

# Bazar Books Figma-Stitched Android Plan

## Overview

Build Bazar Books as an offline-first ecommerce Android app using the Figma Design page `0:1` as the implementation source of truth. Keep the existing module scaffold, use native Material 3 Compose, and implement Figma flows in screen-group order.

## Locked Decisions

- Auth v1: email/password, guest mode, forgot password, simulated refresh token.
- Deferred: Google/Apple sign-in and phone verification screens.
- Market: Figma Kuwait-style address and checkout model.
- Payments: simulated KNET, credit card demo, and cash on delivery.
- UI: Figma layout/copy direction with native Material 3 Compose, not pixel-perfect.
- Data: Room source of truth, cart offline-first, DataStore preferences/session.
- Architecture: feature-per-module Clean Architecture, StateFlow MVI, Hilt, Navigation Compose.
- Boundaries: no feature-to-feature dependencies; cross-feature movement goes through `core:navigation`.

## Architecture Target

```text
app
|- MainActivity, root app state, app nav host, app theme, locale/session bootstrapping

core:common
|- MVI base contracts, dispatchers, app result/error types, validation helpers

core:designsystem
|- Figma tokens, Material 3 theme, reusable Bazar components, adaptive layout helpers

core:navigation
|- typed routes, deep link patterns, bottom-tab destinations

core:domain
|- pure Kotlin models, repository interfaces, use cases, order/checkout rules

core:data
|- Room entities/DAOs, DataStore, Retrofit contracts, simulated remote sources, repositories

feature:*
|- route entrypoints, StateFlow MVI ViewModels, stateless Compose screens
```

## Figma Screen Groups

- `1. Onboarding`: splash and onboarding slides.
- `2. Sign in & Sign up`: email sign-in/sign-up states, password validation, success.
- `3. Forgot Password`: email reset path, verification code, new password, success.
- `4. Home`: home feed, vendors, authors, book detail.
- `5. Category`: category tabs, search, recent searches, results.
- `6. Cart & Checkout`: cart, empty cart, confirm order, address/location, payment, delivery date/time, summary/details.
- `7. Order Status`: waiting shipper, received order, rating feedback.
- `8. Notification`: delivery notifications, news/promos, promo detail, empty state.
- `9. Profile`: profile menu, account, address, favorites, order history, help center, offers, logout.

## Phases

| # | Phase | Status | Effort | Link |
|---|-------|--------|--------|------|
| 1 | Design System and Figma Primitives | Pending | 10h | [phase-01-design-system-figma-primitives](./phase-01-design-system-figma-primitives.md) |
| 2 | Navigation Shell | Pending | 8h | [phase-02-navigation-shell](./phase-02-navigation-shell.md) |
| 3 | Offline-First Data Model | Pending | 18h | [phase-03-offline-first-data-model](./phase-03-offline-first-data-model.md) |
| 4 | Onboarding, Auth, Forgot Password | Pending | 10h | [phase-04-onboarding-auth-forgot-password](./phase-04-onboarding-auth-forgot-password.md) |
| 5 | Home, Category, Search, Detail | Pending | 14h | [phase-05-home-category-search-detail](./phase-05-home-category-search-detail.md) |
| 6 | Cart, Checkout, Orders | Pending | 12h | [phase-06-cart-checkout-orders](./phase-06-cart-checkout-orders.md) |
| 7 | Notifications, Profile, Offers | Pending | 8h | [phase-07-notifications-profile-offers](./phase-07-notifications-profile-offers.md) |
| 8 | Adaptive, Localization, Testing | Pending | 4h | [phase-08-adaptive-localization-testing](./phase-08-adaptive-localization-testing.md) |

## Global Implementation Rules

- Use Figma section/frame names as acceptance anchors for screen coverage.
- Route arguments pass primitive IDs only.
- Domain models must not contain Room, Retrofit, Android, or Compose annotations.
- Repositories expose Room-backed `Flow` and suspend write APIs.
- UI reads `StateFlow` and sends intents to ViewModels.
- Composables should be stateless except local visual state.
- All visible strings come from resources in English and Vietnamese.
- Correct obvious Figma typos in app strings, but keep the same meaning.

## Validation Summary

```powershell
.\gradlew.bat :app:assembleDebug
.\gradlew.bat testDebugUnitTest
.\gradlew.bat lintDebug
.\gradlew.bat :core:data:testDebugUnitTest
.\gradlew.bat connectedDebugAndroidTest
```

Run connected tests before release or major UI merges. Use JVM tests as the default PR gate.
