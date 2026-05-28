---
title: "Bazar Books Figma-Stitched Research"
description: "Figma-grounded research summary for implementing the Bazar Books Android ecommerce app from the Design page."
status: complete
created: 2026-05-27
tags: [research, android, figma, compose, offline-first, room, datastore, navigation, hilt]
---

# Bazar Books Figma-Stitched Research

Timestamp: 2026-05-27, Asia/Saigon.

## Scope

Replace the generic architecture plan with a Figma-stitched implementation plan for Bazar Books. The source of truth is Figma file `Bazar Books`, Design page `0:1`.

The app keeps the existing Android module scaffold:

- `app`
- `core:common`
- `core:data`
- `core:designsystem`
- `core:domain`
- `core:navigation`
- `feature:onboarding`
- `feature:auth`
- `feature:home`
- `feature:cart_checkout`
- `feature:orders`
- `feature:notifications`
- `feature:profile`

## Locked Product Decisions

- Product mode: offline-first.
- Auth v1: email/password, guest mode, forgot password, simulated refresh token, mandatory phone capture during sign-up, and local simulated email/phone OTP.
- Deferred auth: Google sign-in, Apple sign-in, and real SMS/email provider integration.
- Catalog data: category, author, publisher/vendor, rating, stock, discounts, wishlist, reviews, related books.
- Search: server-style search contract with pagination and filters by category, price, rating, author; implemented locally against Room until a real backend exists.
- Checkout: demo-only, but with full simulated processing.
- Market: Figma Kuwait-style one-country model.
- Address fields: name, phone, governorate, city, block, street name/number, building name/number, optional floor, optional flat, optional avenue, saved label.
- Payment simulation: KNET, credit card demo, cash on delivery.
- Order statuses: pending, paid, processing, shipping, delivered, cancelled, refunded.
- Room: source of truth.
- Cart: offline-first.
- DataStore: user preferences and session/token state.
- Multi-account: no, one active account at a time.
- UI: native Compose following Material 3, stitched to Figma layout/copy direction, not pixel-perfect.
- Dark mode: yes.
- Icons: Material icons, icons from Figma design.
- Form factors: phone, tablet, foldable, landscape.
- Tests: ViewModel/use case unit tests, repository tests, Room tests, Compose UI tests.
- Localization: English and Vietnamese.

## Figma Design Page Inventory

Design page `0:1` contains these implementation sections:

### 1. Onboarding

- `1. Spash Screen`
- `1.1 Onboarding 2`
- `1.2 Onboarding 3`
- `1.3 Onboarding 4`

### 2. Sign in & Sign up

- `2. Sign In - Empty State`
- `2.1 Sign In - Active State`
- `2.2 Sign Up - Empte State`
- `2.3 Sign Up - Active State Right Password`
- `2.4 Sign Up - Active State Wrong Password`
- `2.5 Sign Up - Verification Code Email`
- `2.6 Sign Up - Input Phone Number`
- `2.7 Sign Up - Verification Code Phone`
- `2.8 Sign Up - Success Verification`

V1 implements email sign-in plus the full Figma sign-up flow from `2.2` through `2.8`: form, email OTP, mandatory phone input, phone OTP, and success. Email/phone OTP are simulated locally, phone number is mandatory before account completion, and the password checklist appears while typing. Social providers remain out of scope.

### 3. Forgot Password

- `3. Forgot Password`
- `3.1 Forgot Password - With Email`
- `3.2 Forgot Password - With Phone`
- `3.3 Forgot Password - Verification Code Email`
- `3.4 Forgot Password - Verification Code Phone`
- `3.5 Forgot Password - Create New Password`
- `3.6 Forgot Password - Success Create New Password`

V1 implements the email path only.

### 4. Home

- `4. Home`
- `4.1 Home - Set Location`
- `4.2 Home - Set Location`
- `4.3 Home - Vendors`
- `4.3 Detail Menu`
- `4.3 Home - Authors`
- `4.3 Home - Authors - Inner page`

Key Figma content: special offer banners, top of week, best vendors, authors, book detail, review/rating, quantity, price, view cart, continue shopping.

### 5. Category

- `5 Menu`
- `5.1 Menu-Search`

Key Figma content: category tabs `All`, `Novels`, `Self Love`, `Science`, `Romantic`; search page; recent searches; product result cards.

