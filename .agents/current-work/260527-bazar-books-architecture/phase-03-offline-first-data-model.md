# Phase 03: Offline-First Data Model

Status: Pending  
Priority: P1  
Effort: 18h

## Overview

Model the Figma app data in Room and expose it through offline-first repositories. Room is source of truth. Retrofit interfaces remain as future backend contracts, backed by simulated remote sources for now.

## Figma Anchors

- Books with title, price, cover, rating, reviews, stock, discount.
- Categories: All, Novels, Self Love, Science, Romantic.
- Vendors and authors.
- Wishlist/favorites.
- Cart and checkout summary.
- Kuwait-style address.
- KNET/card/COD payment selection.
- Orders and status history.
- Delivery/news/promo notifications.
- Offers/promos with copy action.

## Implementation Steps

1. Add or confirm Room compiler setup.
2. Create entities and DAOs for:
   - book, category, author, vendor/publisher
   - book-category, related-book references
   - review, wishlist
   - cart item
   - address
   - payment method selection
   - order, order item, order status history
   - notification
   - offer/promo
   - profile
   - recent search
   - pending operation/outbox
3. Create DTOs for simulated remote API contracts.
4. Add mappers between DTO, entity, and domain models.
5. Seed Room with Figma-like sample data.
6. Implement local data sources around DAOs.
7. Implement simulated remote data sources for refresh/search/checkout.
8. Implement repositories:
   - auth/session
   - catalog/search
   - wishlist
   - cart
   - checkout/order
   - notification
   - profile/address/offers
9. Search uses a server-style request object but executes locally against Room.
10. Persist cart/order writes first, then simulate sync through pending operations.

## Acceptance Criteria

- App can run without network.
- Catalog, favorites, cart, address, orders, notifications, and offers survive app restart.
- Search/filter/pagination can run from local Room data.
- Repositories expose `Flow` for observable state and suspend functions for writes.

## Validation

```powershell
.\gradlew.bat :core:data:testDebugUnitTest
.\gradlew.bat :core:data:compileDebugKotlin
.\gradlew.bat :app:assembleDebug
```
