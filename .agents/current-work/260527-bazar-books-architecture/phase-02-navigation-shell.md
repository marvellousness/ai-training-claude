# Phase 02: Navigation Shell

Status: Pending  
Priority: P1  
Effort: 8h

## Overview

Create the app navigation shell that matches the Figma bottom-tab model and supports non-tab flows for onboarding, auth, forgot password, detail, checkout, notifications, and order status.

## Figma Anchors

- Bottom tabs shown in Home, Category, Cart, Profile: `Home`, `Category`, `Cart`, `Profile`.
- Non-tab screens: splash/onboarding, sign in/sign up, forgot password, book detail, author detail, checkout, order status, notification detail, profile subpages.

## Implementation Steps

1. Define typed route contracts in `core:navigation`.
2. Add bottom-tab destinations:
   - Home
   - Category
   - Cart
   - Profile
3. Add non-tab routes:
   - Onboarding
   - SignIn
   - SignUp
   - ForgotPassword
   - ForgotPasswordCode
   - CreateNewPassword
   - BookDetail(bookId)
   - VendorList
   - AuthorList
   - AuthorDetail(authorId)
   - Search
   - Checkout
   - AddressEdit(addressId?)
   - PaymentMethod
   - OrderDetail(orderId)
   - OrderStatus(orderId)
   - NotificationList
   - NotificationDetail(notificationId)
   - ProfileAccount
   - ProfileAddress
   - Favorites
   - OrderHistory
   - HelpCenter
   - Offers
4. Implement root start destination logic from DataStore session/onboarding state.
5. Keep route args primitive only.
6. Use navigation effects emitted from ViewModels instead of direct repository calls from Composables.
7. Add deep link patterns for book detail, order detail/status, and notification detail.

## Acceptance Criteria

- Bottom nav appears only for main tab destinations.
- Onboarding/auth flows are outside the bottom-tab shell.
- Feature modules do not depend on each other.
- Routes can be tested as simple data contracts.

## Validation

```powershell
.\gradlew.bat :core:navigation:compileDebugKotlin
.\gradlew.bat :app:assembleDebug
```