### 6. Cart & Checkout

- `6.1 Cart - Confirm Order`
- `6.1 Cart - Confirm Order visa added`
- `6.2 Cart - Confirm Order - See details`
- `6.3 Cart - Confirm Order - Payment Method`
- `4.1 Home - Set Location`
- `4.2 Home - Set Location`
- `Cart Empty`
- `Notifications Empty`

Key Figma content: confirm order, address, payment, KNET, credit card, cash on delivery, date/time, delivery slot, summary, payment details, empty cart.

### 7. Order Status

- `7 Order Status - Order Success Waiting Shipper`
- `7.3 Order Status - Order Received & Rating`

Key Figma content: order details, order number, delivery estimate, cancel action, received state, feedback/rating.

### 8. Notification

- `8. Notification - Delivery`
- `8.1 Notification - News & Promo`
- `8.2 Detail News & Promo`

Key Figma content: delivery notifications, news/promos, promo detail, empty notification state.

### 9. Profile

- `9. Profile`
- `9.1 My Account`
- `9.2 Address`
- `9.3 Your Favorites`
- `9.4 Order History`
- `9.5 Help Center`
- `9.6 Logout`
- `9.7 Offers`

Key Figma content: profile menu, edit account, address, favorites, order history, help center, logout dialog, offers/promos with copy action.

## Current Repo Findings

- The repo already follows the intended module split.
- `core:domain` contains repository interfaces and use cases.
- `core:data` currently binds fake repositories through Hilt.
- `core:data` includes Room runtime/ktx and `BazarBooksDatabase`; Room compiler setup should be confirmed before entity/DAO work.
- `core:common` contains an MVI base ViewModel.
- Feature modules already have `Contract`, `ViewModel`, `Route`, and `Screen` files.
- Several screens still keep durable state inside Composables and need migration to StateFlow MVI.
- Onboarding has already been aligned with the Figma onboarding screens.

## Architecture Recommendations

1. Make the Figma screen inventory the implementation backlog.
2. Build design system primitives before feature rewrites so screens share consistent Figma-styled components.
3. Keep Room as the only source of truth for catalog, cart, wishlist, orders, addresses, reviews, notifications, offers, and profile.
4. Keep Retrofit API contracts even without a real backend; use simulated remote sources and seeded data.
5. Implement local Room search/paging first; add real remote search later without changing feature APIs.
6. Store preferences/session in DataStore; introduce encrypted token storage before real production auth.
7. Keep route contracts in `core:navigation`; pass primitive IDs only.
8. Enforce feature isolation: feature modules depend on core modules only.
9. Move all screen state to ViewModels with `StateFlow`; keep Composables stateless except visual-only state.
10. Add English/Vietnamese resources while correcting obvious Figma copy typos.

## Official Guidance Reviewed

- Android app architecture: https://developer.android.com/topic/architecture
- Android data layer: https://developer.android.com/topic/architecture/data-layer
- Offline-first data layer: https://developer.android.google.cn/topic/architecture/data-layer/offline-first?hl=en
- Room: https://developer.android.com/training/data-storage/room
- DataStore: https://developer.android.com/datastore
- Navigation type safety: https://developer.android.com/guide/navigation/navigation-type-safety
- Compose state: https://developer.android.com/develop/ui/compose/state
- Compose adaptive layouts: https://developer.android.com/develop/ui/compose/build-adaptive-apps
- Paging 3: https://developer.android.com/topic/libraries/architecture/paging/v3-paged-data?hl=en
- Hilt: https://developer.android.com/training/dependency-injection/hilt-android
- Hilt testing: https://developer.android.com/training/dependency-injection/hilt-testing

## Open Risks

- Figma includes social auth and forgot-password phone screens that are out of v1 scope. Mitigation: implement local email/phone OTP only for sign-up, keep Google/Apple and forgot-password phone provider routes absent or disabled until explicitly in scope.
- Server-style search has no real backend. Mitigation: use local Room query/paging behind the same domain request model.
- Checkout is simulated. Mitigation: build a deterministic order state machine and persist every state transition.
- Tablet/foldable support increases UI scope. Mitigation: use shared adaptive layout helpers instead of duplicate feature screens.
- Plain DataStore is not production-secure for tokens. Mitigation: mark encrypted token storage as pre-production requirement.
