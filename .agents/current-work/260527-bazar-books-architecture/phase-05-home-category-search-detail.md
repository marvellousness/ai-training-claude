# Phase 05: Home, Category, Search, Detail

Status: Pending  
Priority: P1  
Effort: 14h

## Overview

Implement the main browsing experience from Figma: home feed, category tabs, search, vendors, authors, book detail, wishlist/favorites, reviews, and related books.

## Figma Anchors

- `4. Home`
- `4.1 Home - Set Location`
- `4.2 Home - Set Location`
- `4.3 Home - Vendors`
- `4.3 Home - Authors`
- `4.3 Home - Authors - Inner page`
- `4.3 Detail Menu`
- `5 Menu`
- `5.1 Menu-Search`
- `9.3 Your Favorites`

## Implementation Steps

1. Define domain search/filter model:
   - query
   - category
   - author
   - price range
   - rating range
   - sort
   - page/pageSize
2. Implement Home MVI state:
   - special offers
   - top of week
   - best vendors
   - authors
   - loading/empty/error/offline states
3. Implement Category MVI state:
   - selected category tab
   - paged result list
   - filter state
4. Implement Search MVI state:
   - query
   - recent searches
   - results
   - no-results state
5. Implement Book Detail MVI state:
   - book metadata
   - rating/reviews
   - stock
   - quantity
   - wishlist state
   - related books
6. Implement Vendor and Author list/detail pages using existing `feature:home` unless a later split is justified.
7. Add wishlist toggle and favorites screen.
8. Use design system book/vendor/author cards.

## Acceptance Criteria

- Home matches Figma section structure.
- Category tabs match Figma labels.
- Search stores and displays recent searches.
- Book detail can add item to cart and view cart.
- Wishlist/favorites works offline and persists.
- Author/vendor flows are reachable from Home.

## Validation

```powershell
.\gradlew.bat :feature:home:testDebugUnitTest
.\gradlew.bat :feature:profile:testDebugUnitTest
.\gradlew.bat :core:data:testDebugUnitTest
.\gradlew.bat :app:assembleDebug
```
