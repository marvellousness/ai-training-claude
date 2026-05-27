# Phase 07: Notifications, Profile, Offers

Status: Pending  
Priority: P1  
Effort: 8h

## Overview

Implement the Figma profile and notification subflows: delivery/news/promo notifications, account edit, addresses, favorites entry, order history, help center, offers/promos, and logout dialog.

## Figma Anchors

- `8. Notification - Delivery`
- `8.1 Notification - News & Promo`
- `8.2 Detail News & Promo`
- `Notifications Empty`
- `9. Profile`
- `9.1 My Account`
- `9.2 Address`
- `9.3 Your Favorites`
- `9.4 Order History`
- `9.5 Help Center`
- `9.6 Logout`
- `9.7 Offers`

## Implementation Steps

1. Implement Notifications MVI:
   - delivery tab/list
   - news/promo tab/list
   - detail screen
   - empty state
   - read/unread state
2. Implement Profile MVI:
   - user summary
   - menu entries
   - logout action
3. Implement My Account:
   - name
   - email
   - phone
   - password/change password placeholder
   - change picture placeholder
4. Implement Address screen using the same Kuwait-style address model as checkout.
5. Wire Favorites to wishlist data from catalog phase.
6. Implement Order History:
   - grouped by month
   - delivered/cancelled/refunded status labels
   - item count
   - order detail navigation
7. Implement Help Center as a static support screen using Figma copy structure.
8. Implement Offers:
   - offer cards
   - discount labels
   - copy action
9. Implement Logout dialog:
   - confirm
   - cancel
   - clear session only after confirm

## Acceptance Criteria

- Notification list/detail and empty states match Figma structure.
- Profile menu reaches all planned subflows.
- Address data is shared between profile and checkout.
- Offers copy action produces a user-visible confirmation.
- Logout clears session and returns to auth flow.

## Validation

```powershell
.\gradlew.bat :feature:notifications:testDebugUnitTest
.\gradlew.bat :feature:profile:testDebugUnitTest
.\gradlew.bat :app:assembleDebug
```
